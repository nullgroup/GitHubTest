/**
 * @(#)ReportCard.java			0.51 14/05/21
 *
 * Copyright (C) 2014 伍至煊
 *
 * ReportCard 是一个通用的高考成绩单类
 *
 */

package com.yzglw.lx;

/**
 * @author OSC
 *
 */
public class ReportCard {

	/**
	 * 初始化的成绩数组.
	 */
	static public final int[] INIT_GRADE_ARRAY = { 120, 120, 120, 240 };

	/**
	 * 学生的姓名.
	 */
	private String stuName;

	/**
	 * 学生所在的高中的名称.
	 */
	private String highSchool;

	/**
	 * 学生的电子邮箱地址.
	 */
	private String emailAddr;

	/**
	 * 学生的简历信息.
	 */
	private String resume;

	/**
	 * 学生高考的语文成绩.
	 */
	private int chinese;

	/**
	 * 学生高考的数学成绩.
	 */
	private int math;

	/**
	 * 学生高考的英语成绩.
	 */
	private int english;

	/**
	 * 学生高考的科学成绩.
	 */
	private int science;

	/**
	 * 该成绩单是否已盖章.
	 */
	private boolean check;

	/**
	 * 默认的构造方法.
	 */
	public ReportCard() {
		this(INIT_GRADE_ARRAY);
	}

	/**
	 * 用特定的, 表示成绩的整形数组为参数初始化的构造方法.
	 * 
	 * @param gradeArray
	 *            表示成绩的整形数组
	 */
	public ReportCard(int[] gradeArray) {
		this(gradeArray, null, null, null, null);
	}

	/**
	 * 用特定的, 表示成绩的整形数组和表示学生信息的字符串数组为参数初始化的构造方法.
	 * 
	 * @param gradeArray
	 *            表示成绩的整形数组
	 * @param eduInfo
	 *            表示学生信息的字符串数组
	 */
	public ReportCard(int[] gradeArray, String[] eduInfo) {
		this(gradeArray, eduInfo[0], eduInfo[1], eduInfo[2], eduInfo[3]);
	}

	/**
	 * 最具体的构造方法, 参数为特定的表示成绩的整形数组和具体的各项信息, 同时为该成绩表设置唯一的 id.
	 * 
	 * @param gradeArray
	 *            表示成绩的整形数组
	 * @param stuName
	 *            学生的姓名
	 * @param highSchool
	 *            学生所在高中的名称
	 * @param emailAddr
	 *            学生的电子邮箱地址
	 * @param resume
	 *            学生的简历
	 * @see com.yzglw.lx.SerialIdentifier
	 */
	public ReportCard(int[] gradeArray, String stuName, String highSchool,
			String emailAddr, String resume) {
		allocateGrade(gradeArray);
		this.stuName = stuName;
		this.highSchool = highSchool;
		this.emailAddr = emailAddr;
		this.resume = resume;
	}

	/**
	 * 返回学生的姓名.
	 * 
	 * @return 学生的姓名
	 */
	public String getName() {
		return stuName;
	}

	/**
	 * 返回学生所在高中的名称.
	 * 
	 * @return 学生所在高中的名称
	 */
	public String getHighSch() {
		return highSchool;
	}

	/**
	 * 返回学生的电子邮箱地址.
	 * 
	 * @return 学生的电子邮箱地址.
	 */
	public String getEmailAddr() {
		return emailAddr;
	}

	/**
	 * 返回学生的简历信息.
	 * 
	 * @return 返回学生的简历信息
	 */
	public String getResume() {
		return resume;
	}

	/**
	 * 更改学生的姓名, 特别的, 该方法不应该被用户所调用.
	 * 
	 * @param stuName
	 *            学生的新姓名
	 */
	public void setName(String stuName) {
		this.stuName = stuName;
	}

	/**
	 * 更改学生所在的高中的名称, 特别的, 该方法不应该被用户所调用.
	 * 
	 * @param highSchool
	 *            学生的新高中名称
	 */
	public void setHighSch(String highSchool) {
		this.highSchool = highSchool;
	}

	/**
	 * 更改学生的电子邮箱地址, 特别的, 该方法不应该被用户所调用.
	 * 
	 * @param emailAddr
	 *            学生的新的电子邮箱地址
	 */
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	/**
	 * 更改学生的简历信息, 特别的, 该方法可以(仅仅是建议可以)被用户所调用, 是否用户能更改简历信息取决于开发者.
	 * 
	 * @param resume
	 *            学生新的简历信息
	 */
	public void setResume(String resume) {
		this.resume = resume;
	}

	/**
	 * 返回一个表示成绩的整形数组, 数组的长度为 4
	 * 
	 * @return 一个表示成绩的整形数组
	 */
	public int[] getGrade() {
		int[] gradeArray = new int[4];

		gradeArray[0] = chinese;
		gradeArray[1] = math;
		gradeArray[2] = english;
		gradeArray[3] = science;

		return gradeArray;
	}

	/**
	 * 通过一个整形数组来修改成绩.
	 * 
	 * @param gradeArray
	 *            表示成绩的整形数组, 长度需大于等于 4, 超过 3 的索引项被忽略
	 */
	public void allocateGrade(int[] gradeArray) {
		if (gradeArray.length < 4) {
			throw new IllegalArgumentException("Illegal Length of GradeArray!");
		}
		for (int i = 0; i < gradeArray.length; i++) {
			if (i != 3) {
				if (gradeArray[i] < 0 || gradeArray[i] > 150) {
					throw new IllegalArgumentException(
							"Illegal Value of GradeArray at Index " + i);
				}
			} else {
				if (gradeArray[i] < 0 || gradeArray[i] > 300) {
					throw new IllegalArgumentException(
							"Illegal Value of GradeArray at Index " + i);
				}
			}
		}
		chinese = gradeArray[0];
		math = gradeArray[1];
		english = gradeArray[2];
		science = gradeArray[3];
	}

	/**
	 * 盖章.
	 */
	public void check() {
		check = true;
	}

	/**
	 * 查看是否盖章.
	 */
	public boolean isChecked() {
		return check;
	}

	/**
	 * 返回一个没有盖过章的副本.
	 */
	public ReportCard copyWithUnchecked() {
		ReportCard card = null;
		String[] eduInfo = { stuName, highSchool, emailAddr, resume };
		int[] gradeInfo = getGrade();

		card = new ReportCard(gradeInfo, eduInfo);

		return card;
	}

	/**
	 * 返回总成绩.
	 * 
	 * @return 总成绩
	 */
	public int getTotal() {
		return (chinese + math + english + science);
	}

	/**
	 * 私有成员, 非接口, 以整形数组表示各项成绩和总成绩.
	 * 
	 * @return 以整形数组表示的各项成绩和总成绩, 数组长度为 5
	 */
	private int[] toArray() {
		int sum = getTotal();
		int[] t = { chinese, math, english, science, sum };
		return t;
	}

	/**
	 * 返回一个描述该成绩单的字符串.
	 * 
	 * @return 一个描述该成绩单的字符串
	 */
	@Override
	public String toString() {
		StringBuilder cardInfo = new StringBuilder();

		cardInfo.append(stuName + ", " + highSchool + ", " + emailAddr + ", "
				+ resume + ", " + java.util.Arrays.toString(toArray()));

		return cardInfo.toString();
	}
}