package com.yzglw.identity;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.yzglw.database.Database;
import com.yzglw.dialog.AddDialog;
import com.yzglw.dialog.DataBaseError;
import com.yzglw.dialog.EditDialog;
import com.yzglw.lx.PhysicalApplication;
import com.yzglw.lx.ReportCard;
import com.yzglw.main.Main;
import com.yzglw.util.ConstString;
import com.yzglw.util.RandomApplication;
import com.yzglw.util.MyTableModel;
import com.yzglw.window.about.AboutWindow;

/**
 * @author OSC
 * 
 */
@SuppressWarnings("serial")
public class ProxyWindow extends JFrame {

	private Database dataBase;
	private Container contentPane;
	private static JTable table;
	private static TableRowSorter<MyTableModel> sorter;

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

	private JRadioButton rdBtnYes;
	private JRadioButton rdBtnNo;
	private JSpinner spinNum;

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

	public ProxyWindow() {
		setLookAndFeel();
		setTitle(ConstString.NAME_OF_SOFTWARE);
		setSize(700, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			initDataBase();
			initMenuBar();
			initComponents();
		} catch (Exception e) {
			DataBaseError errDialog = new DataBaseError(e.getMessage());
			errDialog.setLocationRelativeTo(contentPane);
			errDialog.setVisible(true);
			e.printStackTrace();
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

		JMenuItem itemAdd = new JMenuItem("添加申请表");
		itemAdd.setFont(mainFont);
		itemAdd.addActionListener(addApplication);
		menuOper.add(itemAdd);

		JMenuItem itemEdit = new JMenuItem("修改申请表");
		itemEdit.setFont(mainFont);
		itemEdit.addActionListener(editApplication);
		menuOper.add(itemEdit);

		JMenuItem itemDelete = new JMenuItem("撤销申请表");
		itemDelete.setFont(mainFont);
		itemDelete.addActionListener(deleteApplication);
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

		JButton btnAdd = new JButton("添加");
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.setFont(boldFont);
		btnAdd.setBounds(15, 25, 80, 30);
		btnAdd.addActionListener(addApplication);
		idNumberPanel.add(btnAdd);

		JButton btnEdit = new JButton("编辑");
		btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEdit.setFont(boldFont);
		btnEdit.setBounds(100, 25, 80, 30);
		btnEdit.addActionListener(editApplication);
		idNumberPanel.add(btnEdit);

		JButton btnDelete = new JButton("撤销");
		btnDelete.setFont(boldFont);
		btnDelete.setBounds(185, 25, 80, 30);
		btnDelete.addActionListener(deleteApplication);
		idNumberPanel.add(btnDelete);

		JPanel randPanel = new JPanel();
		randPanel.setBackground(SystemColor.menu);
		TitledBorder randBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "随机添加",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		randPanel.setBorder(randBorder);
		randBorder.setTitleFont(boldFont);

		randPanel.setBounds(6, 162, 200, 56);
		idNumberPanel.add(randPanel);
		randPanel.setLayout(new GridLayout(0, 2, 0, 0));

		spinNum = new JSpinner();
		spinNum.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		randPanel.add(spinNum);

		JButton btnRndAdd = new JButton("添加");
		btnRndAdd.setBounds(new Rectangle(0, 0, 0, 30));
		btnRndAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRndAdd.setFont(boldFont);
		btnRndAdd.addActionListener(randomAdd);
		randPanel.add(btnRndAdd);

		JLabel lblReturn = new JLabel("批复反馈");
		lblReturn.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturn.setFont(mainFont);
		lblReturn.setBounds(10, 75, 55, 20);
		idNumberPanel.add(lblReturn);

		txtReturn = new JTextArea();
		txtReturn.setDisabledTextColor(SystemColor.textText);
		txtReturn.setLineWrap(true);
		txtReturn.setEnabled(false);
		txtReturn.setEditable(false);
		txtReturn.setFont(mainFont);
		txtReturn.setBounds(70, 75, 170, 75);
		idNumberPanel.add(txtReturn);

		JPanel otherPanel = new JPanel();
		otherPanel.setBackground(SystemColor.menu);
		TitledBorder otherBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "申请表详情",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(59, 59,
						59));
		otherPanel.setBorder(otherBorder);
		otherBorder.setTitleFont(boldFont);
		otherPanel.setBounds(280, 212, 404, 224);
		contentPane.add(otherPanel);
		otherPanel.setLayout(null);

		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(SystemColor.menu);
		TitledBorder infoBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "学生信息",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		infoPanel.setBorder(infoBorder);
		infoBorder.setTitleFont(boldFont);
		infoPanel.setBounds(6, 23, 230, 195);
		otherPanel.add(infoPanel);
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
		otherPanel.add(gradePanel);
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

		ButtonGroup btnGroup = new ButtonGroup();
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

	private void update(int id) throws SQLException {
		int[] resultOfId = dataBase.getAllId();
		String[] resultOfName = dataBase.getAllName();
		int[] resultOfScore = dataBase.getAllScore();
		String[] resultOfTarUni = dataBase.getAllTargetUni();
		String[] resultOfTarMaj = dataBase.getAllTargetMaj();

		tableModel.addRow(new Object[] { 0, "-", 0, "-", "-" });
		
		int count = 0;
		int row = tableModel.getRowCount() - 1;
		
		while (resultOfName[count++] != null);

		tableModel.setValueAt(resultOfId[count - 2], row, 0);
		tableModel.setValueAt(resultOfName[count - 2], row, 1);
		tableModel.setValueAt(resultOfScore[count - 2], row, 2);
		tableModel.setValueAt(resultOfTarUni[count - 2], row, 3);
		tableModel.setValueAt(resultOfTarMaj[count - 2], row, 4);
		
		updateTable(tableModel);
	}

	private ActionListener addApplication = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			AddDialog addDialog = new AddDialog();
			addDialog.setLocationRelativeTo(contentPane);
			addDialog.setVisible(true);

			if (!addDialog.isAdd()) {
				return;
			}

			try {
				dataBase.addApp(addDialog.getApp());
				int[] idGroup = dataBase.getAllId();
				int id = idGroup[idGroup.length - 1];
				update(id);
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
		}
	};

	private ActionListener editApplication = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			int id = (Integer) table.getValueAt(selectedRow, 0);

			PhysicalApplication app = getApplicationById(selectedRow, id);
			EditDialog editDialog = new EditDialog(app);
			editDialog.setLocationRelativeTo(contentPane);
			editDialog.setVisible(true);

			if (!editDialog.isEdit()) {
				return;
			}

			String chgedUni = editDialog.getUni();
			String chgedMaj = editDialog.getMaj();
			try {
				dataBase.modifyTarUniAndTarMaj(id, chgedUni, chgedMaj);
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
			tableModel.setValueAt(chgedUni, selectedRow, 3);
			tableModel.setValueAt(chgedMaj, selectedRow, 4);
			updateTable(tableModel);
		}
	};

	private ActionListener deleteApplication = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			int id = (Integer) table.getValueAt(selectedRow, 0);

			try {
				dataBase.deleteApp(id);
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
			clearInfo();
			tableModel.removeRow(selectedRow);
			table.setModel(tableModel);
		}
	};

	private MouseAdapter clickUpdate = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int selectedRow = table.getSelectedRow();
			int id = (Integer) table.getValueAt(selectedRow, 0);

			if (selectedRow < 0) {
				return;
			}

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

	private ActionListener randomAdd = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				for (int i = 0, times = (Integer) spinNum.getValue(); i < times; i++) {
					dataBase.addApp(RandomApplication.getInstance());
				}
				initTable();
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(contentPane);
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
		}
	};

	private void initTable() throws SQLException {
		int[] resultOfId = dataBase.getAllId();
		String[] resultOfName = dataBase.getAllName();
		int[] resultOfScore = dataBase.getAllScore();
		String[] resultOfTarUni = dataBase.getAllTargetUni();
		String[] resultOfTarMaj = dataBase.getAllTargetMaj();

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
		sorter.sort();
		table.setModel(tableModel);
	}

	public static void main(String[] args) {
		ProxyWindow proxy = new ProxyWindow();
		proxy.setVisible(true);
	}
}
