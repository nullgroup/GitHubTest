package com.yzglw.window.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.database.Database;
import com.yzglw.dialog.DataBaseError;
import com.yzglw.dialog.ShowDialog;
import com.yzglw.identity.Identity;
import com.yzglw.main.Main;
import com.yzglw.util.ConstString;
import com.yzglw.util.VerificationCode;

/**
 * @author OSC
 *
 */
@SuppressWarnings("serial")
public class RegWindow extends JFrame {

	private Database dataBase;
	private JPanel contentPane;
	private VerificationCode verifCode;
	private JTextField textVerifCode;
	private JPasswordField textPassword;
	private JPasswordField textConfirmPassword;
	private JTextField textUserName;
	private JComboBox<String> comboBox;

	private JPopupMenu popupMenu;
	private JPopupMenu jPopupMenu1;
	private JMenuItem jmiCut, jmiCopy, jmiPaste;

	private Font regFont = new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12);

	public static void main(String[] args) {
		new RegWindow().setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegWindow() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(560, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDataBase();
		initComponents();
	}
	
	private void initDataBase() {
		try {
			dataBase = new Database();
			dataBase.createAllTable();
		} catch (Exception e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(contentPane);
			errDialog.setVisible(true);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0,
				0)), null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel regPanel = new JPanel();
		regPanel.setBackground(SystemColor.menu);
		Font textFieldFont = new Font("Lucida Sans Unicode", Font.PLAIN, 12);
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				ConstString.NAME_OF_SOFTWARE, TitledBorder.LEADING,
				TitledBorder.TOP, null, null);

		titledBorder.setTitleFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		regPanel.setBorder(titledBorder);
		contentPane.add(regPanel, BorderLayout.CENTER);
		regPanel.setLayout(null);

		verifCode = new VerificationCode();
		textUserName = new JTextField();
		textPassword = new JPasswordField();
		textConfirmPassword = new JPasswordField();
		textVerifCode = new JTextField();
		comboBox = new JComboBox<String>();

		JButton btnSubmit = new JButton("×¢²á");
		JButton btnReturn = new JButton("·µ»Ø");

		popupMenu = new JPopupMenu();
		jPopupMenu1 = new JPopupMenu();
		JMenuItem jmiReg = new JMenuItem("×¢²á");
		JMenuItem jmiReturn = new JMenuItem("·µ»Ø");
		JMenuItem jmiExit = new JMenuItem("ÍË³ö");
		jmiCut = new JMenuItem("¼ôÇÐ(Ctrl+X)");
		jmiCopy = new JMenuItem("¸´ÖÆ(Ctrl+C)");
		jmiPaste = new JMenuItem("Õ³Ìù(Ctrl+V)");

		JLabel lblTitle = new JLabel("»¶Ó­×¢²á");
		JLabel lblUserName = new JLabel("ÓÃ»§Ãû");
		JLabel lblPassword = new JLabel("ÃÜÂë");
		JLabel lblConfirmPassword = new JLabel("È·ÈÏÃÜÂë");
		JLabel lblVerifCode = new JLabel("ÑéÖ¤Âë");
		JLabel lblIdentity = new JLabel("Éí·Ý");
		JLabel lblForUserName = new JLabel("4-16¸ö×ÖÄ¸»òÊý×Ö");
		JLabel lblForPassword = new JLabel("4-16¸ö´¿Êý×Ö");
		JLabel lblForConfirmPassword = new JLabel("ÔÙ´ÎÊäÈëÃÜÂë");
		JLabel lblForIdentity = new JLabel("×¢²áÉí·Ý");
		JLabel lblForVerifCode = new JLabel("ÊäÈëÑéÖ¤Âë");

		textUserName.setBounds(200, 120, 150, 25);
		textPassword.setBounds(200, 145, 150, 25);
		textConfirmPassword.setBounds(200, 170, 150, 25);
		textVerifCode.setBounds(200, 225, 50, 25);
		btnSubmit.setBounds(200, 270, 70, 30);
		btnReturn.setBounds(280, 270, 70, 30);
		comboBox.setBounds(200, 195, 85, 30);
		lblTitle.setBounds(120, 58, 300, 50);
		lblUserName.setBounds(155, 120, 40, 25);
		lblPassword.setBounds(155, 145, 40, 25);
		lblConfirmPassword.setBounds(145, 170, 50, 25);
		lblVerifCode.setBounds(155, 225, 40, 25);
		lblIdentity.setBounds(155, 195, 40, 30);
		lblForUserName.setBounds(355, 120, 150, 25);
		lblForPassword.setBounds(355, 145, 75, 25);
		lblForConfirmPassword.setBounds(355, 170, 100, 25);
		lblForIdentity.setBounds(290, 195, 55, 30);
		lblForVerifCode.setBounds(355, 225, 70, 25);

		textUserName.setFont(textFieldFont);
		textPassword.setFont(textFieldFont);
		textConfirmPassword.setFont(textFieldFont);
		textVerifCode.setFont(textFieldFont);
		verifCode.setFont(textFieldFont);
		comboBox.setFont(regFont);
		btnSubmit.setFont(regFont);
		btnReturn.setFont(regFont);
		lblTitle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 30));
		lblUserName.setFont(regFont);
		lblPassword.setFont(regFont);
		lblConfirmPassword.setFont(regFont);
		lblVerifCode.setFont(regFont);
		lblIdentity.setFont(regFont);
		lblForUserName.setFont(regFont);
		lblForPassword.setFont(regFont);
		lblForConfirmPassword.setFont(regFont);
		lblForIdentity.setFont(regFont);
		lblForVerifCode.setFont(regFont);
		jmiReg.setFont(regFont);
		jmiReturn.setFont(regFont);
		jmiExit.setFont(regFont);
		jmiCut.setFont(regFont);
		jmiCopy.setFont(regFont);
		jmiPaste.setFont(regFont);

		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBackground(new Color(25, 25, 112));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

		textUserName.setColumns(10);
		textVerifCode.setColumns(10);

		textUserName.setDropMode(DropMode.INSERT);

		verifCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSubmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReturn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		verifCode.setSize(95, 40);
		verifCode.setLocation(254, 225);

		final String[] IDENTITY_GROUP = { ConstString.PROXY_STR,
				ConstString.AUDITOR_STR, ConstString.APPROVER_STR,
				ConstString.ADMINISTRATOR_STR };
		comboBox.setModel(new DefaultComboBoxModel<String>(IDENTITY_GROUP));

		textVerifCode.addActionListener(regAction);
		textPassword.addActionListener(regAction);
		textConfirmPassword.addActionListener(regAction);
		textUserName.addActionListener(regAction);
		btnSubmit.addActionListener(regAction);
		btnReturn.addActionListener(returnAction);
		jmiReg.addActionListener(regAction);
		jmiReturn.addActionListener(returnAction);
		jmiExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jmiCut.addActionListener(cutAction);
		jmiCopy.addActionListener(copyAction);
		jmiPaste.addActionListener(pasteAction);

		btnSubmit
				.setToolTipText("<html><body><font face=\"Î¢ÈíÑÅºÚ\" size=3>Ìá½»×¢²áÐÅÏ¢</font></body></html>");
		btnReturn
				.setToolTipText("<html><body><font face=\"Î¢ÈíÑÅºÚ\" size=3>·µ»ØµÇÂ¼½çÃæ</font></body></html>");

		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVerifCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblForUserName.setHorizontalAlignment(SwingConstants.LEFT);
		lblForPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblForConfirmPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblForIdentity.setHorizontalAlignment(SwingConstants.LEFT);
		lblForVerifCode.setHorizontalAlignment(SwingConstants.LEFT);

		lblUserName.setLabelFor(textUserName);
		lblPassword.setLabelFor(textPassword);
		lblConfirmPassword.setLabelFor(textConfirmPassword);
		lblVerifCode.setLabelFor(textVerifCode);

		addPopup(this, popupMenu);
		addPopup(textUserName, jPopupMenu1);
		addPopup(textPassword, jPopupMenu1);
		addPopup(textVerifCode, jPopupMenu1);

		popupMenu.add(jmiReg);
		popupMenu.add(jmiReturn);
		popupMenu.add(jmiExit);
		jPopupMenu1.add(jmiCut);
		jPopupMenu1.add(jmiCopy);
		jPopupMenu1.add(jmiPaste);

		regPanel.add(textUserName);
		regPanel.add(textPassword);
		regPanel.add(textConfirmPassword);
		regPanel.add(textVerifCode);
		regPanel.add(verifCode);
		regPanel.add(btnSubmit);
		regPanel.add(btnReturn);
		regPanel.add(comboBox);
		regPanel.add(lblTitle);
		regPanel.add(lblUserName);
		regPanel.add(lblPassword);
		regPanel.add(lblConfirmPassword);
		regPanel.add(lblVerifCode);
		regPanel.add(lblIdentity);
		regPanel.add(lblForUserName);
		regPanel.add(lblForPassword);
		regPanel.add(lblForConfirmPassword);
		regPanel.add(lblForIdentity);
		regPanel.add(lblForVerifCode);
	}

	private ActionListener regAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String identity = (String) comboBox.getSelectedItem();

			int ideType = getItemType(identity);
			int errType = validate(ideType);

			JDialog dialog = new ShowDialog(errType);
			dialog.setLocationRelativeTo(contentPane);
			dialog.setVisible(true);

			if (errType != ShowDialog.REGISTER_SUCCESS) {
				verifCode.nextCode();
				return;
			} else {
				Main.login.clearTextField();
				Main.login.setLocationRelativeTo(contentPane);
				dispose();
				Main.login.setVisible(true);
			}
		}
	};

	private ActionListener returnAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			TokenWindow token = new TokenWindow();
			token.setLocationRelativeTo(contentPane);
			dispose();
			token.setVisible(true);
		}
	};

	private ActionListener cutAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			action(e);
		}
	};

	private ActionListener copyAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			action(e);
		}
	};

	private ActionListener pasteAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			action(e);
		}
	};

	private void action(ActionEvent e) {
		String cmd = e.getActionCommand();
		JTextField call = (JTextField) jPopupMenu1.getInvoker();

		if (cmd.equals(jmiCut.getText())) {
			call.cut();
		} else if (cmd.equals(jmiCopy.getText())) {
			call.copy();
		} else if (cmd.equals(jmiPaste.getText())) {
			call.paste();
		}
	}

	private int validate(final int idenType) {
		String userName = textUserName.getText();
		String password = new String(textPassword.getPassword());
		String comfirm = new String(textConfirmPassword.getPassword());
		String inputVerifCode = textVerifCode.getText();

		int errType = 0;
		int regResult = 0;

		if (userName.equals(null) || userName.equals("")) {
			errType = ShowDialog.EMPTY_IN_USERNAME_FIELD;
		} else if (!userName.matches(ConstString.REG_FOR_USERNAME)) {
			errType = ShowDialog.WRONG_REGEX_FOR_USERNAME;
		} else if (password.equals(null) || password.equals("")
				|| comfirm.equals(null) || comfirm.equals("")) {
			errType = ShowDialog.EMPTY_IN_PASSWORD_FIELD;
		} else if (!password.matches(ConstString.REG_FOR_PASSWORD)) {
			errType = ShowDialog.WRONG_REGEX_FOR_PASSWORD;
		} else if (!password.equals(comfirm)) {
			errType = ShowDialog.DISMATCH_BETWEEN_PASSWORD_AND_COFIRMEDPASSWORD;
		} else if (inputVerifCode.equals(null) || inputVerifCode.equals("")) {
			errType = ShowDialog.EMPTY_IN_VERIF_CODE_FIELD;
		} else if (!inputVerifCode.equalsIgnoreCase(verifCode.getCode())) {
			errType = ShowDialog.DISMATCH_IN_VERIF_CODE_FIELD;
		} else if ((regResult = dataBase.addAccount(userName, password,
				idenType)) != 0) {
			if (regResult == 1) {
				errType = ShowDialog.REGISTER_FAILURE;
			} else if (regResult == 2) {
				errType = ShowDialog.REGISTER_COLLISION;
			} else {
				errType = ShowDialog.REGISTER_MAXIMUN;
			}
		} else {
			errType = ShowDialog.REGISTER_SUCCESS;
		}
		return errType;
	}

	private int getItemType(String identity) {
		int type = Identity.PROXY;
		if (identity.equals(ConstString.PROXY_STR)) {
			type = Identity.PROXY;
		} else if (identity.equals(ConstString.AUDITOR_STR)) {
			type = Identity.AUDITOR;
		} else if (identity.equals(ConstString.APPROVER_STR)) {
			type = Identity.APPROVER;
		} else {
			type = Identity.ADMINISTRATOR;
		}
		return type;
	}

	public void clearTextField() {
		textUserName.setText("");
		textPassword.setText("");
		textConfirmPassword.setText("");
		textVerifCode.setText("");
		comboBox.setSelectedItem(ConstString.PROXY_STR);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}