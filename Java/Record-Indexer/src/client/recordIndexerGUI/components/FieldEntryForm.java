package client.recordIndexerGUI.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.recordIndexerGUI.Session;
import client.recordIndexerGUI.dataStorageClasses.BatchListener;

@SuppressWarnings("serial")
public class FieldEntryForm extends JPanel implements BatchListener
{
	Session session;
	String[][] data;
	String[] columns;
	ArrayList<JTextField> fields = new ArrayList<JTextField>();
	
	JPanel buttons = new JPanel();
	JPanel InputFields = new JPanel();
	
	RightClickListener rightClickListener;
	
	public FieldEntryForm(Session session, String[] columns, String[][] data)
	{
		this.session = session;
		this.columns = columns;
		this.data = data;
		rightClickListener = new RightClickListener();
		
		//Adds the row buttons.
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.Y_AXIS));
		addButtons();
		
		addFields();

		this.add(buttons, BorderLayout.EAST);
		this.add(InputFields, BorderLayout.CENTER);
		
		session.addListener(this);
	}
	
//other functionality
	public void addButtons()
	{
		int numOfRows = session.getNumOfRows();
		for(int i = 0; i < numOfRows; i++)
		{
			JButton newButton = new JButton(String.valueOf(i+1));
			newButton.setMinimumSize(new Dimension(100, 20));
			newButton.setMaximumSize(new Dimension(100, 20));
			newButton.addActionListener(new ButtonListener(session));
			buttons.add(newButton);
		}
	}
	
	//Adds the input fields. Skips the first column, since there is not input there.
	public void addFields()
	{
		for(int i = 0; i < columns.length; i++)
		{
			InputFields.setLayout(new BoxLayout(InputFields, BoxLayout.Y_AXIS));
			JPanel field = new JPanel();
			JLabel columnLabel = new JLabel(columns[i]);
			JTextField textInput = new JTextField(20);
			textInput.setName(String.valueOf(i));
			textInput.addFocusListener(new TextFieldListener());
			fields.add(textInput);
			
			field.add(columnLabel);
			field.add(textInput);
			
			InputFields.add(field);
		}
	}
	
	@Override
	public void cellSelected(int row, int column, int prevRow, int prevColumn)
	{
		updateValues();
		fields.get(column).requestFocus();
	}
	
	public void updateValues()
	{
		for(int i = 0; i < session.getNumOfColumns(); i++)
		{
			fields.get(i).setText(data[session.getCurrentRow()][i]);
		}
		checkSpelling();
	}
	
	public void checkSpelling()
	{
		for(int i = 0; i < fields.size(); i++)
		{
			if(session.isMispelled(fields.get(i).getText(), i))
			{
				fields.get(i).setBackground(Color.RED);
				fields.get(i).addMouseListener(rightClickListener);
			}
			else
			{
				if(fields.get(i).getBackground() == Color.RED)
				{
					fields.get(i).setBackground(Color.WHITE);
					fields.get(i).removeMouseListener(rightClickListener);
				}
			}
		}
	}
	
//listeners
	public class ButtonListener implements ActionListener
	{
		Session session;
		
		ButtonListener(Session session)
		{
			this.session = session;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int rowSelected = Integer.parseInt(e.getActionCommand());
			
			session.cellSelected(rowSelected-1, session.getCurrentColumn());
		}
	}
	
	public class TextFieldListener implements FocusListener
	{
		//gets selected field, changes the column selected and calles cellSelected on all other components.
		@Override
		public void focusGained(FocusEvent e)
		{
			int columnSelected = Integer.parseInt(e.getComponent().getName());
			session.cellSelected(session.getCurrentRow(), columnSelected);
		}

		@Override
		public void focusLost(FocusEvent e)
		{
			JTextField selectedField = (JTextField) e.getSource();
			String value = selectedField.getText();
			session.setCellData(value);
			checkSpelling();
		}
		
	}
	
	public class RightClickListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				JTextField textField = (JTextField) e.getSource();
				RightClickMenu menu = new RightClickMenu(session, textField.getText(), session.getCurrentRow(), session.getCurrentColumn());
				menu.show(e.getComponent(), e.getX(), e.getY());
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
