package CourseParticipants;

//------------------------------------------------------------------------------------------
//  ParticipantPanel.java		            Michael Gardiner
//
//  Sets up a GUI for the CourseParticipants.java application.
//------------------------------------------------------------------------------------------

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;


public class ParticipantPanel extends JPanel
{

//*****************************************************************************************	
//  Motorcycle Safety Course Participant Lists for two parents, and total number of 
//  participants.
//*****************************************************************************************	
		
	private ParticipantList tomList, benList;
	
	private int total;
	
//*****************************************************************************************	
//  Subpanels	
//*****************************************************************************************	
	
	private JPanel addParticipant, listData, listComparison;
	
//*****************************************************************************************	
//  addParticipant Panel Components	
//*****************************************************************************************	
		
	private JLabel titleAdd, firstName, lastName, hasHelmet, participantAdded;
	
	private JTextField givenName, familyName, ownsHelmet;
	
	private JButton addTom, addBen, removeTom, removeBen;

//*****************************************************************************************	
// listData Panel Components	
//*****************************************************************************************	
		
	private JLabel titleList, participantNum, tomNum, benNum;
	
	private JButton clearTom, clearBen;
	
//*****************************************************************************************	
// listComparison Panel Components
//*****************************************************************************************	
	
	private JLabel titleComparison;
	
	private JLabel equalLists, inCommon, allParticipants, tomParticipants, benParticipants;
	
	private JLabel common, all, tom, ben;
	
	private JButton compare;
	
	
	
//*****************************************************************************************	
//  Constructor for the GUI.  Also initializes the two participant lists.	
//*****************************************************************************************	
	
	public ParticipantPanel()
	{
		tomList = new ParticipantList();
		
		benList = new ParticipantList();
		
		
		addParticipant = new JPanel();
		
		listData = new JPanel();
		
		listComparison = new JPanel();
		
		
		AddParticipantPanel();
		
		add(addParticipant);
		
		
		ListDataPanel();
		
		add(listData);
		
		
		ListComparisonPanel();
		
		add(listComparison);
		
		
	}
	
//***********************************************************************************************
//  Sets up the subpanel addParticipant for adding new participants to the lists.
//***********************************************************************************************	

	public void AddParticipantPanel()
	{
		addParticipant.setLayout(new FlowLayout() );
		
		addParticipant.setPreferredSize (new Dimension (240, 270));
	
		addParticipant.setBackground (Color.red);
		
		
		titleAdd = new JLabel("Add/Remove A New Course Participant"); 
		
		firstName = new JLabel("First Name:"); 
		
		lastName = new JLabel("Last Name:"); 
		
		hasHelmet = new JLabel("Owns Helmet? y = yes, n = no"); 
		
		participantAdded = new JLabel("");
		
		
		
		givenName = new JTextField(10);
		
		familyName = new JTextField(10);
		
		ownsHelmet = new JTextField(1);
		
		
		addTom = new JButton("Add to Tom's Dad's List");
		
		addTom.addActionListener(new TomAddListener() );
		
		
		addBen = new JButton("Add to Ben's Dad's List");
		
		addBen.addActionListener(new BenAddListener() );
		
		
		removeTom = new JButton("Remove from Tom's Dad's List");
		
		removeTom.addActionListener(new TomRemoveListener() );
		
		
		removeBen = new JButton("Remove from Ben's Dad's List");
		
		removeBen.addActionListener(new BenRemoveListener() );
		
		
		addParticipant.add(titleAdd);
		
		addParticipant.add(firstName);
		
		addParticipant.add(givenName);
		
		addParticipant.add(lastName);
		
		addParticipant.add(familyName);
		
		addParticipant.add(hasHelmet);					
		
		addParticipant.add(ownsHelmet);		
		
		addParticipant.add(addTom);										
		
		addParticipant.add(addBen);
		
		addParticipant.add(removeTom);
		
		addParticipant.add(removeBen);
		
		addParticipant.add(participantAdded);
	
	}
	
//***********************************************************************************************
//  Sets up the subpanel listData for displaying the numbers of participants in both lists.
//***********************************************************************************************	

	public void ListDataPanel()
	{
		listData.setLayout(new FlowLayout() );
		
		listData.setPreferredSize (new Dimension (240, 270));
	
		listData.setBackground (Color.cyan);
		
		
		titleList= new JLabel("Participants"); 
		
		
		
		total = (tomList.getSize() ) + (benList.getSize() );
		
		participantNum = new JLabel("Total Number of Participants: " + total); 
		
		
		
		tomNum = new JLabel("Participants in Tom's Dad's List: " + tomList.getSize() ); 
		
		benNum = new JLabel("Participants in Ben's Dad's List: " + benList.getSize() ); 
		
		
		clearTom = new JButton("Clear Tom's Dad's List");
		
		clearTom.addActionListener(new TomClearListener() );
		
		
		clearBen = new JButton("Clear Ben's Dad's List");
		
		clearBen.addActionListener(new BenClearListener() );
		
		
		listData.add(titleList);
		
		listData.add(participantNum);
		
		listData.add(tomNum);
		
		listData.add(benNum);
		
		listData.add(clearTom);
		
		listData.add(clearBen);
		
	}
	
//***********************************************************************************************
//  Sets up the subpanel listComparison for comparing the participants in the lists.
//***********************************************************************************************	
	
	public void ListComparisonPanel()
	{
		listComparison.setLayout(new GridLayout(11,1) );
		
		listComparison.setPreferredSize (new Dimension (700, 270));
	
		listComparison.setBackground (Color.GREEN);
		
		
		titleComparison = new JLabel("List Comparison");
		
		equalLists = new JLabel("There are no participants.");
		
		inCommon = new JLabel("Participants Common to Both Lists:");
		
		common = new JLabel("");
		
		allParticipants = new JLabel("Participants in Either or Both List(s):");
		
		all = new JLabel("");
		
		tomParticipants = new JLabel("Participants in Tom's Dad's List ONLY:");
		
		tom = new JLabel("");
		
		benParticipants = new JLabel("Participants in Ben's Dad's List ONLY:");
		
		ben = new JLabel("");
		
		compare = new JButton("Compare the Lists");
		
		compare.addActionListener(new CompareListener() );
		
		
		listComparison.add(titleComparison);
		
		listComparison.add(compare);
		
		listComparison.add(equalLists);
		
		listComparison.add(inCommon);
		
		listComparison.add(common);
		
		listComparison.add(allParticipants);
		
		listComparison.add(all);
		
		listComparison.add(tomParticipants);
		
		listComparison.add(tom);
		
		listComparison.add(benParticipants);
		
		listComparison.add(ben);
		
	}
	
	
//***********************************************************************************************
//  Represents an action listener for the "addTom" Button on the "AddParticipant" subpanel.
//  Governs addition of participants to Tom's Dad's List (tomList)
//***********************************************************************************************	
	
	private class TomAddListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{	
			String nameFirst = givenName.getText();
			
			String nameLast = familyName.getText();
			
			String helmet = ownsHelmet.getText();
			
			char helmetChar = helmet.charAt(0);
			
			boolean hasHelmet;
			
			switch(helmetChar)
			{
				case 'y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'Y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'n':
				{
					hasHelmet = false;
				
					break;
				}
			
				default:
				{
					hasHelmet = false;
				}
				
			}
			
			boolean added = !(tomList.AddParticipant(nameFirst, nameLast, hasHelmet) );
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total); 
			
			tomNum.setText("Participants in Tom's Dad's List: " + tomList.getSize() );
			
			if(added == true)
			{ 
				participantAdded.setText("Participant added to Tom's Dad's List.");
		
				boolean same = tomList.isEqualTo(benList);
				
				if (same == true)
				{
					equalLists.setText("These lists have the same participants.");
				}
				
				else
				{
					equalLists.setText("These lists are not the same.");
				}
				
			}
			
			else
			{
				participantAdded.setText("Cannot add: participant already in list");
			}
			
		}
	
	}
	
//***********************************************************************************************
//  Represents an action listener for the "addBen" Button on the "AddParticipant" subpanel.
//  Governs addition of participants to Ben's Dad's List (benList)
//***********************************************************************************************	
	
	private class BenAddListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{	
			String nameFirst = givenName.getText();
			
			String nameLast = familyName.getText();
			
			String helmet = ownsHelmet.getText();
			
			char helmetChar = helmet.charAt(0);
			
			boolean hasHelmet;
			
			switch(helmetChar)
			{
				case 'y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'Y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'n':
				{
					hasHelmet = false;
				
					break;
				}
			
				default:
				{
					hasHelmet = false;
				}
				
			}
			
			boolean added = !(benList.AddParticipant(nameFirst, nameLast, hasHelmet) );
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total); 
			
			benNum.setText("Participants in Ben's Dad's List: " + benList.getSize() );
			
			if(added == true)
			{ 
				participantAdded.setText("Participant added to Ben's Dad's List.");
			
				boolean same = tomList.isEqualTo(benList);
			
				if (same == true)
				{
					equalLists.setText("These lists have the same participants.");
				}
				
				else
				{
					equalLists.setText("These lists are not the same.");
				}
				
			}
			
			else
			{
				participantAdded.setText("Cannot add: participant already in list");
			}
			
		}
	
	}
	
	
//***********************************************************************************************
//  Represents an action listener for the "removeTom" Button on the "AddParticipant" subpanel.
//  Governs removal of participants from Tom's Dad's List (tomList)
//***********************************************************************************************	
	
	private class TomRemoveListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{	
			String nameFirst = givenName.getText();
			
			String nameLast = familyName.getText();
			
			String helmet = ownsHelmet.getText();
			
			char helmetChar = helmet.charAt(0);
			
			boolean hasHelmet;
			
			switch(helmetChar)
			{
				case 'y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'Y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'n':
				{
					hasHelmet = false;
				
					break;
				}
			
				default:
				{
					hasHelmet = false;
				}
				
			}
			
			boolean removed = (tomList.RemoveParticipant(nameFirst, nameLast, hasHelmet) );
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total); 
			
			tomNum.setText("Participants in Tom's Dad's List: " + tomList.getSize() );
			
			if(removed == true)
			{ 
				participantAdded.setText("Participant removed from Tom's Dad's List.");
			
				boolean same = tomList.isEqualTo(benList);
			
				if (same == true)
				{
					equalLists.setText("These lists have the same participants.");
				}
				
				else
				{
					equalLists.setText("These lists are not the same.");
				}
				
			}
			
			else
			{
				participantAdded.setText("Cannot remove: participant not in list");
			}
			
		}
	
	}	

//***********************************************************************************************
//  Represents an action listener for the "removeBen" Button on the "AddParticipant" subpanel.
//  Governs removal of participants from Ben's Dad's List (benList)
//***********************************************************************************************	
	
	private class BenRemoveListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{	
			String nameFirst = givenName.getText();
			
			String nameLast = familyName.getText();
			
			String helmet = ownsHelmet.getText();
			
			char helmetChar = helmet.charAt(0);
			
			boolean hasHelmet;
			
			switch(helmetChar)
			{
				case 'y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'Y':
				{
					hasHelmet = true;
				
					break;
				}
				
				case 'n':
				{
					hasHelmet = false;
				
					break;
				}
			
				default:
				{
					hasHelmet = false;
				}
				
			}
			
			boolean removed = (benList.RemoveParticipant(nameFirst, nameLast, hasHelmet) );
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total); 
			
			benNum.setText("Participants in Ben's Dad's List: " + benList.getSize() );
			
			if(removed == true)
			{ 
				participantAdded.setText("Participant removed from Ben's Dad's List.");
			
				boolean same = tomList.isEqualTo(benList);
			
				if (same == true)
				{
					equalLists.setText("These lists have the same participants.");
				}
				
				else
				{
					equalLists.setText("These lists are not the same.");
				}
				
			}
			
			else
			{
				participantAdded.setText("Cannot remove: participant not in list");
			}
			
		}
	
	}		
	
//***********************************************************************************************
//  Represents an action listener for the "clearTom" Button on the "listData" subpanel.
//  Deletes all participants in Tom's Dad's List (tomList)
//***********************************************************************************************	
		
	private class TomClearListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			tomList.ClearList();
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total);
		
			tomNum.setText("Participants in Tom's Dad's List: " + tomList.getSize() );
		
			boolean same = tomList.isEqualTo(benList);
			
			if (same == true)
			{
				equalLists.setText("These lists have the same participants.");
			}
			
			else
			{
				equalLists.setText("These lists are not the same.");
			}
		
		
		}
	
	}
	
//***********************************************************************************************
//  Represents an action listener for the "clearBen" Button on the "listData" subpanel.
//  Deletes all participants in Ben's Dad's List (benList)
//***********************************************************************************************	
		
	private class BenClearListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			benList.ClearList();
			
			total = (tomList.getSize() ) + (benList.getSize() );
			
			participantNum.setText("Total Number of Participants: " + total);
		
			benNum.setText("Participants in Ben's Dad's List: " + benList.getSize() );
		
			boolean same = tomList.isEqualTo(benList);
			
			if (same == true)
			{
				equalLists.setText("These lists have the same participants.");
			}
			
			else
			{
				equalLists.setText("These lists are not the same.");
			}
		
		
		}
	
	}	
	
//***********************************************************************************************
//  Represents an action listener for the "compare" Button on the "listComparison" subpanel.
//  Compares the two lists using all the ParticipantList.java comparison methods.
//***********************************************************************************************	
	
	private class CompareListener implements ActionListener
	{
		public void actionPerformed (ActionEvent event)
		{
			
			// List Equality Test
			
			boolean same = tomList.isEqualTo(benList);
			
			if (same == true)
			{
				equalLists.setText("These lists have the same participants.");
			}
			
			else
			{
				equalLists.setText("These lists are not the same.");
			}
		
			//  Lists' Intersection
			
			ParticipantList intersection = tomList.IntersectionWith(benList);
		
			common.setText(intersection.toString() );
			
			
			// Lists' Union
			
			ParticipantList union = tomList.UnionWith(benList);
			
			all.setText(union.toString() );
			
			
			// Participants only in Tom's Dad's List
			
			ParticipantList tomDifference = tomList.ParticipantsNotIn(benList);
			
			tom.setText(tomDifference.toString() );
			
			
			// Participants only in Ben's Dad's List
			
			ParticipantList benDifference = benList.ParticipantsNotIn(tomList);
			
			ben.setText(benDifference.toString() );
			
		}
	
	}
	
	
	
}
