package com.yzglw.dialog;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * @author Galdon
 *
 */
@SuppressWarnings("serial")
public class AdminDialog extends JDialog {

	private JComboBox<String> comboBox;

	private Font dialogFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);
	private Container contentPane;
	
	private String typeStr;
	private int idenType;
	private boolean cancel;
	
	public String getSelectedItem() {
		return typeStr;
	}
	
	public int getSelectedIndex() {
		return idenType;
	}
	
	public boolean isCancel() {
		return cancel;
	}

	public static final String[] IDENTITY = { "代理专员", "审核专员", "批复专员" };

	public AdminDialog() {
		setLookAndFeel();
		setTitle("提示");
		setSize(300, 140);
		setLocationRelativeTo(null);
		setResizable(false);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initComponents();
	}
	
	private void initComponents() {
		contentPane = getContentPane();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setLayout(null);

		JLabel lblPrompt = new JLabel("更改为");
		lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrompt.setFont(dialogFont);
		lblPrompt.setBounds(70, 17, 40, 30);
		contentPane.add(lblPrompt);

		comboBox = new JComboBox<String>();
		comboBox.setFont(dialogFont);
		comboBox.setModel(new DefaultComboBoxModel<String>(IDENTITY));
		comboBox.setBounds(130, 17, 100, 30);
		contentPane.add(comboBox);

		JButton btnOk = new JButton("确定");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				typeStr = (String) comboBox.getSelectedItem();
				idenType = comboBox.getSelectedIndex() + 1;
				cancel = true;
				dispose();
			}
		});
		btnOk.setFont(boldFont);
		btnOk.setBounds(41, 54, 93, 32);
		contentPane.add(btnOk);

		JButton btnCancel = new JButton("取消");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel = false;
				dispose();
			}
		});
		btnCancel.setFont(boldFont);
		btnCancel.setBounds(153, 54, 93, 32);
		contentPane.add(btnCancel);
	}
	
	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
