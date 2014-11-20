package com.yzglw.util;

import javax.swing.table.DefaultTableModel;

/**
 * @author Galdon
 *
 */
@SuppressWarnings("serial")
public class MyTableModel extends DefaultTableModel{
		
	public MyTableModel(Object[][] data, Object[] columnNames){
		super(data, columnNames);//这里一定要覆盖父类的构造方法，否则不能实例myTableModel
		}
		
	public boolean isCellEditable(int row, int column){
		return false;//父类的方法里面是 return true的，所以就可以编辑了~~~
		}
}
