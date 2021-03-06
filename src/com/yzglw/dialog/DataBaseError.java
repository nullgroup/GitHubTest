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
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.util.ConstString;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class DataBaseError extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private Font dialogFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);
	private JTextArea textArea;
	
	public String getReason() {
		return textArea.getText();
	}
	
	public void setReason(String reason) {
		textArea.setText(reason);
	}

	public static void main(String[] args) {
		DataBaseError dialog = new DataBaseError();
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
	
	public DataBaseError() {
		this("逗你玩, 其实没错误");
	}

	public DataBaseError(String reason) {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(400, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initComponents(reason);
	}

	private void initComponents(String reason) {
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.menu);
		TitledBorder dialogBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				ConstString.NAME_OF_SOFTWARE,
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		contentPanel.setBorder(dialogBorder);
		dialogBorder.setTitleFont(boldFont);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPrompt = new JLabel("数据库错误!");
		lblPrompt.setFont(boldFont);
		lblPrompt.setBounds(40, 20, 100, 30);
		contentPanel.add(lblPrompt);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 50, 320, 70);
		contentPanel.add(scrollPane);
		
		textArea = new JTextArea(reason);
		textArea.setLineWrap(true);
		textArea.setOpaque(true);
		textArea.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		textArea.setEditable(false);
		textArea.setBackground(SystemColor.menu);
		scrollPane.setViewportView(textArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(SystemColor.menu);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton errorButton = new JButton("  确定  ");
				errorButton.setFont(dialogFont);
				errorButton.setActionCommand("Cancel");
				errorButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
					
				});
				buttonPane.add(errorButton);
			}
		}
	}
}
