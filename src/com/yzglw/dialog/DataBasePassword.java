package com.yzglw.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.util.ConstString;

@SuppressWarnings("serial")
public class DataBasePassword extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	
	private static String password = "";

	public static void main(String[] args) {
		DataBasePassword dialog = new DataBasePassword();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataBasePassword() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(300, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.menu);
		TitledBorder dialogBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				ConstString.NAME_OF_SOFTWARE,
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		contentPanel.setBorder(dialogBorder);
		dialogBorder.setTitleFont(new Font("微软雅黑", Font.BOLD, 12));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPrompt = new JLabel("请输入数据库密码:");
		lblPrompt.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblPrompt.setBounds(82, 40, 100, 18);
		contentPanel.add(lblPrompt);
		
		passwordField = new JPasswordField();
		passwordField.setText("");
		passwordField.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		passwordField.setBounds(82, 70, 120, 30);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.menu);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确定");
				okButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String input = new String(passwordField.getPassword());
						if (!input.equals(null) && !input.equals("")) {
							password = input;
						}
						dispose();
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
					
				});
				buttonPane.add(cancelButton);
			}
		}

	}

	public static String getPassword() {
		return password;
	}
}
