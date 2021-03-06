package com.yzglw.identity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.database.Database;
import com.yzglw.dialog.AdminDialog;
import com.yzglw.dialog.DataBaseError;
import com.yzglw.dialog.ShowDialog;
import com.yzglw.identity.Identity;
import com.yzglw.main.Main;
import com.yzglw.util.ConstString;
import com.yzglw.util.MyTableModel;
import com.yzglw.window.about.AboutWindow;

/**
 * @author Galdon
 * 
 */
@SuppressWarnings("serial")
public class AdminWindow extends JFrame {

	private Database dataBase;
	private Container contentPane;
	private static JTable table;

	private JSpinner spinPro;
	private JSpinner spinAud;
	private JSpinner spinApp;
	private JLabel lblPro;
	private JLabel lblAud;
	private JLabel lblApp;

	private Font mainFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);

	static final String[] TABLE_HEADER = new String[] { "用户账户", "密码", "用户类型" };

	public static MyTableModel tableModel = new MyTableModel(new Object[][] {},
			TABLE_HEADER) {
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { String.class, String.class,
				String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
	};

	public AdminWindow() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(700, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initDataBase();
		initMenuBar();
		initComponents();
	}

	private void initDataBase() {
		try {
			dataBase = new Database();
		} catch (Exception e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(contentPane);
			errDialog.setVisible(true);
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("文件");
		menuFile.setFont(mainFont);
		menuBar.add(menuFile);

		JMenuItem itemLogOff = new JMenuItem("注销");
		itemLogOff.setFont(mainFont);
		itemLogOff.addActionListener(logOffAdmin);
		menuFile.add(itemLogOff);

		JMenuItem itemExit = new JMenuItem("退出");
		itemExit.setFont(mainFont);
		itemExit.addActionListener(exitAdmin);
		menuFile.add(itemExit);

		JMenu menuOper = new JMenu("操作");
		menuOper.setFont(mainFont);
		menuBar.add(menuOper);

		JMenuItem itemLoad = new JMenuItem("读取账户信息");
		itemLoad.setFont(mainFont);
		itemLoad.addActionListener(loadAccount);
		menuOper.add(itemLoad);

		JMenuItem itemModify = new JMenuItem("修改账户类型");
		itemModify.setFont(mainFont);
		itemModify.addActionListener(modifyAccount);
		menuOper.add(itemModify);

		JMenuItem itemDelete = new JMenuItem("删除账户");
		itemDelete.setFont(mainFont);
		itemDelete.addActionListener(deleteAccount);
		menuOper.add(itemDelete);

		JMenu menuOther = new JMenu("其他");
		menuOther.setFont(mainFont);
		menuBar.add(menuOther);

		JMenuItem itemAbout = new JMenuItem("关于我们");
		itemAbout.setFont(mainFont);
		itemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutWindow aw = new AboutWindow();
				aw.setLocationRelativeTo(contentPane);
				aw.setVisible(true);
			}
		});
		menuOther.add(itemAbout);
	}

	private void initComponents() {
		contentPane = getContentPane();

		contentPane.setBackground(SystemColor.menu);
		contentPane.setLayout(null);

		JPanel userPanel = new JPanel();
		userPanel.setBackground(SystemColor.menu);
		userPanel.setBounds(0, 0, 684, 212);
		TitledBorder userBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "账户管理",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		userPanel.setBorder(userBorder);
		userBorder.setTitleFont(boldFont);
		contentPane.add(userPanel);
		userPanel.setLayout(null);

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(true);
		table.setFont(mainFont);
		table.getTableHeader().setFont(boldFont);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		table.setBounds(10, 22, 664, 128);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(664, 139);
		scrollPane.setLocation(10, 20);
		scrollPane.setViewportView(table);
		userPanel.add(scrollPane);

		JButton btnLoad = new JButton("读取账户信息");
		btnLoad.setFont(mainFont);
		btnLoad.setBounds(10, 160, 150, 40);
		btnLoad.addActionListener(loadAccount);
		userPanel.add(btnLoad);

		JButton btnLogOff = new JButton("退出管理");
		btnLogOff.setFont(mainFont);
		btnLogOff.setBounds(524, 160, 150, 40);
		btnLogOff.addActionListener(logOffAdmin);
		userPanel.add(btnLogOff);

		JButton btnDelete = new JButton("删除账户");
		btnDelete.setFont(mainFont);
		btnDelete.setBounds(354, 160, 150, 40);
		btnDelete.addActionListener(deleteAccount);
		userPanel.add(btnDelete);

		JButton btnModify = new JButton("修改账户类型");
		btnModify.setFont(mainFont);
		btnModify.setBounds(180, 160, 150, 40);
		btnModify.addActionListener(modifyAccount);
		userPanel.add(btnModify);

		JPanel idNumberPanel = new JPanel();
		idNumberPanel.setBackground(SystemColor.menu);
		TitledBorder idNumBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "身份分配管理",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		idNumberPanel.setBorder(idNumBorder);
		idNumBorder.setTitleFont(boldFont);
		idNumberPanel.setBounds(0, 212, 342, 224);
		contentPane.add(idNumberPanel);
		idNumberPanel.setLayout(null);

		JLabel lblNumOfPro = new JLabel("代理专员数量");
		lblNumOfPro.setFont(mainFont);
		lblNumOfPro.setBounds(53, 58, 72, 18);
		idNumberPanel.add(lblNumOfPro);

		JLabel lblNumOfAud = new JLabel("审核专员数量");
		lblNumOfAud.setFont(mainFont);
		lblNumOfAud.setBounds(53, 87, 72, 18);
		idNumberPanel.add(lblNumOfAud);

		JLabel lblNumOfApp = new JLabel("批复专员数量");
		lblNumOfApp.setFont(mainFont);
		lblNumOfApp.setBounds(53, 118, 72, 18);
		idNumberPanel.add(lblNumOfApp);

		JButton btnSave = new JButton("保存");
		btnSave.addActionListener(saveOperation);
		btnSave.setFont(mainFont);
		btnSave.setBounds(92, 162, 156, 40);
		idNumberPanel.add(btnSave);

		JLabel lblMaxOfPro = new JLabel("当前最大数量");
		lblMaxOfPro.setFont(mainFont);
		lblMaxOfPro.setBounds(204, 58, 72, 18);
		idNumberPanel.add(lblMaxOfPro);

		JLabel lblMaxOfAud = new JLabel("当前最大数量");
		lblMaxOfAud.setFont(mainFont);
		lblMaxOfAud.setBounds(204, 88, 72, 18);
		idNumberPanel.add(lblMaxOfAud);

		JLabel lblMaxOfApp = new JLabel("当前最大数量");
		lblMaxOfApp.setFont(mainFont);
		lblMaxOfApp.setBounds(204, 118, 72, 18);
		idNumberPanel.add(lblMaxOfApp);

		spinPro = new JSpinner();
		spinPro.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinPro.setFont(mainFont);
		spinPro.setBounds(137, 52, 55, 30);
		idNumberPanel.add(spinPro);

		spinAud = new JSpinner();
		spinAud.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinAud.setFont(mainFont);
		spinAud.setBounds(137, 81, 55, 30);
		idNumberPanel.add(spinAud);

		spinApp = new JSpinner();
		spinApp.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinApp.setFont(mainFont);
		spinApp.setBounds(137, 112, 55, 30);
		idNumberPanel.add(spinApp);

		lblPro = new JLabel(String.valueOf(Identity.NUM_OF_PROXY));
		lblPro.setBounds(281, 58, 55, 18);

		lblAud = new JLabel(String.valueOf(Identity.NUM_OF_AUDITOR));
		lblAud.setBounds(281, 88, 55, 18);

		lblApp = new JLabel(String.valueOf(Identity.NUM_OF_APPROVER));
		lblApp.setBounds(281, 118, 55, 18);

		if (dataBase.getIdentityNumber()[0] != 0
				&& dataBase.getIdentityNumber()[1] != 0
				&& dataBase.getIdentityNumber()[2] != 0) {
			lblPro.setText(String.valueOf(dataBase.getIdentityNumber()[0]));
			lblAud.setText(String.valueOf(dataBase.getIdentityNumber()[1]));
			lblApp.setText(String.valueOf(dataBase.getIdentityNumber()[2]));
			Identity.setNumOfProxy(dataBase.getIdentityNumber()[0]);
			Identity.setNumOfAuditor(dataBase.getIdentityNumber()[1]);
			Identity.setNumOfApprover(dataBase.getIdentityNumber()[2]);
		} else {
			lblPro.setText(String.valueOf(Identity.NUM_OF_PROXY));
			lblAud.setText(String.valueOf(Identity.NUM_OF_AUDITOR));
			lblApp.setText(String.valueOf(Identity.NUM_OF_APPROVER));
		}

		idNumberPanel.add(lblPro);
		idNumberPanel.add(lblAud);
		idNumberPanel.add(lblApp);

		JPanel listPanel = new JPanel();
		listPanel.setBackground(SystemColor.menu);
		TitledBorder listBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "查看申请表",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		listPanel.setBorder(listBorder);
		listBorder.setTitleFont(boldFont);
		listPanel.setBounds(342, 212, 342, 224);
		contentPane.add(listPanel);
		listPanel.setLayout(null);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ActionListener loadAccount = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] resultOfAccount = null;
			String[] resultOfPassword = null;
			int[] resultOfType = null;
			try {
				resultOfAccount = dataBase.getAllAccount();
				resultOfPassword = dataBase.getAllPassword();
				resultOfType = dataBase.getAllType();
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}

			int rowcount = 0;
			for (rowcount = tableModel.getRowCount(); rowcount > 0; rowcount--)
				tableModel.removeRow(rowcount - 1);
			table.setModel(tableModel);

			int i = 0;
			while (resultOfAccount[i] != null) {
				tableModel.addRow(TABLE_HEADER);
				tableModel.setValueAt(resultOfAccount[i], i, 0);
				i++;
			}
			i = 0;
			while (resultOfPassword[i] != null) {
				tableModel.setValueAt(resultOfPassword[i], i, 1);
				i++;
			}
			i = 0;
			while (resultOfType[i] != 0) {
				switch (resultOfType[i]) {
				case 1:
					tableModel.setValueAt("代理专员", i, 2);
					break;
				case 2:
					tableModel.setValueAt("审查专员", i, 2);
					break;
				case 3:
					tableModel.setValueAt("批复专员", i, 2);
					break;
				case 4:
					tableModel.setValueAt("管理员", i, 2);
					break;
				default:
					break;
				}
				i++;
			}
			updateTable(tableModel);
		}
	};

	private ActionListener modifyAccount = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();
			String curIden = (String) table.getValueAt(selectedRow, 2);

			if (selectedRow < 0) {
				return;
			}

			if (curIden.equals("管理员")) {
				ShowDialog dialog = new ShowDialog("管理员账户无法修改");
				dialog.setLocationRelativeTo(contentPane);
				dialog.setVisible(true);
				return;
			}

			AdminDialog dialog = new AdminDialog();
			dialog.setLocationRelativeTo(contentPane);
			dialog.setVisible(true);
			int idenType = dialog.getSelectedIndex();
			String tarIden = dialog.getSelectedItem();

			if (!dialog.isCancel() || tarIden.equals(curIden)) {
				return;
			}

			String account = (String) table.getValueAt(selectedRow, 0);
			try {
				if (!dataBase.setAccountType(account, idenType)) {
					ShowDialog maxDailog = new ShowDialog("该身份人数已满");
					maxDailog.setLocationRelativeTo(contentPane);
					maxDailog.setVisible(true);
					return;
				}
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
			tableModel.setValueAt(tarIden, selectedRow, 2);
			updateTable(AdminWindow.tableModel);
		}
	};
	private ActionListener deleteAccount = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			String temp = (String) table.getValueAt(selectedRow, 0);

			try {
				if (table.getValueAt(selectedRow, 2).equals("管理员")) {
					ShowDialog dialog = new ShowDialog("管理员账户不能删除");
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
					return;
				} else {
					tableModel.removeRow(selectedRow);
					dataBase.deleteAccount(temp);
				}
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				System.exit(0);
				ex.printStackTrace();
			}
			updateTable(tableModel);
		}
	};

	private ActionListener logOffAdmin = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Main.login.refresh();
			Main.login.setLocationRelativeTo(contentPane);
			dispose();
			Main.login.setVisible(true);
		}
	};

	private ActionListener exitAdmin = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};

	private ActionListener saveOperation = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			int numOfPro = (Integer) spinPro.getValue();
			int numOfAud = (Integer) spinAud.getValue();
			int numOfApp = (Integer) spinApp.getValue();

			if (dataBase.checkNumber(Identity.PROXY) > numOfPro
					|| dataBase.checkNumber(Identity.AUDITOR) > numOfAud
					|| dataBase.checkNumber(Identity.APPROVER) > numOfApp) {
				ShowDialog dialog = new ShowDialog("设定值低于已存在的值");
				dialog.setLocationRelativeTo(contentPane);
				dialog.setVisible(true);
				return;
			}

			ShowDialog okDialog = new ShowDialog("保存成功");
			okDialog.setLocationRelativeTo(contentPane);
			okDialog.setVisible(true);

			Identity.setNumOfProxy(numOfPro);
			Identity.setNumOfAuditor(numOfAud);
			Identity.setNumOfApprover(numOfApp);
			lblPro.setText(String.valueOf(Identity.NUM_OF_PROXY));
			lblAud.setText(String.valueOf(Identity.NUM_OF_AUDITOR));
			lblApp.setText(String.valueOf(Identity.NUM_OF_APPROVER));

			try {
				if (!setTypeNumber(numOfPro, numOfAud, numOfApp)) {
					ShowDialog dialog = new ShowDialog("设置类型失败");
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
				}
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				System.exit(0);
				ex.printStackTrace();
			}
		}
	};

	public void updateTable(MyTableModel tableModel) {
		table.setModel(tableModel);
	}

	public boolean setTypeNumber(int num1, int num2, int num3)
			throws SQLException {
		dataBase.createTypeNumberTable();
		return dataBase.setTypeNumber(num1, num2, num3);
	}

	public static void main(String[] args) {
		new AdminWindow().setVisible(true);
	}
}
