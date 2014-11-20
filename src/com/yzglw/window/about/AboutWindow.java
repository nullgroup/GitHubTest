package com.yzglw.window.about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.util.ConstString;

/**
 * @author OSC
 * 
 */
@SuppressWarnings("serial")
public class AboutWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblContact;
	private JLabel lblVersion;
	private JPanel logoPanel;
	private JTextPane textPane;
	private JLabel lblName;

	private JPopupMenu popupMenu;
	private JMenuItem jmiCopy;

	private String systemInfo;

	private Font aboutFont = new Font("微软雅黑", Font.PLAIN, 12);

	public JLabel getContact() {
		return lblContact;
	}

	public void setContact(JLabel lblContact) {
		this.lblContact = lblContact;
	}

	public JLabel getVersion() {
		return lblVersion;
	}

	public void setVersion(JLabel lblVersion) {
		this.lblVersion = lblVersion;
	}

	public JPanel getLogo() {
		return logoPanel;
	}

	public void setLogo(JPanel logoPanel) {
		this.logoPanel = logoPanel;
	}

	public JTextPane getSystemInfo() {
		return textPane;
	}

	public void setSystemInfo(JTextPane textPane) {
		this.textPane = textPane;
	}

	public JLabel getSoftName() {
		return lblName;
	}

	public void setSoftName(JLabel lblName) {
		this.lblName = lblName;
	}

	public static void main(String[] args) {
		new AboutWindow().setVisible(true);
	}

	public AboutWindow() {
		setLookAndFeel();
		setTitle("关于我们");
		setBounds(100, 100, 360, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getSystemInformation();
		initComponents();
	}

	private void getSystemInformation() {
		Properties props = System.getProperties();
		StringBuilder builder = new StringBuilder();
		builder.append("账户名称 : " + props.getProperty("user.name") + "\n");
		builder.append("操作系统名称 : " + props.getProperty("os.name") + "\n");
		builder.append("操作系统架构 : " + props.getProperty("os.arch") + "\n");
		builder.append("操作系统版本 : " + props.getProperty("os.version") + "\n");

		systemInfo = builder.toString();
	}

	private void initComponents() {
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "关于我们",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		titledBorder.setTitleFont(new Font("微软雅黑", Font.BOLD, 12));
		contentPane.setBorder(titledBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel infoPanel = new JPanel();
		TitledBorder textFieldBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, new Color(109, 109, 109), null), "系统信息",
				TitledBorder.CENTER, TitledBorder.TOP, null, null);
		textFieldBorder.setTitleFont(aboutFont);
		infoPanel.setBackground(SystemColor.menu);
		infoPanel.setBorder(textFieldBorder);
		infoPanel.setBounds(57, 160, 240, 160);
		contentPane.add(infoPanel);
		infoPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 228, 136);
		infoPanel.add(scrollPane);

		textPane = new JTextPane();
		textPane.setFont(aboutFont);
		textPane.setText(systemInfo);
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);

		logoPanel = new JPanel();
		logoPanel.setBackground(SystemColor.menu);
		popupMenu = new JPopupMenu();
		jmiCopy = new JMenuItem("复制(Ctrl+C)");
		JLabel lblLogo = new JLabel(new ImageIcon(
				AboutWindow.class
						.getResource("/com/yzglw/resource/logo.png")));
		lblLogo.setBackground(SystemColor.menu);
		JLabel lblDeveloper = new JLabel("开发者 :");
		JLabel lblContactUs = new JLabel("联系我们 :");
		JLabel lblYg = new JLabel("余果");
		JLabel lblGz = new JLabel("高志");
		JLabel lblLy = new JLabel("李渊");
		JLabel lblZrq = new JLabel("张汝倩");
		JLabel lblWzx = new JLabel("伍至煊");
		lblContact = new JLabel("<html><u>@双子星人23333</u></html>");
		JButton btnContinue = new JButton("确定");
		lblName = new JLabel(ConstString.NAME_OF_SOFTWARE);
		lblVersion = new JLabel("v1.0");

		lblDeveloper.setHorizontalAlignment(SwingConstants.LEFT);

		lblYg.setForeground(Color.BLUE);
		lblGz.setForeground(Color.BLUE);
		lblLy.setForeground(Color.BLUE);
		lblZrq.setForeground(Color.BLUE);
		lblWzx.setForeground(Color.BLUE);

		textPane.setBounds(6, 18, 228, 136);
		logoPanel.setBounds(57, 20, 64, 64);
		lblDeveloper.setBounds(57, 90, 53, 15);
		lblContactUs.setBounds(57, 135, 65, 15);
		lblYg.setBounds(140, 90, 30, 15);
		lblGz.setBounds(140, 110, 30, 15);
		lblLy.setBounds(175, 110, 30, 15);
		lblZrq.setBounds(175, 90, 40, 15);
		lblWzx.setBounds(210, 110, 40, 15);
		lblContact.setBounds(140, 135, 100, 15);
		btnContinue.setBounds(132, 330, 90, 25);
		lblName.setBounds(140, 30, 100, 15);
		lblVersion.setBounds(140, 55, 54, 15);

		lblDeveloper.setFont(aboutFont);
		lblContactUs.setFont(aboutFont);
		lblYg.setFont(aboutFont);
		lblGz.setFont(aboutFont);
		lblLy.setFont(aboutFont);
		lblZrq.setFont(aboutFont);
		lblWzx.setFont(aboutFont);
		lblContact.setFont(aboutFont);
		btnContinue.setFont(aboutFont);
		lblName.setFont(aboutFont);
		lblVersion.setFont(aboutFont);
		jmiCopy.setFont(aboutFont);

		addPopup(textPane, popupMenu);
		popupMenu.add(jmiCopy);

		lblYg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGz.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblZrq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblWzx.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblContactUs.setHorizontalAlignment(SwingConstants.LEFT);

		lblYg.addMouseListener(invokeBrowser);
		lblGz.addMouseListener(invokeBrowser);
		lblLy.addMouseListener(invokeBrowser);
		lblZrq.addMouseListener(invokeBrowser);
		lblWzx.addMouseListener(invokeBrowser);
		btnContinue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jmiCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextPane call = (JTextPane) popupMenu.getInvoker();
				call.copy();
			}
		});

		contentPane.add(logoPanel);
		contentPane.add(lblDeveloper);
		contentPane.add(lblContactUs);
		contentPane.add(lblYg);
		contentPane.add(lblGz);
		contentPane.add(lblLy);
		contentPane.add(lblZrq);
		contentPane.add(lblWzx);
		contentPane.add(lblContact);
		contentPane.add(btnContinue);
		contentPane.add(lblName);
		contentPane.add(lblVersion);
		logoPanel.setLayout(new BorderLayout(0, 0));

		logoPanel.add(lblLogo);
	}

	private MouseListener invokeBrowser = new MouseListener() {
		@Override
		public void mouseClicked(MouseEvent e) {
			JLabel jlbSource = (JLabel) e.getSource();
			String name = jlbSource.getText();
			String url = null;
			int nameVal = 0;

			nameVal = parseName(name);

			switch (nameVal) {
			case (ConstString.ID_YUGUO):
				url = new String(ConstString.URL_YUGUO);
				break;
			case (ConstString.ID_LIYUAN):
				url = new String(ConstString.URL_LIYUAN);
				break;
			case (ConstString.ID_GAOZHI):
				url = new String(ConstString.URL_GAOZHI);
				break;
			case (ConstString.ID_ZHANGRUQIAN):
				url = new String(ConstString.URL_ZHANGRUQIAN);
				break;
			default:
				url = new String(ConstString.URL_WUZHIXUAN);
			}

			invoke(url);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	};

	private void invoke(final String url) {
		try {
			URI uri = new URI(url);
			Desktop.getDesktop().browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int parseName(final String name) {
		int result = 0;
		if (name.equals(ConstString.YUGUO)) {
			result = 1;
		} else if (name.equals(ConstString.LIYUAN)) {
			result = 2;
		} else if (name.equals(ConstString.GAOZHI)) {
			result = 3;
		} else if (name.equals(ConstString.ZHANGRUQIAN)) {
			result = 4;
		} else {
			result = 5;
		}
		return result;
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
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
