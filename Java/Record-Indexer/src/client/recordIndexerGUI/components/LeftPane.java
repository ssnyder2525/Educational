package client.recordIndexerGUI.components;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shared.models.Batch;
import shared.models.Project;
import client.recordIndexerGUI.Session;

@SuppressWarnings("serial")
public class LeftPane extends JTabbedPane
{
	Session session;
	TableEntryForm tableEntry;
	FieldEntryForm fieldEntry;
	
	public LeftPane(Session session)
	{
		this.session = session;
		this.add("Table Entry", tableEntry);
		this.add("Field Entry", fieldEntry);
	}
	
//getters
	public TableEntryForm getTableEntry()
	{
		return tableEntry;
	}
	
	public FieldEntryForm getFieldEntry()
	{
		return fieldEntry;
	}
	
//setters
	public void setTableEntry(TableEntryForm tableEntry)
	{
		this.tableEntry = tableEntry;
	}
	
	public void setFieldEntry(FieldEntryForm fieldEntry)
	{
		this.fieldEntry = fieldEntry;
	}
	
//Other functionality
	public void refresh()
	{
		this.revalidate();
		tableEntry.revalidate();
		fieldEntry.revalidate();
	}
	
	public void createTable(Batch batch, Project project)
	{
		String[] columns = getTableColumns(session.loadColumns());
		String[][] data = getTableData(session.getInputData());
		tableEntry = new TableEntryForm(session, data, columns);
		
		tableEntry.setPreferredScrollableViewportSize(new Dimension(100,70));
		tableEntry.setFillsViewportHeight(true);
		
		this.removeAll();
		JScrollPane j = new JScrollPane(tableEntry);
		this.add("Table Entry", j);
	}
	
	private String[][] getTableData(String[][] data)
	{
		int numColumns = session.getBatch().getFields().size() + 1;
		int numRows = Integer.parseInt(session.getProject().getRecordsPerBatch());
		String[][] result = new String[numRows][numColumns];
		for (int i = 0; i < numRows; i++)
		{
			for(int j = 0; j < numColumns; j++)
			{
				if(j == 0)
				{
					result[i][j] = String.valueOf(i+1);
				}
				else
				{
					if(data == null)
					{
						result[i][j] = "";
					}
					else
					{
						result[i][j] = data[i][j-1];
					}
				}
			}
		}
		return result;
	}
	private String[] getTableColumns(String[] columns)
	{
		int numColumns = columns.length + 1;
		String[] result = new String[numColumns];
		result[0] = "";
		for(int i = 1; i < numColumns; i++)
		{
			result[i] = columns[i-1];
		}
		return result;
	}
	
	public void setUpFieldInputPane(String[] columns, String[][] data)
	{
		this.fieldEntry = new FieldEntryForm(session, columns, data);
		this.add("Field Entry", fieldEntry);
		this.setVisible(true);
		this.addChangeListener(new fieldFocusListener());
		this.revalidate();
	}
	
	public String[][] buildRowsTableData(String[] rowsColumn)
	{
		rowsColumn[0] = "";
		int numRows = Integer.parseInt(session.getProject().getRecordsPerBatch());
		String[][] data = new String[1][numRows];
		for(int i = 0; i < numRows; i++)
		{
			data[0][i] = String.valueOf(i+1);
		}
		return data;
	}
	
	public void updateValues()
	{
		tableEntry.updateCellValues();
		fieldEntry.updateValues();
	}

	public void removeLeftContent()
	{
		this.removeAll();
		tableEntry = null;
		fieldEntry = null;
		this.add("Table Entry", tableEntry);
		this.add("Field Entry", fieldEntry);
	}
	
//listener
	public class fieldFocusListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			JTabbedPane pane = (JTabbedPane) e.getSource();
			int tab = pane.getSelectedIndex();
			if(tab == 1)
			{
				session.cellSelected(session.getCurrentRow(), session.getCurrentColumn());
			}
		}
	}
}
