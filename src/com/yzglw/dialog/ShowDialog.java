package com.yzglw.dialog;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author OSC
 *
 */
@SuppressWarnings("serial")
public class ShowDialog extends JDialog {
	
	static private final String MSG_ACCOUNT_DISMATCHED = "用户名或密码不正确";
	static private final String MSG_COMFIRM_DISMATCHED = "两次输入的密码不一致";
	static private final String MSG_VERIF_CODE_DISMATCHED = "验证码不正确";
	static private final String MSG_TOKEN_DISMATCHED = "口令错误";
	static private final String MSG_EMPTY_IN_USERNAME = "用户名不能为空";
	static private final String MSG_ENPTY_IN_PASSWORD = "密码不能为空";
	static private final String MSG_EMPTY_IN_VERIF_CODE = "验证码不能为空";
	static private final String MSG_REGIST_FAILURE = "注册失败, 请联系开发者";
	static private final String MSG_REGISTER_COLLISION = "注册信息冲突, 请修改注册信息";
	static private final String MSG_REGISTER_MAXIMUM = "注册人数已达最大值, 请修改注册身份";
	static private final String MSG_REGISTER_SUCCESS = "注册成功, 点击返回登录界面";
	static private final String MSG_WRONG_REGEX_FOR_USERNAME = "用户名格式不正确";
	static private final String MSG_WRONG_REGEX_FOR_PASSWORD = "密码格式不正确";

	static public final int PASS = 0;
	static public final int DISMATCH_BETWEEN_USERNAME_AND_PASSWORD = 1;
	static public final int DISMATCH_BETWEEN_PASSWORD_AND_COFIRMEDPASSWORD = 2;
	static public final int DISMATCH_IN_VERIF_CODE_FIELD = 3;
	static public final int DISMATCH_IN_TOKEN_FIELD = 4;
	static public final int EMPTY_IN_USERNAME_FIELD = 5;
	static public final int EMPTY_IN_PASSWORD_FIELD = 6;
	static public final int EMPTY_IN_VERIF_CODE_FIELD = 7;
	static public final int REGISTER_FAILURE = 8;
	static public final int REGISTER_COLLISION = 9;
	static public final int REGISTER_MAXIMUN = 10;
	static public final int REGISTER_SUCCESS = 11;
	static public final int WRONG_REGEX_FOR_USERNAME = 12;
	static public final int WRONG_REGEX_FOR_PASSWORD = 13;

	private JLabel lblMessage = null;
	private Font dialogFont = new Font("微软雅黑", Font.PLAIN, 12);

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public ShowDialog(int opt) {
		this(getMode(opt));
	}
	
	public ShowDialog(String info) {
		setLookAndFeel();
		setTitle("提示");
		setBounds(100, 100, 256, 119);
		setLocationRelativeTo(null);
		setResizable(false);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initComponents(info);
	}

	private void initComponents(String info) {
		Container contentPane = getContentPane();
		
		contentPane.setFocusable(true);
		contentPane.addKeyListener(enterKey);
		contentPane.setLayout(new GridLayout(2, 0, 0, 0));
		{
			JPanel textPanel = new JPanel();
			textPanel.setBackground(SystemColor.menu);
			contentPane.add(textPanel);
			textPanel.setLayout(null);
			{
				lblMessage = new JLabel(info);
				lblMessage.setBounds(0, 10, 250, 40);
				lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
				lblMessage.setFont(dialogFont);
				textPanel.add(lblMessage);
			}
		}
		{
			JPanel btnPanel = new JPanel();
			btnPanel.setBackground(SystemColor.menu);
			contentPane.add(btnPanel);
			btnPanel.setLayout(null);
			{
				JButton btnComfirm = new JButton("确认");
				btnComfirm.setBounds(85, 5, 80, 30);
				btnComfirm.setFont(dialogFont);
				btnComfirm.addActionListener(onConfirm);
				btnPanel.add(btnComfirm);
			}
		}
	}
	
	private static String getMode(int opt) {
		String mode = null;
		switch (opt) {
		case EMPTY_IN_USERNAME_FIELD:
			mode = MSG_EMPTY_IN_USERNAME;
			break;
		case EMPTY_IN_PASSWORD_FIELD:
			mode = MSG_ENPTY_IN_PASSWORD;
			break;
		case EMPTY_IN_VERIF_CODE_FIELD:
			mode = MSG_EMPTY_IN_VERIF_CODE;
			break;
		case DISMATCH_IN_VERIF_CODE_FIELD:
			mode = MSG_VERIF_CODE_DISMATCHED;
			break;
		case DISMATCH_BETWEEN_PASSWORD_AND_COFIRMEDPASSWORD:
			mode = MSG_COMFIRM_DISMATCHED;
			break;
		case DISMATCH_IN_TOKEN_FIELD:
			mode = MSG_TOKEN_DISMATCHED;
			break;
		case REGISTER_FAILURE:
			mode = MSG_REGIST_FAILURE;
			break;
		case REGISTER_COLLISION:
			mode = MSG_REGISTER_COLLISION;
			break;
		case REGISTER_MAXIMUN:
			mode = MSG_REGISTER_MAXIMUM;
			break;
		case REGISTER_SUCCESS:
			mode = MSG_REGISTER_SUCCESS;
			break;
		case WRONG_REGEX_FOR_USERNAME:
			mode = MSG_WRONG_REGEX_FOR_USERNAME;
			break;
		case WRONG_REGEX_FOR_PASSWORD:
			mode = MSG_WRONG_REGEX_FOR_PASSWORD;
			break;
		default:
			mode = MSG_ACCOUNT_DISMATCHED;
		}
		return mode;
	}

	private ActionListener onConfirm = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	private KeyAdapter enterKey = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				dispose();
			}
		}
	};
	
	public static void main(String[] args) {
		ShowDialog dialog = new ShowDialog("blablablabla");
		dialog.setVisible(true);
	}
}