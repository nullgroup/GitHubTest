package com.yzglw.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.yzglw.dialog.DataBaseError;
import com.yzglw.dialog.DataBasePassword;
import com.yzglw.identity.Identity;
import com.yzglw.lx.PhysicalApplication;
import com.yzglw.lx.PhysicalUniversity;
import com.yzglw.lx.ReportCard;
import com.yzglw.util.RandomApplication;

/**
 * @author Galdon
 * 
 */
public class Database {

	public static void main(String[] args) {
		try {
			Database db = new Database();
			db.createAllTable();
		} catch (Exception e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
	}

	private Connection conn;
	private Statement stmt;

	private ResultSet resultSet;

	public Database() throws ClassNotFoundException, FileNotFoundException,
			IOException, SQLException {

		String command = "jdbc:mysql://127.0.0.1:3306/mytest";
		String user = "root";
		String password = DataBasePassword.getPassword();

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(command, user, password);
		stmt = conn.createStatement();
	}

	// 向用户表中添加用户信息，包括三项用户名，密码，用户类型
	public int addAccount(String account, String password, int type) {
		String sql = "insert into jdbc(Account,Password,Type)" + "values('"
				+ account + "','" + password + "','" + type + "');";
		if (checkNumber(type) >= Identity.getType(type))
			return 3;
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return 2;
		}
		return 0;
	}

	// 向学生申请表内添加一条记录
	public boolean addApp(PhysicalApplication app) throws SQLException {
		ReportCard card = app.getReportCard();
		int checks = card.isChecked() ? 1 : 0;

		return (addApp(card.getName(), card.getHighSch(), card.getEmailAddr(),
				card.getResume(), card.getGrade()[0], card.getGrade()[1],
				card.getGrade()[2], card.getGrade()[3], checks,
				app.getTargetUniversity(), app.getTargetMajor()));
	}

	// 向学生申请表内添加一条记录
	public boolean addApp(String name, String highschool, String email,
			String resume, int chinese, int math, int english, int science,
			int checks, String tuni, String tmaj) throws SQLException {
		String sql = "insert into app(Name,HighSchool,EmailAddr,Resume,Chinese,"
				+ "Math,English,Science,Checks,TargetUni,TargetMaj)"
				+ "values('"
				+ name
				+ "','"
				+ highschool
				+ "','"
				+ email
				+ "','"
				+ resume
				+ "',"
				+ chinese
				+ ","
				+ math
				+ ","
				+ english
				+ ","
				+ science
				+ "," + checks + ",'" + tuni + "','" + tmaj + "');";
		stmt.executeUpdate(sql);
		return false;
	}

	// 使某个专业当前录取人数+1
	public boolean admitAndPlusByMajName(String maj) throws SQLException {
		int number = 0;
		String sql = "select * from maj where Major = '" + maj + "';";

		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			number = resultSet.getInt(4) + 1;
		}
		stmt.executeUpdate("update maj set Number = " + number
				+ " where Major = '" + maj + "';");
		return true;
	}

	// 检查数据库中各类型账户数量
	public int checkNumber(int identity) {
		int num = 0;
		String sql = "select Account from jdbc where type = " + identity + ";";
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				num++;
			}
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return num;
	}

	// 使某个专业当前录取人数清零
	public boolean clearAdmitByMajName(String maj) throws SQLException {
		int number = 0;
		String sql = "update maj set Number = " + number + " where Major = '"
				+ maj + "';";

		stmt.executeUpdate(sql);
		return true;
	}

	// 创建新用户表，若存在则不创建
	public void createAccountTable() throws SQLException {
		String sql = "create table if not exists jdbc" + "("
				+ "Account varchar(255) default '-' primary key,"
				+ "Password varchar(255) default '-' ," + "Type int" + ");";

		stmt.executeUpdate(sql);
	}

	// 新建所有用到的表
	public void createAllTable() throws SQLException {
		createAccountTable();
		createTypeNumberTable();
		createApplicationTable();
		createMajTable();
	}

	// 创建学生申请表，若存在则不创建
	public void createApplicationTable() throws SQLException {
		String sql1 = "create table if not exists app" + "("
				+ "ID int NOT NULL AUTO_INCREMENT primary key,"
				+ "Name varchar(255)," + "HighSchool varchar(255),"
				+ "EmailAddr varchar(255)," + "Resume varchar(255),"
				+ "Chinese int," + "Math int," + "English int,"
				+ "Science int," + "Checks int," + "TargetUni varchar(255),"
				+ "TargetMaj varchar(255)," + "Advice varchar(255)" + ");";
		stmt.executeUpdate(sql1);
	}

	// 创建大学专业表并初始化
	public void createMajTable() throws SQLException {
		String sql1 = "create table if not exists maj" + "("
				+ "Major varchar(255)," + "University int," + "MaxNumber int,"
				+ "Number int" + ");";
		String sql2 = "select * from maj;";

		stmt.executeUpdate(sql1);
		resultSet = stmt.executeQuery(sql2);
		if (!resultSet.next()) {
			initMajTable();
		}
	}

	// 创建用户类型数量表并初始化，若存在则不创建
	public void createTypeNumberTable() throws SQLException {
		String sql1 = "create table if not exists typenumber" + "("
				+ "PROXY int," + "AUDITOR int," + "APPROVER int" + ");";
		String sql2 = "insert into typenumber(PROXY,AUDITOR,APPROVER)"
				+ "values(" + Identity.NUM_OF_PROXY + ","
				+ Identity.NUM_OF_AUDITOR + "," + Identity.NUM_OF_APPROVER
				+ ");";
		stmt.executeUpdate(sql1);
		stmt.executeUpdate(sql2);
	}

	// 以用户名为参数删除某账户
	public void deleteAccount(String account) throws SQLException {
		String sql = "delete from jdbc where Account = '" + account + "';";
		stmt.executeUpdate(sql);
	}

	// 根据ID删除一张学生申请表
	public boolean deleteApp(int id) throws SQLException {
		String sql = "delete from app where ID = " + id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// 编辑一张学生表
	public boolean editApp(int id, PhysicalApplication app) throws SQLException {
		ReportCard card = app.getReportCard();
		int checks = card.isChecked() ? 1 : 0;
		return (editApp(id, card.getName(), card.getHighSch(),
				card.getEmailAddr(), card.getResume(), card.getGrade()[0],
				card.getGrade()[1], card.getGrade()[2], card.getGrade()[3],
				checks, app.getTargetUniversity(), app.getTargetMajor()));
	}

	// 编辑一张学生申请表
	public boolean editApp(int id, String name, String highschool,
			String email, String resume, int chinese, int math, int english,
			int science, int checks, String tuni, String tmaj)
			throws SQLException {
		return (deleteApp(id) && addApp(name, highschool, email, resume,
				chinese, math, english, science, checks, tuni, tmaj));
	}

	// 获得所有帐户名
	public String[] getAllAccount() {
		String sql = "select * from jdbc;";
		String[] result = new String[100];
		int i = 0;
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				result[i] = resultSet.getString(1);
				i++;
			}
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return result;
	}

	// 获得所有学生ID
	public int[] getAllId() throws SQLException {
		String sql = "select * from app;";
		int[] result = new int[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getInt(1);
			i++;
		}
		return result;
	}

	// 返回某大学的所有专业
	public String[] getAllMajByUniversityId(int university) throws SQLException {
		String[] allUniversity = new String[100];
		String sql = "select * from maj where University = " + university + ";";
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			allUniversity[i] = resultSet.getString(1);
			i++;
		}
		return allUniversity;
	}

	// 获得所有学生姓名
	public String[] getAllName() throws SQLException {
		String sql = "select * from app;";
		String[] result = new String[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getString(2);
			i++;
		}
		return result;
	}

	// 获得所有密码
	public String[] getAllPassword() throws SQLException {
		String sql = "select * from jdbc;";
		String[] result = new String[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getString(2);
			i++;
		}
		return result;
	}

	public int[] getAllScore() throws SQLException {
		String sql = "select * from app;";
		int[] result = new int[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			int chn = resultSet.getInt(6);
			int mth = resultSet.getInt(7);
			int eng = resultSet.getInt(8);
			int sci = resultSet.getInt(9);
			result[i] = chn + mth + eng + sci;
			i++;
		}
		return result;
	}

	// 获得所有学生目标专业
	public String[] getAllTargetMaj() throws SQLException {
		String sql = "select * from app;";
		String[] result = new String[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getString(12);
			i++;
		}
		return result;
	}

	// 获得所有学生目标大学
	public String[] getAllTargetUni() throws SQLException {
		String sql = "select * from app;";
		String[] result = new String[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getString(11);
			i++;
		}
		return result;
	}

	// 获得所有用户类型
	public int[] getAllType() throws SQLException {
		String sql = "select * from jdbc;";
		int[] result = new int[100];
		int i = 0;
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			result[i] = resultSet.getInt(3);
			i++;
		}
		return result;
	}

	// 根据ID获得学生盖章情况
	public int getCheckInfo(int id) {
		String sql = "select * from app where ID = " + id + ";";
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				return resultSet.getInt(10);
			}
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return -1;
	}

	// 返回某大学的所有专业的当前录取人数
	public int[] getCurAdmitByUniversityId(int university) throws SQLException {
		int k;
		int[] allNum = new int[100];
		String sql = "select * from maj where University = " + university + ";";
		int i = 0;

		for (k = 0; k < 100; k++) {
			allNum[k] = -1;
		}
		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			allNum[i] = resultSet.getInt(4);
			i++;
		}
		return allNum;
	}

	// 根据ID获得学生姓名,高中,电邮,简历,批复信息
	public String[] getEduInfoById(int id) {
		String sql = "select * from app where ID = " + id + ";";
		String[] info = new String[5];
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				info[0] = resultSet.getString(2);
				info[1] = resultSet.getString(3);
				info[2] = resultSet.getString(4);
				info[3] = resultSet.getString(5);
				info[4] = resultSet.getString(13);
			}
			return info;
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return null;
	}

	// 根据ID获得学生四项成绩
	public int[] getGradeInfoById(int id) {
		String sql = "select * from app where ID = " + id + ";";
		int[] grade = new int[4];
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				grade[0] = resultSet.getInt(6);
				grade[1] = resultSet.getInt(7);
				grade[2] = resultSet.getInt(8);
				grade[3] = resultSet.getInt(9);
			}
			return grade;
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return null;
	}

	// 获得用户类型数量表的值
	public int[] getIdentityNumber() {
		String sql = "select * from typenumber;";
		int[] result = new int[3];
		@SuppressWarnings("unused")
		int i = 0;
		try {
			resultSet = stmt.executeQuery(sql);

			while (resultSet.next()) {
				result[0] = resultSet.getInt(1);
				result[1] = resultSet.getInt(2);
				result[2] = resultSet.getInt(3);
			}
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return result;
	}

	// 返回某大学的所有专业的最大人数
	public int[] getMaxAdmitByUniversityId(int university) {
		int[] allMax = new int[100];
		String sql = "select * from maj where University = " + university + ";";
		int i = 0;

		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				allMax[i] = resultSet.getInt(3);
				i++;
			}
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return allMax;
	}

	// 根据ID获得学生目标大学和目标专业
	public String[] getTarUniAndTarMajById(int id) {
		String sql = "select * from app where ID = " + id + ";";
		String[] target = new String[2];
		try {
			resultSet = stmt.executeQuery(sql);
			while (resultSet.next()) {
				target[0] = resultSet.getString(11);
				target[1] = resultSet.getString(12);
			}
			return target;
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return null;
	}

	// 检查学生申请表期望的学校中有没有该专业
	public boolean hasTarUniAndTarMajMatched(PhysicalApplication app) {
		String uni = app.getTargetUniversity();
		String maj = app.getTargetMajor();
		int university;
		if (uni.equals(PhysicalUniversity.AUU)) {
			university = 0;
		} else if (uni.equals(PhysicalUniversity.TUS)) {
			university = 1;
		} else {
			university = 2;
		}
		String sql = "select * from maj where Major = '" + maj
				+ "' and University = " + university + ";";
		try {
			resultSet = stmt.executeQuery(sql);
			return resultSet.next();
		} catch (SQLException e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(null);
			errDialog.setVisible(true);
			e.printStackTrace();
		}
		return false;
	}

	// 若大学专业表为空则初始化
	public void initMajTable() throws SQLException {
		Random random = new Random();
		String sql = "insert into maj(Major,University,MaxNumber,Number)"
				+ "values('"
				+ RandomApplication.MAJ_LIB[0][0]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][1]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][2]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][3]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][4]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][5]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][6]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][7]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[0][8]
				+ "',0,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][0]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][1]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][2]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][3]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][4]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][5]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[1][6]
				+ "',1,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][0]
				+ "',2,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][1]
				+ "',2,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][2]
				+ "',2,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][3]
				+ "',2,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][4]
				+ "',2,"
				+ (random.nextInt(10) + 1)
				+ ",0),"
				+ "('"
				+ RandomApplication.MAJ_LIB[2][5]
				+ "',2,"
				+ (random.nextInt(10) + 1) + ",0);";
		stmt.executeUpdate(sql);
	}

	// 判断该专业是否满员
	public boolean notFull(String maj) throws SQLException {
		String sql = "select * from maj where Major = '" + maj + "';";
		int max = 0;
		int num = 0;

		resultSet = stmt.executeQuery(sql);
		while (resultSet.next()) {
			num = resultSet.getInt(4);
			max = resultSet.getInt(3);
		}
		return num < max;
	}

	// 登陆查询数据库，若用户名+密码+身份组合正确则成功登陆，成功返回true，失败返回false
	public boolean login(String account, String password, int type)
			throws SQLException {
		String sql = "select Account from jdbc where Account = '" + account
				+ "' and Password = '" + password + "' and Type = '" + type
				+ "';";
		resultSet = stmt.executeQuery(sql);
		return (resultSet.next() != false);
	}

	// 根据ID修改学生的目标大学和目标专业
	public boolean modifyTarUniAndTarMaj(int id, String changeuni,
			String changemaj) throws SQLException {
		String sql = "update app set TargetUni = '" + changeuni
				+ "', TargetMaj = '" + changemaj + "' where ID = " + id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// 通过用户名修改此账户类型
	public boolean setAccountType(String account, int type) throws SQLException {
		String sql = "update jdbc set Type = " + type + " where Account = '"
				+ account + "';";
		@SuppressWarnings("unused")
		int result = checkNumber(type);
		if (checkNumber(type) >= Identity.getType(type)) {
			System.out.println("No room!");
			return false;
		}
		stmt.executeUpdate(sql);
		return true;
	}

	// 根据ID设定某学生的批复信息
	public boolean setAdvice(int id, String advice) throws SQLException {
		String sql = "update app set Advice = '" + advice + "' where ID = "
				+ id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// 设置用户类型数量表中各值
	public boolean setTypeNumber(int num1, int num2, int num3)
			throws SQLException {
		String sql1 = "delete from typenumber;";

		String sql2 = "insert into typenumber(PROXY,AUDITOR,APPROVER)"
				+ "values(" + num1 + "," + num2 + "," + num3 + ");";
		stmt.executeUpdate(sql1);
		stmt.executeUpdate(sql2);
		return true;
	}

	public void sort() {
//		try {
//			stmt.executeUpdate("alter table app order by ID;");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
}
