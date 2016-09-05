package CourseParticipants;

//------------------------------------------------------------------------------------------
//  CourseParticipants.java		Michael Gardiner
//								63-987-2126
//                              CS 142 Winter 2010 Midterm 2
//
//  Allows for comparison / examination of differences and similarities between two parents'
//  lists of participants in the Motorcycle Safety Course.
//------------------------------------------------------------------------------------------

import javax.swing.JFrame;

public class CourseParticipants {

	
	public static void main(String[] args) 
	{
		JFrame motorcycle = new JFrame ("Motorcycle Safety Course Participants");
	
		motorcycle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ParticipantPanel participants = new ParticipantPanel();
		
		motorcycle.getContentPane().add(participants);
		
		motorcycle.pack();
		
		motorcycle.setVisible(true);
		
	}
	
	
	
}
