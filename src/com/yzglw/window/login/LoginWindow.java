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
import java.sql.SQLException;

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
import com.yzglw.identity.AdminWindow;
import com.yzglw.identity.ApproverWindow;
import com.yzglw.identity.AuditorWindow;
import com.yzglw.identity.Identity;
import com.yzglw.identity.ProxyWindow;
import com.yzglw.util.ConstString;
import com.yzglw.util.VerificationCode;

/**
 * @author OSC
 *
 */
@SuppressWarnings("serial")
public class LoginWindow extends JFrame {

	private final String[] IDENTITY_GROUP = { ConstString.PROXY_STR,
			ConstString.AUDITOR_STR, ConstString.APPROVER_STR,
			ConstString.ADMINISTRATOR_STR };

	private Database dataBase;
	private JPanel contentPane;
	private VerificationCode verifCode;
	private JTextField textUserName;
	private JPasswordField textPassword;
	private JTextField textVerifCode;
	private JComboBox<String> comboBox;

	private JPopupMenu popupMenu;
	private JPopupMenu jPopupMenu1;
	private JMenuItem jmiCut, jmiCopy, jmiPaste;

	private Font loginFont = new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 12);

	public static void main(String[] args) {
		new LoginWindow();
	}
	
	public void refresh() {
		verifCode.nextCode();
		textVerifCode.setText("");
		comboBox.setSelectedItem(ConstString.PROXY_STR);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LoginWindow() {
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
		int[] numOfIdenArray = dataBase.getIdentityNumber();
		Identity.setNumOfProxy(numOfIdenArray[0]);
		Identity.setNumOfAuditor(numOfIdenArray[1]);
		Identity.setNumOfApprover(numOfIdenArray[2]);
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0,
				0)), null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(SystemColor.menu);
		Font textFieldFont = new Font("Lucida Sans Unicode", Font.PLAIN, 12);
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				ConstString.NAME_OF_SOFTWARE, TitledBorder.LEADING,
				TitledBorder.TOP, null, null);

		titledBorder.setTitleFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		loginPanel.setBorder(titledBorder);
		contentPane.add(loginPanel, BorderLayout.CENTER);
		loginPanel.setLayout(null);

		verifCode = new VerificationCode();
		textUserName = new JTextField();
		textPassword = new JPasswordField();
		textVerifCode = new JTextField();
		comboBox = new JComboBox<String>();

		JLabel lblWelcome = new JLabel("»¶Ó­µÇÂ¼");
		JLabel lblUserName = new JLabel("ÓÃ»§Ãû");
		JLabel lblPassword = new JLabel("ÃÜÂë");
		JLabel lblVerifCode = new JLabel("ÑéÖ¤Âë");
		JLabel lblIdentity = new JLabel("Éí·Ý");

		JButton btnLogin = new JButton("µÇÂ¼");
		JButton btnReg = new JButton("×¢²á");

		popupMenu = new JPopupMenu();
		jPopupMenu1 = new JPopupMenu();
		JMenuItem jmiLogin = new JMenuItem("µÇÂ¼");
		JMenuItem jmiReg = new JMenuItem("×¢²á");
		JMenuItem jmiExit = new JMenuItem("ÍË³ö");
		jmiCut = new JMenuItem("¼ôÇÐ(Ctrl+X)");
		jmiCopy = new JMenuItem("¸´ÖÆ(Ctrl+C)");
		jmiPaste = new JMenuItem("Õ³Ìù(Ctrl+V)");

		textUserName.setBounds(200, 120, 150, 25);
		textPassword.setBounds(200, 145, 150, 25);
		textVerifCode.setBounds(200, 200, 50, 25);
		comboBox.setBounds(200, 170, 85, 30);
		btnLogin.setBounds(200, 240, 70, 30);
		btnReg.setBounds(280, 240, 70, 30);
		lblWelcome.setBounds(120, 58, 300, 50);
		lblUserName.setBounds(155, 120, 40, 25);
		lblPassword.setBounds(155, 145, 40, 25);
		lblVerifCode.setBounds(155, 200, 40, 25);
		lblIdentity.setBounds(155, 170, 40, 30);

		textUserName.setFont(textFieldFont);
		textPassword.setFont(textFieldFont);
		textVerifCode.setFont(textFieldFont);
		verifCode.setFont(textFieldFont);
		comboBox.setFont(loginFont);
		btnLogin.setFont(loginFont);
		btnReg.setFont(loginFont);
		lblUserName.setFont(loginFont);
		lblPassword.setFont(loginFont);
		lblVerifCode.setFont(loginFont);
		lblIdentity.setFont(loginFont);
		jmiLogin.setFont(loginFont);
		jmiReg.setFont(loginFont);
		jmiExit.setFont(loginFont);
		jmiCut.setFont(loginFont);
		jmiCopy.setFont(loginFont);
		jmiPaste.setFont(loginFont);

		lblWelcome.setForeground(Color.DARK_GRAY);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 30));

		textUserName.setColumns(10);
		textVerifCode.setColumns(10);

		textUserName.setDropMode(DropMode.INSERT);

		verifCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		verifCode.setSize(95, 40);
		verifCode.setLocation(254, 200);

		comboBox.setModel(new DefaultComboBoxModel<String>(IDENTITY_GROUP));

		textUserName.addActionListener(loginAction);
		textPassword.addActionListener(loginAction);
		textVerifCode.addActionListener(loginAction);
		btnLogin.addActionListener(loginAction);
		btnReg.addActionListener(regAction);
		jmiLogin.addActionListener(loginAction);
		jmiReg.addActionListener(regAction);
		jmiExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jmiCut.addActionListener(cutAction);
		jmiCopy.addActionListener(copyAction);
		jmiPaste.addActionListener(pasteAction);
		
		btnLogin.setToolTipText("<html><body><font face=\"Î¢ÈíÑÅºÚ\" size=3>ÒÔµ±Ç°Éí·ÝµÇÂ¼</font></body></html>");
		btnReg.setToolTipText("<html><body><font face=\"Î¢ÈíÑÅºÚ\" size=3>½øÈë×¢²á½çÃæ(ÐèÒª¿ª·¢Õß¿ÚÁî)</font></body></html>");

		lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVerifCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIdentity.setHorizontalAlignment(SwingConstants.RIGHT);

		lblUserName.setLabelFor(textUserName);
		lblPassword.setLabelFor(textPassword);
		lblVerifCode.setLabelFor(textVerifCode);
		lblIdentity.setLabelFor(comboBox);

		addPopup(this, popupMenu);
		addPopup(textUserName, jPopupMenu1);
		addPopup(textPassword, jPopupMenu1);
		addPopup(textVerifCode, jPopupMenu1);

		popupMenu.add(jmiLogin);
		popupMenu.add(jmiReg);
		popupMenu.add(jmiExit);
		jPopupMenu1.add(jmiCut);
		jPopupMenu1.add(jmiCopy);
		jPopupMenu1.add(jmiPaste);

		loginPanel.add(textPassword);
		loginPanel.add(textVerifCode);
		loginPanel.add(textUserName);
		loginPanel.add(verifCode);
		loginPanel.add(comboBox);
		loginPanel.add(btnLogin);
		loginPanel.add(btnReg);
		loginPanel.add(lblWelcome);
		loginPanel.add(lblUserName);
		loginPanel.add(lblPassword);
		loginPanel.add(lblVerifCode);
		loginPanel.add(lblIdentity);
	}

	private ActionListener loginAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String identity = (String) comboBox.getSelectedItem();

			int ideType = getItemType(identity);
			try {
				int errType = validate(ideType);

				if (errType != ShowDialog.PASS) {
					JDialog dialog = new ShowDialog(errType);
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
					verifCode.nextCode();
					return;
				}
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}

			switch (ideType) {
			case (Identity.PROXY):
				ProxyWindow proxy = new ProxyWindow();
				proxy.setLocationRelativeTo(contentPane);
				dispose();
				proxy.setVisible(true);
				break;
			case (Identity.AUDITOR):
				AuditorWindow auditor = new AuditorWindow();
				auditor.setLocationRelativeTo(contentPane);
				dispose();
				auditor.setVisible(true);
				break;
			case (Identity.APPROVER):
				ApproverWindow approver = new ApproverWindow();
				approver.setLocationRelativeTo(contentPane);
				dispose();
				approver.setVisible(true);
				break;
			case (Identity.ADMINISTRATOR):
				AdminWindow admin = new AdminWindow();
				admin.setLocationRelativeTo(contentPane);
				dispose();
				admin.setVisible(true);
				break;
			default:
				break;
			}
		}
	};

	private ActionListener regAction = new ActionListener() {
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

	private int validate(final int idenType) throws SQLException {
		String userName = textUserName.getText();
		String password = new String(textPassword.getPassword());
		String inputVerifCode = textVerifCode.getText();

		int errType = 0;

		if (userName.equals(null) || userName.equals("")) {
			errType = ShowDialog.EMPTY_IN_USERNAME_FIELD;
		} else if (password.equals(null) || password.equals("")) {
			errType = ShowDialog.EMPTY_IN_PASSWORD_FIELD;
		} else if (inputVerifCode.equals(null) || inputVerifCode.equals("")) {
			errType = ShowDialog.EMPTY_IN_VERIF_CODE_FIELD;
		} else if (!inputVerifCode.equalsIgnoreCase(verifCode.getCode())) {
			errType = ShowDialog.DISMATCH_IN_VERIF_CODE_FIELD;
		} else if (!dataBase.login(userName, password, idenType)) {
			errType = ShowDialog.DISMATCH_BETWEEN_USERNAME_AND_PASSWORD;
		} else {
			errType = ShowDialog.PASS;
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