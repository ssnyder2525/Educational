package client.recordIndexerGUI.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
//import javax.swing.table.DefaultTableModel;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;

@SuppressWarnings("serial")
public class TableEntryForm extends JTable implements BatchListener
{
	Session session;
	String[] columns;
	String[][] recordedData;
	String[][] tableData;
	
	int numOfRows;
	int numOfColumns;
	
	RightClickListener rightClickListener;
	
	public TableEntryForm(Session session, String[][] data, String[] columns)
	{
		super(data, columns);
		this.setPreferredScrollableViewportSize(new Dimension(100,80));
		this.setFillsViewportHeight(true);
		this.setCellSelectionEnabled(true);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.session = session;
		this.columns = columns;
		this.recordedData = session.getInputData();
		this.tableData = data;
		this.numOfRows = session.getNumOfRows();
		this.numOfColumns = session.getNumOfColumns();
		this.rightClickListener = new RightClickListener();
		
		this.setModel(new TableModel(session, columns, data, columns.length, Integer.parseInt(session.getProject().getRecordsPerBatch())));
		changesListener l = new changesListener();
		this.getSelectionModel().addListSelectionListener(l);
		this.getColumnModel().getSelectionModel() .addListSelectionListener(l);
		
		session.addListener(this);
		this.addMouseListener(rightClickListener);
		this.setColumnSelectionInterval(1, 1);
		this.setRowSelectionInterval(0, 0);
	}	
	
	public class changesListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			tableValueChanged();
		}
	}
	
	public void tableValueChanged()
	{
		if(this.hasFocus())
		{
			int row = this.getSelectedRow();
			int col = this.getSelectedColumn()-1;
			if(row != -1)
			{
				if(session.getPrevRow() != -1 && session.getPrevColumn() != -1)
				{
					String value = tableData[session.getPrevRow()][session.getPrevColumn()+1];
					session.setCellData(value);
					if(col == -1)
					{
						session.cellSelected(row, 0);
					}
					else
					{
						session.cellSelected(row, col);
					}
				}
				else
				{
					session.cellSelected(row, col);
				}
			}
		}
	}
	
	@Override
	public void cellSelected(int row, int col, int prevRow, int prevColumn)
	{
		this.setRowSelectionInterval(row, row);
		this.setColumnSelectionInterval(col+1, col+1);
		updateCellValues();
		repaint();
	}
	
	public void updateCellValues()
	{
		for(int i = 0; i < numOfRows; i++)
		{
			for(int j = 0; j < numOfColumns; j++)
			{
				tableData[i][j+1] = recordedData[i][j];
			}
		}
		this.repaint();
	}
	
//This sets the red highlights for the quality checker.
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
         Component c = super.prepareRenderer(renderer, row, column);
         if (!c.getBackground().equals(getSelectionBackground()))
         {
             String type = (String)getModel().getValueAt(row, column);
             //if this value is not in the known data file
             if(session.isMispelled(type, column-1))
             {
            	 session.markCellAsMispelled(row, column);
            	 c.setBackground(Color.RED);
            	 c.addMouseListener(rightClickListener);
             }
 
             else
             {
            	 session.markCellAsNotMispelled(row, column);
            	 c.setBackground(Color.WHITE);
            	 c.removeMouseListener(rightClickListener);
             }
         }
         return c;
	}

	public class RightClickListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				JTable target = (JTable) e.getSource();
				int row = target.rowAtPoint(e.getPoint());
				int col = target.columnAtPoint(e.getPoint());
				String value = (String) target.getValueAt(row, col);
				if(session.isMispelled(value, col-1))
				{
					RightClickMenu menu = new RightClickMenu(session, value, row, col-1);
			 		menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{}

		@Override
		public void mouseExited(MouseEvent arg0)
		{}

		@Override
		public void mousePressed(MouseEvent arg0)
		{}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{}	
	}
}
