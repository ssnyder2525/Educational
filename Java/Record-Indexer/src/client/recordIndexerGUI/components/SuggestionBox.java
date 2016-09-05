package client.recordIndexerGUI.components;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.recordIndexerGUI.Session;

public class SuggestionBox 
{
	Session session;
	int row;
	int column;
	String value;
	
	JOptionPane suggestionBox;
	
	@SuppressWarnings("rawtypes")
	JList list;
	JButton useSuggestion = new JButton("Use Suggestion");
	JButton cancel = new JButton("Cancel");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	SuggestionBox(Session s, String v, int r, int c)
	{	
		this.session = s;
		this.row = r;
		this.column = c;
		this.value = v;
		
		Set<String> wordChoices = session.getSuggestions(value, column);
		Object[] data = wordChoices.toArray();
		list = new JList(data); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent listSelectionEvent) 
	        {
        		useSuggestion.setEnabled(true);
	        }
		});
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		useSuggestion.setEnabled(false);
		
		class ImageBoxClosingHandler extends WindowAdapter 
		{
			public void windowClosing(WindowEvent evt) 
			{
				session.getMainFrame().setFocusable(true);
				JDialog dialog = (JDialog)evt.getSource();
				dialog.dispose();
			}
		}
		final JDialog dialog = new JDialog(session.getMainFrame(), Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setLayout(new FlowLayout());
		dialog.setMinimumSize(new Dimension(600,150));
		dialog.setMaximumSize(new Dimension(600,150));
		dialog.setLocation(400, 250);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setResizable(false);
		dialog.addWindowListener(new ImageBoxClosingHandler());
		
		useSuggestion.setMinimumSize(new Dimension(40,80));
		useSuggestion.setMaximumSize(new Dimension(40,80));
		cancel.setMinimumSize(new Dimension(40,80));
		cancel.setMaximumSize(new Dimension(40,80));
		
		useSuggestion.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				session.replaceData(row, column, (String)list.getSelectedValue());
				session.updateValues();
				dialog.dispose();
				
			}       
		});
		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				dialog.dispose();
			}       
		});
		
		dialog.add(listScroller);
		dialog.add(useSuggestion);
		dialog.add(cancel);
		dialog.setVisible(true);
	}
}
