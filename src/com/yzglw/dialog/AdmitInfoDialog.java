package com.yzglw.dialog;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.database.Database;
import com.yzglw.lx.PhysicalUniversity;
import com.yzglw.util.RandomApplication;
import com.yzglw.util.MyTableModel;

/**
 * @author OSC
 * 
 */
@SuppressWarnings("serial")
public class AdmitInfoDialog extends JDialog {

	private Database dataBase;
	private static JTable table;
	private final JPanel contentPanel = new JPanel();

	private JComboBox<String> comboBoxUni;

	private Font mainFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);

	static public final String[] TABLE_HEADER = { "专业名称", "总人数", "录取人数" };

	public static void main(String[] args) {
		new AdmitInfoDialog().setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public AdmitInfoDialog() {
		setLookAndFeel();
		setTitle("专业表与录取信息");
		setSize(400, 450);
		setLocationRelativeTo(null);
		setResizable(false);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		try {
			initDataBase();
			initComponents();
		} catch (Exception ex) {
			DataBaseError errDialog = new DataBaseError(ex.getMessage());
			errDialog.setLocationRelativeTo(getContentPane());
			errDialog.setVisible(true);
			ex.printStackTrace();
			System.exit(0);
		}
	}

	private void initDataBase() throws ClassNotFoundException,
			FileNotFoundException, IOException, SQLException {
		dataBase = new Database();
	}

	private void initComponents() throws SQLException {
		Container contentPane = getContentPane();

		contentPane.setLayout(null);
		contentPanel.setBounds(0, 0, 394, 421);
		contentPanel.setBackground(SystemColor.menu);
		TitledBorder addBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "专业表与录取信息",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		contentPanel.setBorder(addBorder);
		addBorder.setTitleFont(boldFont);
		contentPane.add(contentPanel);
		contentPanel.setLayout(null);

		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(true);
		table.setFont(mainFont);
		table.getTableHeader().setFont(boldFont);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null,
				null));
		table.setBounds(10, 22, 664, 128);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(374, 300);
		scrollPane.setLocation(10, 60);
		scrollPane.setViewportView(table);
		contentPanel.add(scrollPane);

		comboBoxUni = new JComboBox<String>();
		comboBoxUni.setFont(mainFont);
		comboBoxUni.setModel(new DefaultComboBoxModel<String>(
				RandomApplication.UNI_LIB));
		comboBoxUni.setBounds(50, 25, 120, 30);
		comboBoxUni.addActionListener(showMajorInfo);
		contentPanel.add(comboBoxUni);

		JLabel lblUni = new JLabel("大学");
		lblUni.setFont(mainFont);
		lblUni.setHorizontalAlignment(SwingConstants.CENTER);
		lblUni.setBounds(10, 25, 40, 30);
		contentPanel.add(lblUni);

		JButton btnClear = new JButton("清空");
		btnClear.setFont(mainFont);
		btnClear.setBounds(280, 25, 90, 30);
		btnClear.addActionListener(toEmpty);
		contentPanel.add(btnClear);

		JButton btnOk = new JButton("返回");
		btnOk.setFont(mainFont);
		btnOk.setBounds(152, 375, 90, 30);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPanel.add(btnOk);

		initTable();
	}

	private ActionListener toEmpty = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();

			if (selectedRow < 0) {
				return;
			}

			String majName = (String) table.getValueAt(selectedRow, 0);

			try {
				dataBase.clearAdmitByMajName(majName);
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(getContentPane());
				errDialog.setVisible(true);
				System.exit(0);
				ex.printStackTrace();
			}
			tableModel.setValueAt(0, selectedRow, 2);
			updateTable(tableModel);
		}
	};

	private ActionListener showMajorInfo = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				initTable();
			} catch (SQLException ex) {
				DataBaseError errDialog = new DataBaseError(ex.getMessage());
				errDialog.setLocationRelativeTo(getContentPane());
				errDialog.setVisible(true);
				ex.printStackTrace();
				System.exit(0);
			}
		}
	};

	private void initTable() throws SQLException {
		String focusUni = (String) comboBoxUni.getSelectedItem();
		int indexUni = 2;

		if (focusUni.equals(PhysicalUniversity.AUU)) {
			indexUni = 0;
		} else if (focusUni.equals(PhysicalUniversity.TUS)) {
			indexUni = 1;
		}

		String[] majArr = dataBase.getAllMajByUniversityId(indexUni);
		int[] maxArr = dataBase.getMaxAdmitByUniversityId(indexUni);
		int[] numArr = dataBase.getCurAdmitByUniversityId(indexUni);
		int rowCount = 0;

		for (rowCount = tableModel.getRowCount(); rowCount > 0; rowCount--)
			tableModel.removeRow(rowCount - 1);
		table.setModel(tableModel);

		int i = 0;
		String[] initArr = { "", "0", "-1" };
		while (majArr[i] != null) {
			tableModel.addRow(initArr);
			tableModel.setValueAt(majArr[i], i, 0);
			i++;
		}
		i = 0;
		while (maxArr[i] != 0) {
			tableModel.setValueAt(maxArr[i], i, 1);
			i++;
		}
		i = 0;
		while (numArr[i] != -1) {
			tableModel.setValueAt(numArr[i], i, 2);
			i++;
		}

		updateTable(tableModel);
	}

	public static void updateTable(MyTableModel tableModel) {
		table.setModel(tableModel);
	}
}
