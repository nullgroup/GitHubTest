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

	// ���û���������û���Ϣ�����������û��������룬�û�����
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

	// ��ѧ������������һ����¼
	public boolean addApp(PhysicalApplication app) throws SQLException {
		ReportCard card = app.getReportCard();
		int checks = card.isChecked() ? 1 : 0;

		return (addApp(card.getName(), card.getHighSch(), card.getEmailAddr(),
				card.getResume(), card.getGrade()[0], card.getGrade()[1],
				card.getGrade()[2], card.getGrade()[3], checks,
				app.getTargetUniversity(), app.getTargetMajor()));
	}

	// ��ѧ������������һ����¼
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

	// ʹĳ��רҵ��ǰ¼ȡ����+1
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

	// ������ݿ��и������˻�����
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

	// ʹĳ��רҵ��ǰ¼ȡ��������
	public boolean clearAdmitByMajName(String maj) throws SQLException {
		int number = 0;
		String sql = "update maj set Number = " + number + " where Major = '"
				+ maj + "';";

		stmt.executeUpdate(sql);
		return true;
	}

	// �������û����������򲻴���
	public void createAccountTable() throws SQLException {
		String sql = "create table if not exists jdbc" + "("
				+ "Account varchar(255) default '-' primary key,"
				+ "Password varchar(255) default '-' ," + "Type int" + ");";

		stmt.executeUpdate(sql);
	}

	// �½������õ��ı�
	public void createAllTable() throws SQLException {
		createAccountTable();
		createTypeNumberTable();
		createApplicationTable();
		createMajTable();
	}

	// ����ѧ��������������򲻴���
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

	// ������ѧרҵ����ʼ��
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

	// �����û�������������ʼ�����������򲻴���
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

	// ���û���Ϊ����ɾ��ĳ�˻�
	public void deleteAccount(String account) throws SQLException {
		String sql = "delete from jdbc where Account = '" + account + "';";
		stmt.executeUpdate(sql);
	}

	// ����IDɾ��һ��ѧ�������
	public boolean deleteApp(int id) throws SQLException {
		String sql = "delete from app where ID = " + id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// �༭һ��ѧ����
	public boolean editApp(int id, PhysicalApplication app) throws SQLException {
		ReportCard card = app.getReportCard();
		int checks = card.isChecked() ? 1 : 0;
		return (editApp(id, card.getName(), card.getHighSch(),
				card.getEmailAddr(), card.getResume(), card.getGrade()[0],
				card.getGrade()[1], card.getGrade()[2], card.getGrade()[3],
				checks, app.getTargetUniversity(), app.getTargetMajor()));
	}

	// �༭һ��ѧ�������
	public boolean editApp(int id, String name, String highschool,
			String email, String resume, int chinese, int math, int english,
			int science, int checks, String tuni, String tmaj)
			throws SQLException {
		return (deleteApp(id) && addApp(name, highschool, email, resume,
				chinese, math, english, science, checks, tuni, tmaj));
	}

	// ��������ʻ���
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

	// �������ѧ��ID
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

	// ����ĳ��ѧ������רҵ
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

	// �������ѧ������
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

	// �����������
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

	// �������ѧ��Ŀ��רҵ
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

	// �������ѧ��Ŀ���ѧ
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

	// ��������û�����
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

	// ����ID���ѧ���������
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

	// ����ĳ��ѧ������רҵ�ĵ�ǰ¼ȡ����
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

	// ����ID���ѧ������,����,����,����,������Ϣ
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

	// ����ID���ѧ������ɼ�
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

	// ����û������������ֵ
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

	// ����ĳ��ѧ������רҵ���������
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

	// ����ID���ѧ��Ŀ���ѧ��Ŀ��רҵ
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

	// ���ѧ�������������ѧУ����û�и�רҵ
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

	// ����ѧרҵ��Ϊ�����ʼ��
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

	// �жϸ�רҵ�Ƿ���Ա
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

	// ��½��ѯ���ݿ⣬���û���+����+��������ȷ��ɹ���½���ɹ�����true��ʧ�ܷ���false
	public boolean login(String account, String password, int type)
			throws SQLException {
		String sql = "select Account from jdbc where Account = '" + account
				+ "' and Password = '" + password + "' and Type = '" + type
				+ "';";
		resultSet = stmt.executeQuery(sql);
		return (resultSet.next() != false);
	}

	// ����ID�޸�ѧ����Ŀ���ѧ��Ŀ��רҵ
	public boolean modifyTarUniAndTarMaj(int id, String changeuni,
			String changemaj) throws SQLException {
		String sql = "update app set TargetUni = '" + changeuni
				+ "', TargetMaj = '" + changemaj + "' where ID = " + id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// ͨ���û����޸Ĵ��˻�����
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

	// ����ID�趨ĳѧ����������Ϣ
	public boolean setAdvice(int id, String advice) throws SQLException {
		String sql = "update app set Advice = '" + advice + "' where ID = "
				+ id + ";";
		stmt.executeUpdate(sql);
		return true;
	}

	// �����û������������и�ֵ
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
