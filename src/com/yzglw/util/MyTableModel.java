package com.yzglw.util;

import javax.swing.table.DefaultTableModel;

/**
 * @author Galdon
 *
 */
@SuppressWarnings("serial")
public class MyTableModel extends DefaultTableModel{
		
	public MyTableModel(Object[][] data, Object[] columnNames){
		super(data, columnNames);//����һ��Ҫ���Ǹ���Ĺ��췽����������ʵ��myTableModel
		}
		
	public boolean isCellEditable(int row, int column){
		return false;//����ķ��������� return true�ģ����ԾͿ��Ա༭��~~~
		}
}
