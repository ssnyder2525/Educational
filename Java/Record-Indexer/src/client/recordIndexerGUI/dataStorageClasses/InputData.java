package client.recordIndexerGUI.dataStorageClasses;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import shared.models.*;
import client.recordIndexerGUI.qualityChecker.SpellCorrector;

public class InputData
{
	String[] columns;
	String[][] data;
	boolean[][] mispelledCells;
	int rows = -1;
	int cols = -1;
	
	int prevRowSelected = 0;
	int prevColumnSelected = 0;
	int rowSelected = 0;
	int columnSelected = 0;
	
	SpellCorrector[] qualityChecker;
	
	transient ArrayList<BatchListener> listeners;
	
	public InputData(int rows, int cols)
	{
		data = new String[rows][cols];
		mispelledCells = new boolean[rows][cols];
		this.rows = rows;
		this.cols = cols;
		this.listeners = new ArrayList<BatchListener>();
	}
	
//getters
	public String[] getColumns()
	{
		return columns;
	}
	
	public String[][] getInputData()
	{
		return data;
	}
	
	public int getRowSelected()
	{
		return rowSelected;
	}
	
	public int getColumnSelected()
	{
		return columnSelected;
	}
	
	public int getPrevRowSelected()
	{
		return prevRowSelected;
	}
	
	public int getPrevColumnSelected()
	{
		return prevColumnSelected;
	}
	
	public ArrayList<BatchListener> getListeners()
	{
		return listeners;
	}
	
//setters
	public void setColumns(String[] columns)
	{
		this.columns = columns;
	}
	
	public void setInputData(String[][] data)
	{
		this.data = data;
	}

	public void setCellData(String value)
	{
		data[prevRowSelected][prevColumnSelected] = value;
	}

	public void setRowSelected(int rowSelected)
	{
		this.rowSelected = rowSelected;
	}

	public void setColumnSelected(int columnSelected)
	{
		this.columnSelected = columnSelected;
	}
	
	public void setPrevRowSelected(int prevRowSelected)
	{
		this.prevRowSelected = prevRowSelected;
	}

	public void setPrevColumnSelected(int prevColumnSelected)
	{
		this.prevColumnSelected = prevColumnSelected;
	}

//Batch Listener functionality
	public void setListener()
	{
		this.listeners = new ArrayList<BatchListener>();
	}
	
	public void addListener(BatchListener b)
	{
		this.listeners.add(b);
	}
	
	public void cellSelected(int row, int col)
	{
		rowSelected = row;
		columnSelected = col;
		for(BatchListener b : listeners)
		{
			b.cellSelected(row, col, prevRowSelected, prevColumnSelected);
		}
		prevRowSelected = row;
		prevColumnSelected = col;
	}
	
	public void initiateQualityChecker(Batch batch)
	{		
		qualityChecker = new SpellCorrector[cols];
		ArrayList<Field> fields = batch.getFields();
		
		for(int i = 0; i < fields.size(); i++)
		{
			SpellCorrector newSC = new SpellCorrector();
			try
			{
				URL url = new URL(fields.get(i).getKnownDataFile());
				newSC.useDictionary(url);
				qualityChecker[i] = newSC;
			} 
			catch (IOException e)
			{
				qualityChecker[i] = null;
			}
		}
	}

	public boolean isMispelled(Batch batch, String value, int column)
	{
		if(column == -1)
		{
			return false;
		}
		if(qualityChecker[column] == null)
		{
			return false;
		}
		return qualityChecker[column].isMispelled(value);
	}

	public Set<String> getSuggestions(String value, int column)
	{
		return qualityChecker[column].suggestSimilarWords(value);
	}

	public void markCellAsMispelled(int row, int column)
	{
		if(column != 0)
		{
			mispelledCells[row][column-1] = true;
		}
	}
	
	public void markCellAsNotMispelled(int row, int column)
	{
		if(column != 0)
		{
			mispelledCells[row][column-1] = false;
		}
	}

	public void replaceData(int row, int column, String value)
	{
		data[row][column] = value;
	}
}
