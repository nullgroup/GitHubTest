package com.yzglw.identity;

/**
 * @author OSC
 *
 */
public final class Identity {

	static public final int PROXY = 1;
	static public final int AUDITOR = 2;
	static public final int APPROVER = 3;
	static public final int ADMINISTRATOR = 4;

	static public int NUM_OF_PROXY = 2;
	static public int NUM_OF_AUDITOR = 1;
	static public int NUM_OF_APPROVER = 3;
	static public int NUM_OF_ADMIN = 1;

	public static void setNumOfProxy(int numOfProxy) {
		NUM_OF_PROXY = numOfProxy;
	}

	public static void setNumOfAuditor(int numOfAuditor) {
		NUM_OF_AUDITOR = numOfAuditor;
	}

	public static void setNumOfApprover(int numOfApprover) {
		NUM_OF_APPROVER = numOfApprover;
	}

	public static void setNumOfAdmin(int numOfAdmin) {
		NUM_OF_ADMIN = numOfAdmin;
	}

	public static int getType(int type) {
		if (type == Identity.PROXY) {
			return Identity.NUM_OF_PROXY;
		} else if (type == Identity.AUDITOR) {
			return Identity.NUM_OF_AUDITOR;
		} else if (type == Identity.APPROVER) {
			return Identity.NUM_OF_APPROVER;
		} else if (type == Identity.ADMINISTRATOR) {
			return Identity.NUM_OF_ADMIN;
		} else {
			return 0;
		}
	}
}
