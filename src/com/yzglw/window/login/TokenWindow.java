package com.yzglw.window.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
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

import com.yzglw.dialog.ShowDialog;
import com.yzglw.main.Main;
import com.yzglw.util.ConstString;
import com.yzglw.window.about.AboutWindow;

/**
 * @author OSC
 *
 */
@SuppressWarnings("serial")
public class TokenWindow extends JFrame {

	private JPanel contentPane;
	private JPasswordField tokenField;

	private JPopupMenu popupMenu;
	private JPopupMenu jPopupMenu1;
	private JMenuItem jmiCut, jmiCopy, jmiPaste;

	private Font tokenFont = new Font("微软雅黑", Font.BOLD, 12);

	private static String token = " ";

	public static String getToken() {
		return token;
	}

	public static void setToken(String newToken) {
		token = newToken;
	}

	public static void main(String[] args) {
		new TokenWindow().setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TokenWindow() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(450, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0,
				0)), null));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel tokenPanel = new JPanel();
		tokenPanel.setBackground(SystemColor.menu);
		Font lightFont = new Font("微软雅黑", Font.PLAIN, 12);
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null),
				ConstString.NAME_OF_SOFTWARE, TitledBorder.LEADING,
				TitledBorder.TOP, null, null);

		titledBorder.setTitleFont(tokenFont);
		tokenPanel.setBorder(titledBorder);
		contentPane.add(tokenPanel, BorderLayout.CENTER);
		tokenPanel.setLayout(null);

		tokenField = new JPasswordField();

		JLabel lblToken = new JLabel("请输入开发者口令");
		JButton btnContinue = new JButton("继续");
		JButton btnReturn = new JButton("返回");
		JLabel lblMessageA = new JLabel("* 开发者口令由所有内部开发者共享, 用于注册账户操作");

		popupMenu = new JPopupMenu();
		jPopupMenu1 = new JPopupMenu();
		JMenuItem jmiContinue = new JMenuItem("继续");
		JMenuItem jmiReturn = new JMenuItem("返回");
		JMenuItem jmiExit = new JMenuItem("退出");
		jmiCut = new JMenuItem("剪切(Ctrl+X)");
		jmiCopy = new JMenuItem("复制(Ctrl+C)");
		jmiPaste = new JMenuItem("粘贴(Ctrl+V)");

		lblToken.setBounds(100, 100, 100, 25);
		tokenField.setBounds(210, 100, 120, 25);
		btnContinue.setBounds(140, 140, 70, 30);
		btnReturn.setBounds(220, 140, 70, 30);
		lblMessageA.setBounds(80, 180, 300, 50);

		lblToken.setFont(tokenFont);
		btnContinue.setFont(tokenFont);
		btnReturn.setFont(tokenFont);
		tokenField.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		lblMessageA.setFont(lightFont);
		jmiContinue.setFont(lightFont);
		jmiReturn.setFont(lightFont);
		jmiExit.setFont(lightFont);
		jmiCut.setFont(lightFont);
		jmiCopy.setFont(lightFont);
		jmiPaste.setFont(lightFont);

		tokenField.addActionListener(continueAction);
		btnContinue.addActionListener(continueAction);
		btnReturn.addActionListener(returnAction);
		jmiContinue.addActionListener(continueAction);
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

		lblToken.setHorizontalAlignment(SwingConstants.CENTER);

		lblToken.setLabelFor(tokenField);

		addPopup(this, popupMenu);
		addPopup(tokenField, jPopupMenu1);

		popupMenu.add(jmiContinue);
		popupMenu.add(jmiReturn);
		popupMenu.add(jmiExit);
		jPopupMenu1.add(jmiCut);
		jPopupMenu1.add(jmiCopy);
		jPopupMenu1.add(jmiPaste);

		tokenPanel.add(lblToken);
		tokenPanel.add(tokenField);
		tokenPanel.add(btnContinue);
		tokenPanel.add(btnReturn);
		tokenPanel.add(lblMessageA);

		setVisible(true);
	}

	private ActionListener continueAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String tokenStr = new String(tokenField.getPassword());

			if (!tokenStr.equals(token)) {
				if (tokenStr.equalsIgnoreCase("about")) {
					AboutWindow about = new AboutWindow();
					about.setLocationRelativeTo(contentPane);
					about.setVisible(true);
					tokenField.setText("");
					return;
				}
				JDialog dialog = new ShowDialog(
						ShowDialog.DISMATCH_IN_TOKEN_FIELD);
				dialog.setLocationRelativeTo(contentPane);
				dialog.setVisible(true);
				tokenField.setText("");
				return;
			}

			RegWindow reg = new RegWindow();
			reg.setLocationRelativeTo(contentPane);
			dispose();
			reg.setVisible(true);
		}
	};

	private ActionListener returnAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.login.setLocationRelativeTo(contentPane);
			Main.login.refresh();
			dispose();
			Main.login.setVisible(true);
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
