package CourseParticipants;

//------------------------------------------------------------------------------------------
//  ParticipantList.java		            Michael Gardiner
//
//  Represents a specific parent's list of Motorcycle Safety Course Participants.
//------------------------------------------------------------------------------------------

public class ParticipantList 
{
	private Participant[] list;
	
//--------------------------------------------------------------------------------------------
//  Constructor.  Creates an initially empty list.
//--------------------------------------------------------------------------------------------	
	
	public ParticipantList()
	{
		list = new Participant[0];
	}
	
//--------------------------------------------------------------------------------------------
//  Adds a participant with the specified names and helmet ownership to the list, if that
//  participant is not already in the list.
//  (Adapted from my solution to lab 6)
//--------------------------------------------------------------------------------------------	
	
	public boolean AddParticipant(String givenName, String lastName, boolean ownsHelmet)
	{
		boolean already = IsInList( new Participant (givenName, lastName, ownsHelmet) );
		
		if (already == false)
		{
			increaseListArr();
		
			list[list.length - 1] = new Participant(givenName, lastName, ownsHelmet);
		}
		
		else
		{
			
		}
		
		return already;
	}
	
//--------------------------------------------------------------------------------------------
//  Removes a participant with the specified names and helmet ownership from the list,
//  if the participant is in the list (Adapted from my solution to lab 6).
//--------------------------------------------------------------------------------------------	
	
	public boolean RemoveParticipant(String givenName, String lastName, boolean ownsHelmet)
	{
		boolean inList = IsInList( new Participant(givenName, lastName, ownsHelmet) );
		
		if (inList == true)
		{
			Participant participant = new Participant(givenName, lastName, ownsHelmet);
			
			Participant[] temp = new Participant[list.length - 1];
			
			for(int person = 0; person < (list.length - 1); person++)
			{
				if ( list[person].isEqualTo(participant) == true )
				{
					while (person < (list.length - 1) )
					{
						temp[person] = list[person + 1 ];
					
						person++;
					}	

				}
				
				else
				{
					temp[person] = list[person];
				}
				
			}
			
			list = temp;
			
		}
		
		else
		{
			
		}
		
		return inList;
	}
	
//--------------------------------------------------------------------------------------------
//  Determines if a participant with the specified full name and helmet ownership 
//  is already in the list or not (Adapted from my solution to lab 6).
//--------------------------------------------------------------------------------------------		
	
	public boolean IsInList(Participant participant)
	{
		boolean inList = false;
		
		int element = 0;
		
		while( (inList == false) && ( element < list.length ) )
		{	
			if ( ( list[element].getFullName().equals( participant.getFullName() ) == true ) 
					&& ( list[element].hasHelmet() == participant.hasHelmet() )   )
			{
				inList = true;
			
				return inList;
			}
			
			else
			{
				element++;
			}
		}
		
		return inList;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns the index of a participant with the specified full name and helmet ownership in
//  the list.
//--------------------------------------------------------------------------------------------		
	
	public int FindParticipant(Participant participant)
	{
		int index = 0;
	
	
	
	
		return index;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns the current number of participants in the list.
//--------------------------------------------------------------------------------------------
	
	public int getSize()
	{
		return list.length;
	}
	
//--------------------------------------------------------------------------------------------
//  Deletes all participants in the list and sets the size to zero.
//--------------------------------------------------------------------------------------------	
	
	public void ClearList()
	{
		Participant[] temp = new Participant[0];
	
		list = temp;
	}
	
//--------------------------------------------------------------------------------------------
//  Compares the list to a given list to determine if they are identical in which participants
//  are listed, regardless of the order of listing.  Begins with comparing their sizes,
//	then the participants in list 1 to list 2 participants, than list 2 participants to list 1
//  participants.
//--------------------------------------------------------------------------------------------	
	
	public boolean isEqualTo(ParticipantList otherList)
	{
		boolean equal = true;
		
		if(list.length != otherList.getSize() )
		{
			equal = false;
		}
		
		else
		{
			int element = 0;
			
			while ( (equal == true) && element < list.length )
			{
				
				if( otherList.IsInList(list[element]) == false)
				{
					equal = false;
				}	
				
				element++;
			}
				
				
			while ( (equal == true)  && element < otherList.list.length )
			{
			
				if(IsInList(otherList.list[element]) == false)
				{
					equal = false;
				}
				
				element++;
			}
				
		}
		
		return equal;
	}
	
//--------------------------------------------------------------------------------------------
//  Compares the list to a given list to determine which participants are in both lists,
//  and returns the participants in both lists as a new "intersection" list.
//--------------------------------------------------------------------------------------------		
	
	public ParticipantList IntersectionWith(ParticipantList otherList)
	{
		ParticipantList intersection = new ParticipantList();
		
		for (int element = 0; element < list.length; element++)
		{
			if (otherList.IsInList( list[element] ) == true)
			{
				intersection.AddParticipant(list[element].getFirstName(), 
						list[element].getLastName(), list[element].hasHelmet() );
			} 
		}
		
		return intersection;
	}
	
//--------------------------------------------------------------------------------------------
// Compares the list to a given list to return all participants, without repetition,
// as a "union" list.  Starts by adding all participants from the first list to the union
// list, and then adds all participants in the second list that are not in the first (or in
// other words were not already added to the union list).
//--------------------------------------------------------------------------------------------	
	
	public ParticipantList UnionWith(ParticipantList otherList)
	{
		ParticipantList union = new ParticipantList();
		
		
		for (int element = 0; element < list.length; element++ )
		{
			union.AddParticipant(list[element].getFirstName(), list[element].getLastName(),
					list[element].hasHelmet() );
		}
		
		for(int otherElement = 0; otherElement < otherList.list.length; otherElement++)
		{
			if(IsInList(otherList.list[otherElement]) == false)
			{
				union.AddParticipant(otherList.list[otherElement].getFirstName(), 
						otherList.list[otherElement].getLastName(),
						otherList.list[otherElement].hasHelmet() );
			}
		}
		
		return union;
	}
	
//--------------------------------------------------------------------------------------------
// Compares the list to a given list to return a "difference" list of the participants in the 
// list that are not in the other given list.
//--------------------------------------------------------------------------------------------		
	
	public ParticipantList ParticipantsNotIn(ParticipantList otherList)
	{
		ParticipantList difference = new ParticipantList();
		
		for (int element = 0; element < list.length; element++)
		{
			if( otherList.IsInList(list[element]) == false )
			{
				difference.AddParticipant(list[element].getFirstName(), 
						list[element].getLastName(), list[element].hasHelmet() );
			}
			
		}
		
		return difference;
	}
	

	
//--------------------------------------------------------------------------------------------
// Creates a string representation of each participant object in the list.
//--------------------------------------------------------------------------------------------		
	
	public String toString()
	{
		String listString="";
		
		for (int element = 0; element < list.length; element++)
		{
			listString += ( list[element].toString() + ", " );
		}
		
		return listString;
	}
	
	
	
//--------------------------------------------------------------------------------------------
// Increases the list's size for use in other methods (adapted from my solution to lab 6).
//--------------------------------------------------------------------------------------------		
	
	private void increaseListArr()
	{
		Participant[] temp = new Participant[list.length + 1];
		
		for(int participant = 0; participant < list.length; participant++)
		{
			temp[participant] = list[participant];
		}
		
		list = temp;
	}
	
	
	
}
