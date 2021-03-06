package com.yzglw.identity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.yzglw.database.Database;
import com.yzglw.dialog.AdmitInfoDialog;
import com.yzglw.dialog.DataBaseError;
import com.yzglw.dialog.ShowDialog;
import com.yzglw.lx.PhysicalApplication;
import com.yzglw.lx.ReportCard;
import com.yzglw.main.Main;
import com.yzglw.util.ConstString;
import com.yzglw.util.MyTableModel;
import com.yzglw.window.about.AboutWindow;

/**
 * @author OSC
 * 
 */
@SuppressWarnings("serial")
public class ApproverWindow extends JFrame {

	private Database dataBase;
	private Container contentPane;
	private static JTable table;
	private TableRowSorter<MyTableModel> sorter;

	private JTextField txtName;
	private JTextField txtHigh;
	private JTextField txtEmail;
	private JTextArea txtResume;
	private JTextField txtChn;
	private JTextField txtMth;
	private JTextField txtEng;
	private JTextField txtSci;
	private JTextField txtTotal;
	private JTextArea txtReturn;

	private JButton btnApp;
	private JRadioButton rdBtnYes;
	private JRadioButton rdBtnNo;
	private ButtonGroup btnGroup;

	private boolean isOnApp = false;

	private Font mainFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);

	static final String[] TABLE_HEADER = new String[] { "ID", "学生姓名", "总分",
			"目标大学", "目标专业" };

	public static MyTableModel tableModel = new MyTableModel(new Object[][] {},
			TABLE_HEADER) {
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] { Integer.class, String.class,
				Integer.class, String.class, String.class };

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return columnTypes[columnIndex];
		}
	};

	public ApproverWindow() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(700, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMenuBar();
		try {
			initDataBase();
			initComponents();
		} catch (Exception ex) {
			DataBaseError errDialog = new DataBaseError(ex.getMessage());
			errDialog.setLocationRelativeTo(contentPane);
			errDialog.setVisible(true);
			ex.printStackTrace();
			System.exit(0);
		}
	}

	private void initDataBase() throws ClassNotFoundException,
			FileNotFoundException, IOException, SQLException {
		dataBase = new Database();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("文件");
		menuFile.setFont(mainFont);
		menuBar.add(menuFile);

		JMenuItem itemRefresh = new JMenuItem("刷新表格");
		itemRefresh.setFont(mainFont);
		itemRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearInfo();
				try {
					initTable();
				} catch (SQLException ex) {
					DataBaseError errDialog = new DataBaseError(ex.getMessage());
					errDialog.setLocationRelativeTo(contentPane);
					errDialog.setVisible(true);
					ex.printStackTrace();
					System.exit(0);
				}
			}
		});
		menuFile.add(itemRefresh);

		JMenuItem itemLogOff = new JMenuItem("注销");
		itemLogOff.setFont(mainFont);
		itemLogOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.login.refresh();
				Main.login.setLocationRelativeTo(contentPane);
				dispose();
				Main.login.setVisible(true);
			}
		});
		menuFile.add(itemLogOff);

		JMenuItem itemExit = new JMenuItem("退出");
		itemExit.setFont(mainFont);
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(itemExit);

		JMenu menuOper = new JMenu("操作");
		menuOper.setFont(mainFont);
		menuBar.add(menuOper);

		JMenuItem itemAdmitInfo = new JMenuItem("查看录取信息");
		itemAdmitInfo.setFont(mainFont);
		itemAdmitInfo.addActionListener(queryAdmitInfo);
		menuOper.add(itemAdmitInfo);

		JMenuItem itemAdmit = new JMenuItem("录取申请表");
		itemAdmit.setFont(mainFont);
		itemAdmit.addActionListener(admitApplication);
		menuOper.add(itemAdmit);

		JMenuItem itemDelete = new JMenuItem("淘汰申请表");
		itemDelete.setFont(mainFont);
		itemDelete.addActionListener(deleteApplication);
		menuOper.add(itemDelete);

		JMenuItem itemReturn = new JMenuItem("批复反馈");
		itemReturn.setFont(mainFont);
		itemReturn.addActionListener(approveApplication);
		menuOper.add(itemReturn);

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

	private void initComponents() throws SQLException {
		contentPane = getContentPane();

		contentPane.setBackground(SystemColor.menu);
		contentPane.setLayout(null);

		JPanel userPanel = new JPanel();
		userPanel.setBackground(SystemColor.menu);
		userPanel.setBounds(0, 0, 684, 212);
		TitledBorder userAccountBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "申请表管理",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		userPanel.setBorder(userAccountBorder);
		userAccountBorder.setTitleFont(boldFont);
		contentPane.add(userPanel);
		userPanel.setLayout(null);

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(true);
		table.setFont(mainFont);
		table.getTableHeader().setFont(boldFont);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		sorter = new TableRowSorter<MyTableModel>(tableModel);
		table.setRowSorter(sorter);
		table.setBounds(10, 22, 664, 128);

		DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
		tableRenderer.setHorizontalAlignment(JLabel.LEFT);
		table.setDefaultRenderer(Object.class, tableRenderer);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setSize(664, 179);
		scrollPane.setLocation(10, 20);
		scrollPane.setViewportView(table);
		userPanel.add(scrollPane);

		JPanel idNumberPanel = new JPanel();
		idNumberPanel.setBackground(SystemColor.menu);
		TitledBorder idenAssignBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "操作管理",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		idenAssignBorder.setTitleFont(boldFont);
		idNumberPanel.setBorder(idenAssignBorder);
		idNumberPanel.setBounds(0, 212, 280, 224);
		contentPane.add(idNumberPanel);
		idNumberPanel.setLayout(null);

		JButton btnAdmit = new JButton("录取");
		btnAdmit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdmit.setFont(boldFont);
		btnAdmit.setBounds(15, 25, 80, 30);
		btnAdmit.addActionListener(admitApplication);
		idNumberPanel.add(btnAdmit);

		btnApp = new JButton("批复");
		btnApp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApp.setFont(boldFont);
		btnApp.setBounds(185, 25, 80, 30);
		btnApp.addActionListener(approveApplication);
		idNumberPanel.add(btnApp);

		JButton btnDelete = new JButton("淘汰");
		btnDelete.setFont(boldFont);
		btnDelete.setBounds(100, 25, 80, 30);
		btnDelete.addActionListener(deleteApplication);
		idNumberPanel.add(btnDelete);

		JButton btnCheckAdmit = new JButton("查看录取信息");
		btnCheckAdmit.setFont(boldFont);
		btnCheckAdmit.setBounds(90, 170, 100, 30);
		btnCheckAdmit.addActionListener(queryAdmitInfo);
		idNumberPanel.add(btnCheckAdmit);

		JLabel lblReturn = new JLabel("批复意见");
		lblReturn.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturn.setFont(mainFont);
		lblReturn.setBounds(10, 75, 55, 20);
		idNumberPanel.add(lblReturn);

		txtReturn = new JTextArea();
		txtReturn.setLineWrap(true);
		txtReturn.setDisabledTextColor(SystemColor.textText);
		txtReturn.setEnabled(false);
		txtReturn.setFont(mainFont);
		txtReturn.setBounds(70, 75, 170, 75);
		idNumberPanel.add(txtReturn);

		JPanel otherControlPanel = new JPanel();
		otherControlPanel.setBackground(SystemColor.menu);
		TitledBorder otherBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "申请表详情",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		otherControlPanel.setBorder(otherBorder);
		otherBorder.setTitleFont(boldFont);
		otherControlPanel.setBounds(280, 212, 404, 224);
		contentPane.add(otherControlPanel);
		otherControlPanel.setLayout(null);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(SystemColor.menu);
		TitledBorder infoBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "学生信息",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		infoPanel.setBorder(infoBorder);
		infoBorder.setTitleFont(boldFont);
		infoPanel.setBounds(6, 23, 230, 195);
		otherControlPanel.add(infoPanel);
		infoPanel.setLayout(null);

		JLabel lblName = new JLabel("姓名");
		lblName.setFont(mainFont);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(6, 25, 50, 30);
		infoPanel.add(lblName);

		JLabel lblHigh = new JLabel("高中");
		lblHigh.setFont(mainFont);
		lblHigh.setHorizontalAlignment(SwingConstants.CENTER);
		lblHigh.setBounds(6, 55, 50, 30);
		infoPanel.add(lblHigh);

		JLabel lblEmail = new JLabel("电子邮箱");
		lblEmail.setFont(mainFont);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setBounds(6, 85, 50, 30);
		infoPanel.add(lblEmail);

		JLabel lblResume = new JLabel("简历");
		lblResume.setFont(mainFont);
		lblResume.setHorizontalAlignment(SwingConstants.CENTER);
		lblResume.setBounds(6, 115, 50, 30);
		infoPanel.add(lblResume);

		txtName = new JTextField();
		txtName.setDisabledTextColor(SystemColor.textText);
		txtName.setFont(mainFont);
		txtName.setHorizontalAlignment(SwingConstants.RIGHT);
		txtName.setEnabled(false);
		txtName.setEditable(false);
		txtName.setBounds(72, 25, 150, 30);
		infoPanel.add(txtName);
		txtName.setColumns(10);

		txtHigh = new JTextField();
		txtHigh.setDisabledTextColor(SystemColor.textText);
		txtHigh.setFont(mainFont);
		txtHigh.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHigh.setEnabled(false);
		txtHigh.setEditable(false);
		txtHigh.setBounds(72, 55, 150, 30);
		infoPanel.add(txtHigh);
		txtHigh.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setDisabledTextColor(SystemColor.textText);
		txtEmail.setFont(mainFont);
		txtEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEmail.setEnabled(false);
		txtEmail.setEditable(false);
		txtEmail.setBounds(72, 85, 150, 30);
		infoPanel.add(txtEmail);
		txtEmail.setColumns(10);

		txtResume = new JTextArea();
		txtResume.setLineWrap(true);
		txtResume.setDisabledTextColor(SystemColor.textText);
		txtResume.setFont(mainFont);
		txtResume.setEnabled(false);
		txtResume.setEditable(false);
		txtResume.setBounds(72, 115, 150, 74);
		infoPanel.add(txtResume);

		JPanel gradePanel = new JPanel();
		TitledBorder gradeBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "成绩单信息",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		gradePanel.setBorder(gradeBorder);
		gradeBorder.setTitleFont(boldFont);
		gradePanel.setBackground(SystemColor.menu);
		gradePanel.setBounds(238, 23, 160, 195);
		otherControlPanel.add(gradePanel);
		gradePanel.setLayout(null);

		JLabel lblChn = new JLabel("语文");
		lblChn.setFont(mainFont);
		lblChn.setHorizontalAlignment(SwingConstants.CENTER);
		lblChn.setBounds(6, 25, 55, 24);
		gradePanel.add(lblChn);

		JLabel lblMth = new JLabel("数学");
		lblMth.setFont(mainFont);
		lblMth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMth.setBounds(6, 49, 55, 24);
		gradePanel.add(lblMth);

		JLabel lblEng = new JLabel("英语");
		lblEng.setFont(mainFont);
		lblEng.setHorizontalAlignment(SwingConstants.CENTER);
		lblEng.setBounds(6, 73, 55, 24);
		gradePanel.add(lblEng);

		JLabel lblSci = new JLabel("综合");
		lblSci.setHorizontalAlignment(SwingConstants.CENTER);
		lblSci.setFont(mainFont);
		lblSci.setBounds(6, 97, 55, 24);
		gradePanel.add(lblSci);

		JLabel lblTotal = new JLabel("总分");
		lblTotal.setFont(mainFont);
		lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotal.setBounds(6, 121, 55, 24);
		gradePanel.add(lblTotal);

		JLabel lblCheck = new JLabel("盖章");
		lblCheck.setFont(mainFont);
		lblCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheck.setBounds(6, 155, 55, 20);
		gradePanel.add(lblCheck);

		txtChn = new JTextField();
		txtChn.setHorizontalAlignment(SwingConstants.RIGHT);
		txtChn.setDisabledTextColor(SystemColor.textText);
		txtChn.setEnabled(false);
		txtChn.setEditable(false);
		txtChn.setFont(mainFont);
		txtChn.setBounds(94, 25, 60, 24);
		gradePanel.add(txtChn);
		txtChn.setColumns(10);

		txtMth = new JTextField();
		txtMth.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMth.setDisabledTextColor(SystemColor.textText);
		txtMth.setFont(mainFont);
		txtMth.setEnabled(false);
		txtMth.setEditable(false);
		txtMth.setColumns(10);
		txtMth.setBounds(94, 49, 60, 24);
		gradePanel.add(txtMth);

		txtEng = new JTextField();
		txtEng.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEng.setDisabledTextColor(SystemColor.textText);
		txtEng.setFont(mainFont);
		txtEng.setEditable(false);
		txtEng.setEnabled(false);
		txtEng.setColumns(10);
		txtEng.setBounds(94, 73, 60, 24);
		gradePanel.add(txtEng);

		txtSci = new JTextField();
		txtSci.setHorizontalAlignment(SwingConstants.RIGHT);
		txtSci.setDisabledTextColor(SystemColor.textText);
		txtSci.setFont(mainFont);
		txtSci.setEnabled(false);
		txtSci.setEditable(false);
		txtSci.setColumns(10);
		txtSci.setBounds(94, 97, 60, 24);
		gradePanel.add(txtSci);

		txtTotal = new JTextField();
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setDisabledTextColor(SystemColor.textText);
		txtTotal.setFont(mainFont);
		txtTotal.setEnabled(false);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		txtTotal.setBounds(94, 121, 60, 24);
		gradePanel.add(txtTotal);

		rdBtnYes = new JRadioButton("是");
		rdBtnYes.setFont(mainFont);
		rdBtnYes.setBounds(69, 155, 40, 20);
		rdBtnYes.setSelected(true);
		rdBtnYes.setEnabled(false);
		gradePanel.add(rdBtnYes);

		rdBtnNo = new JRadioButton("否");
		rdBtnNo.setFont(mainFont);
		rdBtnNo.setBounds(114, 155, 40, 20);
		rdBtnNo.setEnabled(false);
		gradePanel.add(rdBtnNo);

		btnGroup = new ButtonGroup();
		btnGroup.add(rdBtnYes);
		btnGroup.add(rdBtnNo);

		table.addMouseListener(clickUpdate);
		initTable();
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ActionListener admitApplication = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			int id = (Integer) table.getValueAt(selectedRow, 0);
			PhysicalApplication app = getApplicationById(selectedRow, id);
			String majName = (String) table.getValueAt(selectedRow, 4);

			try {
				if (dataBase.hasTarUniAndTarMajMatched(app)
						&& dataBase.notFull(majName)
						&& app.getReportCard().isChecked()) {
					dataBase.admitAndPlusByMajName(majName);
					dataBase.deleteApp(id);
					clearInfo();
					tableModel.removeRow(selectedRow);
					updateTable(tableModel);
				} else if (!app.getReportCard().isChecked()) {
					ShowDialog dialog = new ShowDialog("无法录取, 成绩单未盖章");
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
				} else if (!dataBase.hasTarUniAndTarMajMatched(app)) {
					ShowDialog dialog = new ShowDialog("无法录取, 该学校无此专业");
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
				} else if (!dataBase.notFull(majName)) {
					ShowDialog dialog = new ShowDialog("无法录取, 专业招满");
					dialog.setLocationRelativeTo(contentPane);
					dialog.setVisible(true);
				} else {

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

	private ActionListener approveApplication = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int selecedRow = table.getSelectedRow();

			if (selecedRow < 0) {
				return;
			}

			int id = (Integer) table.getValueAt(selecedRow, 0);
			try {
				if (!isOnApp) {
					isOnApp = true;
					btnApp.setText("保存");
					txtReturn.setEnabled(true);
				} else {
					isOnApp = false;
					btnApp.setText("批复");
					txtReturn.setEnabled(false);
					dataBase.setAdvice(id, txtReturn.getText());
				}
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
		}
	};

	private ActionListener deleteApplication = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selecedRow = table.getSelectedRow();

			if (selecedRow < 0) {
				return;
			}

			int id = (Integer) table.getValueAt(selecedRow, 0);

			try {
				dataBase.deleteApp(id);
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				System.exit(0);
				ex.printStackTrace();
			}
			clearInfo();
			tableModel.removeRow(selecedRow);
			updateTable(tableModel);
		}
	};

	private ActionListener queryAdmitInfo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			AdmitInfoDialog admitInfo = new AdmitInfoDialog();
			admitInfo.setLocationRelativeTo(contentPane);
			admitInfo.setVisible(true);
		}
	};

	private MouseAdapter clickUpdate = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			if (isOnApp) {
				isOnApp = false;
				btnApp.setText("批复");
				txtReturn.setEnabled(false);
			}

			int id = (Integer) table.getValueAt(selectedRow, 0);

			txtName.setText(dataBase.getEduInfoById(id)[0]);
			txtHigh.setText(dataBase.getEduInfoById(id)[1]);
			txtEmail.setText(dataBase.getEduInfoById(id)[2]);
			txtResume.setText(dataBase.getEduInfoById(id)[3]);
			txtReturn.setText(dataBase.getEduInfoById(id)[4]);
			txtChn.setText(String.valueOf(dataBase.getGradeInfoById(id)[0]));
			txtMth.setText(String.valueOf(dataBase.getGradeInfoById(id)[1]));
			txtEng.setText(String.valueOf(dataBase.getGradeInfoById(id)[2]));
			txtSci.setText(String.valueOf(dataBase.getGradeInfoById(id)[3]));
			txtTotal.setText(String.valueOf(dataBase.getGradeInfoById(id)[0]
					+ dataBase.getGradeInfoById(id)[1]
					+ dataBase.getGradeInfoById(id)[2]
					+ dataBase.getGradeInfoById(id)[3]));
			if (dataBase.getCheckInfo(id) == 1) {
				rdBtnYes.setSelected(true);
			} else {
				rdBtnNo.setSelected(true);
			}
		}
	};

	private void initTable() throws SQLException {
		int[] resultOfId = dataBase.getAllId();
		String[] resultOfName = dataBase.getAllName();
		int[] resultOfScore = dataBase.getAllScore();
		String[] resultOfTarUni = dataBase.getAllTargetUni();
		String[] resultOfTarMaj = dataBase.getAllTargetMaj();

		dataBase.sort();

		int rowCount = 0;
		for (rowCount = tableModel.getRowCount(); rowCount > 0; rowCount--) {
			tableModel.removeRow(rowCount - 1);
		}
		updateTable(tableModel);

		int i = 0;
		while (resultOfId[i] != 0) {
			tableModel.addRow(new Object[] { 0, "-", 0, "-", "-" });
			tableModel.setValueAt(resultOfId[i], i, 0);
			i++;
		}

		i = 0;
		while (resultOfName[i] != null) {
			tableModel.setValueAt(resultOfName[i], i, 1);
			i++;
		}

		i = 0;
		while (resultOfScore[i] != 0) {
			tableModel.setValueAt(resultOfScore[i], i, 2);
			i++;
		}

		i = 0;
		while (resultOfTarUni[i] != null) {
			tableModel.setValueAt(resultOfTarUni[i], i, 3);
			i++;
		}

		i = 0;
		while (resultOfTarMaj[i] != null) {
			tableModel.setValueAt(resultOfTarMaj[i], i, 4);
			i++;
		}
		updateTable(tableModel);
	}

	private void clearInfo() {
		txtName.setText("");
		txtHigh.setText("");
		txtEmail.setText("");
		txtResume.setText("");
		txtChn.setText("");
		txtMth.setText("");
		txtEng.setText("");
		txtSci.setText("");
		txtTotal.setText("");
		txtReturn.setText("");
		rdBtnYes.setSelected(true);
	}

	private PhysicalApplication getApplicationById(int row, int id) {
		String[] infoArray = { dataBase.getEduInfoById(id)[0],
				dataBase.getEduInfoById(id)[1], dataBase.getEduInfoById(id)[2],
				dataBase.getEduInfoById(id)[3] };
		int[] gradeArray = { dataBase.getGradeInfoById(id)[0],
				dataBase.getGradeInfoById(id)[1],
				dataBase.getGradeInfoById(id)[2],
				dataBase.getGradeInfoById(id)[3] };

		String tarUni = (String) table.getValueAt(row, 3);
		String tarMaj = (String) table.getValueAt(row, 4);

		PhysicalApplication app = new PhysicalApplication(new ReportCard(
				gradeArray, infoArray), tarUni, tarMaj);

		if (dataBase.getCheckInfo(id) == 1) {
			app.getReportCard().check();
		}

		return app;
	}

	public static void updateTable(MyTableModel tableModel) {
		table.setModel(tableModel);
	}

	public static void main(String[] args) {
		new ApproverWindow().setVisible(true);
		;
	}
}
