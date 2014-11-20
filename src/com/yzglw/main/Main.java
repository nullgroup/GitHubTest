package com.yzglw.main;

import com.yzglw.dialog.DataBasePassword;
import com.yzglw.window.login.LoginWindow;

/**
 * @author OSC
 * 
 */
public class Main {

	public static LoginWindow login;

	public static void main(String[] args) {
		DataBasePassword beginDialog = new DataBasePassword();
		beginDialog.setVisible(true);
		login = new LoginWindow();
		login.setVisible(true);
	}
}
