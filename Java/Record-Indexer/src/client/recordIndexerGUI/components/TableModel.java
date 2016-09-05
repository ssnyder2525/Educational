package client.recordIndexerGUI.components;

import javax.swing.table.*;

import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class TableModel extends AbstractTableModel
{
	Session session;
	String[] columns;
	String[][] tableData;
	int columnNum;
	int rowNum;
	
	public TableModel(Session session, String[] columns, String[][] tableData, int columnNum, int rowNum) 
	{
		this.session = session;
		this.tableData = tableData;
		this.columns = columns;
		this.columnNum = columnNum;
		this.rowNum = rowNum;
	}

	@Override
	public boolean isCellEditable(int row, int column) 
	{
		if(column != 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public int getColumnCount()
	{
		return columnNum;
	}

	@Override
	public int getRowCount()
	{
		return rowNum;
	}

	@Override
	public String getColumnName(int col) 
	{
		return columns[col];
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		return tableData[row][col];
	}

	@Override
	public void setValueAt(Object value, int row, int col) 
	{            
		tableData[row][col] = (String) value;
	}
}

