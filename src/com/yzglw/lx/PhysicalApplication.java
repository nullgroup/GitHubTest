/**
 * @(#)PhysicalApplication.java			0.51 14/05/21
 *
 * Copyright (C) 2014 伍至煊
 *
 * PhysicalApplication 代表一个通用的申请表(Application)
 *
 */

package com.yzglw.lx;

/**
 * @author OSC
 *
 */
public class PhysicalApplication {

	static public final int QUALIFIED = 1;
	static public final int DISQUALIFIED = 0;
	static public final int UNCHECKED = -1;
	
	private int applicationId;
	private ReportCard reportCard;
	private String targetUniversity;
	private String targetMajor;
	
	private int stateCode;

	public PhysicalApplication() {
		this(new ReportCard(), "", "");
	}
	
	public PhysicalApplication(ReportCard reportCard, String targetUniversity, String targetMajor) {
		this.reportCard = reportCard;
		this.targetUniversity = targetUniversity;
		this.targetMajor = targetMajor;
		stateCode = UNCHECKED;
	}

	public int getState() {
		return stateCode;
	}

	public void modifyState(int stateCode) {
		if (stateCode != PhysicalApplication.QUALIFIED
				|| stateCode != PhysicalApplication.DISQUALIFIED
				|| stateCode != PhysicalApplication.UNCHECKED) {
			throw new IllegalArgumentException("Illegal State Code!");
		} else {
			this.stateCode = stateCode;
		}
	}

	/**
	 * 返回一个用于描述该申请表的字符串.
	 * 
	 * @return 一个用于描述该申请表的字符串
	 */
	@Override
	public String toString() {
		StringBuilder appInfo = new StringBuilder();

		appInfo.append(applicationId + "\n");
		appInfo.append(reportCard.toString() + "\n");
		appInfo.append(targetUniversity + ", " + targetMajor);

		return appInfo.toString();
	}

	/**
	 * 返回该申请表的合格状态的字符表示形式, 有三种形式 - "QUALIFIED", "DISQUALIFIED", "UNCHECKED",
	 * 其中除了 "UNCHECKED" 之外的状态都可以被认定成已检查 "CHECKED".
	 * 
	 * @return 该申请表的合格状态的字符表示形式
	 */
	public String getStateStr() {
		return (stateCode == QUALIFIED) ? "QUALIFIED"
				: ((stateCode == DISQUALIFIED) ? "DISQUALIFIED"
						: "UNCHECKED");
	}
	
	public int getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public ReportCard getReportCard() {
		return reportCard;
	}

	public void setReportCard(ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	public String getTargetUniversity() {
		return targetUniversity;
	}

	public void setTargetUniversity(String targetUniversity) {
		this.targetUniversity = targetUniversity;
	}

	public String getTargetMajor() {
		return targetMajor;
	}

	public void setTargetMajor(String targetMajor) {
		this.targetMajor = targetMajor;
	}
}