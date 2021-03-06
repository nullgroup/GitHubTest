package com.yzglw.dialog;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.yzglw.lx.PhysicalApplication;
import com.yzglw.lx.ReportCard;
import com.yzglw.util.RandomApplication;

/**
 * @author OSC
 * 
 */
@SuppressWarnings("serial")
public class AddDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private PhysicalApplication app = new PhysicalApplication();
	private JTextField txtName;
	private JTextField txtHigh;
	private JTextField txtEmail;
	private JComboBox<String> comboBoxUni;
	private JComboBox<String> comboBoxMaj;
	private JTextArea textArea;
	private JRadioButton rdBtnYes;
	private JRadioButton rdBtnNo;
	private ButtonGroup btnGroup;
	private JSpinner spinChn;
	private JSpinner spinMth;
	private JSpinner spinEng;
	private JSpinner spinSci;

	private Font mainFont = new Font("微软雅黑", Font.PLAIN, 12);
	private Font boldFont = new Font("微软雅黑", Font.BOLD, 12);

	private boolean add = false;

	public static void main(String[] args) {
		AddDialog dialog = new AddDialog();
		dialog.setVisible(true);
	}

	public boolean isAdd() {
		return add;
	}

	private void setLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AddDialog() {
		setLookAndFeel();
		setTitle("添加申请表");
		setBounds(100, 100, 415, 340);
		setLocationRelativeTo(null);
		setResizable(false);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 409, 272);
		contentPanel.setBackground(SystemColor.menu);
		TitledBorder addBorder = new TitledBorder(new EtchedBorder(
				EtchedBorder.LOWERED, null, null), "添加申请表",
				TitledBorder.LEADING, TitledBorder.TOP, null, null);
		contentPanel.setBorder(addBorder);
		addBorder.setTitleFont(boldFont);
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblName = new JLabel("姓名");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(mainFont);
		lblName.setBounds(6, 26, 50, 30);
		contentPanel.add(lblName);

		JLabel lblHigh = new JLabel("高中");
		lblHigh.setHorizontalAlignment(SwingConstants.CENTER);
		lblHigh.setFont(mainFont);
		lblHigh.setBounds(6, 56, 50, 30);
		contentPanel.add(lblHigh);

		JLabel lblEmail = new JLabel("电子邮箱");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(mainFont);
		lblEmail.setBounds(6, 86, 50, 30);
		contentPanel.add(lblEmail);

		JLabel lblResume = new JLabel("简历");
		lblResume.setFont(mainFont);
		lblResume.setHorizontalAlignment(SwingConstants.CENTER);
		lblResume.setBounds(6, 116, 50, 30);
		contentPanel.add(lblResume);

		txtName = new JTextField();
		txtName.setHorizontalAlignment(SwingConstants.RIGHT);
		txtName.setFont(mainFont);
		txtName.setDisabledTextColor(Color.BLACK);
		txtName.setColumns(10);
		txtName.setBounds(72, 26, 150, 30);
		contentPanel.add(txtName);

		txtHigh = new JTextField();
		txtHigh.setHorizontalAlignment(SwingConstants.RIGHT);
		txtHigh.setFont(mainFont);
		txtHigh.setDisabledTextColor(Color.BLACK);
		txtHigh.setColumns(10);
		txtHigh.setBounds(72, 56, 150, 30);
		contentPanel.add(txtHigh);

		txtEmail = new JTextField();
		txtEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		txtEmail.setFont(mainFont);
		txtEmail.setDisabledTextColor(Color.BLACK);
		txtEmail.setColumns(10);
		txtEmail.setBounds(72, 86, 150, 30);
		contentPanel.add(txtEmail);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setFont(mainFont);
		textArea.setDisabledTextColor(Color.BLACK);
		textArea.setBounds(72, 116, 150, 74);
		contentPanel.add(textArea);

		JLabel lblChn = new JLabel("语文");
		lblChn.setHorizontalAlignment(SwingConstants.CENTER);
		lblChn.setFont(mainFont);
		lblChn.setBounds(238, 26, 50, 30);
		contentPanel.add(lblChn);

		JLabel lblMth = new JLabel("数学");
		lblMth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMth.setFont(mainFont);
		lblMth.setBounds(238, 56, 50, 30);
		contentPanel.add(lblMth);

		JLabel lblEng = new JLabel("英语");
		lblEng.setHorizontalAlignment(SwingConstants.CENTER);
		lblEng.setFont(mainFont);
		lblEng.setBounds(238, 86, 50, 30);
		contentPanel.add(lblEng);

		JLabel lblSci = new JLabel("综合");
		lblSci.setHorizontalAlignment(SwingConstants.CENTER);
		lblSci.setFont(mainFont);
		lblSci.setBounds(238, 116, 50, 30);
		contentPanel.add(lblSci);

		JLabel lblTarUni = new JLabel("目标大学");
		lblTarUni.setHorizontalAlignment(SwingConstants.CENTER);
		lblTarUni.setFont(new Font("微软雅黑", Font.BOLD, 12));
		lblTarUni.setBounds(6, 215, 55, 30);
		contentPanel.add(lblTarUni);

		JLabel lblTarMaj = new JLabel("目标专业");
		lblTarMaj.setHorizontalAlignment(SwingConstants.CENTER);
		lblTarMaj.setFont(new Font("微软雅黑", Font.BOLD, 12));
		lblTarMaj.setBounds(220, 215, 55, 30);
		contentPanel.add(lblTarMaj);

		comboBoxUni = new JComboBox<String>();
		comboBoxUni.setModel(new DefaultComboBoxModel<String>(
				RandomApplication.UNI_LIB));
		comboBoxUni.setFont(mainFont);
		comboBoxUni.setBounds(72, 215, 120, 30);
		contentPanel.add(comboBoxUni);

		String[] majArray = new String[RandomApplication.MAJ_LIB[0].length
				+ RandomApplication.MAJ_LIB[1].length
				+ RandomApplication.MAJ_LIB[2].length];
		for (int i = 0, sum = 0; i < 3; i++) {
			for (int j = 0, len = RandomApplication.MAJ_LIB[i].length; j < len; j++, sum++) {
				majArray[sum] = RandomApplication.MAJ_LIB[i][j];
			}
		}
		comboBoxMaj = new JComboBox<String>();
		comboBoxMaj.setModel(new DefaultComboBoxModel<String>(majArray));
		comboBoxMaj.setBounds(280, 215, 100, 30);
		comboBoxMaj.setFont(mainFont);
		contentPanel.add(comboBoxMaj);

		JLabel lblCheck = new JLabel("盖章");
		lblCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lblCheck.setFont(mainFont);
		lblCheck.setBounds(238, 170, 50, 20);
		contentPanel.add(lblCheck);

		rdBtnYes = new JRadioButton("是");
		rdBtnYes.setSelected(true);
		rdBtnYes.setFont(mainFont);
		rdBtnYes.setBounds(301, 170, 40, 20);
		contentPanel.add(rdBtnYes);

		rdBtnNo = new JRadioButton("否");
		rdBtnNo.setFont(mainFont);
		rdBtnNo.setBounds(346, 170, 40, 20);

		btnGroup = new ButtonGroup();
		btnGroup.add(rdBtnYes);
		btnGroup.add(rdBtnNo);

		contentPanel.add(rdBtnNo);

		spinChn = new JSpinner();
		spinChn.setModel(new SpinnerNumberModel(90, 0, 150, 1));
		spinChn.setBounds(335, 26, 60, 30);
		contentPanel.add(spinChn);

		spinMth = new JSpinner();
		spinMth.setModel(new SpinnerNumberModel(90, 0, 150, 1));
		spinMth.setBounds(335, 56, 60, 30);
		contentPanel.add(spinMth);

		spinEng = new JSpinner();
		spinEng.setModel(new SpinnerNumberModel(90, 0, 150, 1));
		spinEng.setBounds(335, 86, 60, 30);
		contentPanel.add(spinEng);

		spinSci = new JSpinner();
		spinSci.setModel(new SpinnerNumberModel(180, 0, 300, 1));
		spinSci.setBounds(335, 116, 60, 30);
		contentPanel.add(spinSci);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 272, 409, 40);
			buttonPane.setBackground(SystemColor.menu);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton btnOk = new JButton("确定");
				btnOk.setBounds(193, 5, 90, 30);
				btnOk.setFont(mainFont);
				btnOk.setActionCommand("OK");
				btnOk.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						assign();
						dispose();
						add = true;
					}

				});
				buttonPane.add(btnOk);
				getRootPane().setDefaultButton(btnOk);
			}
			{
				JButton btnCancel = new JButton("取消");
				btnCancel.setBounds(295, 5, 90, 30);
				btnCancel.setFont(mainFont);
				btnCancel.setActionCommand("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				buttonPane.add(btnCancel);
			}
		}
	}

	private void assign() {
		String targetUni = (String) comboBoxUni.getSelectedItem();
		String targetMaj = (String) comboBoxMaj.getSelectedItem();

		int[] gradeArray = { (Integer) spinChn.getValue(),
				(Integer) spinMth.getValue(), (Integer) spinEng.getValue(),
				(Integer) spinSci.getValue() };
		String[] infoArray = { txtName.getText(), txtHigh.getText(),
				txtEmail.getText(), textArea.getText() };

		ReportCard card = new ReportCard(gradeArray, infoArray);
		if (rdBtnYes.isSelected()) {
			card.check();
		}

		app = new PhysicalApplication(card, targetUni, targetMaj);
	}

	public PhysicalApplication getApp() {
		return app;
	}

	public String getUni() {
		return (String) comboBoxUni.getSelectedItem();
	}

	public String getMaj() {
		return (String) comboBoxMaj.getSelectedItem();
	}
}
