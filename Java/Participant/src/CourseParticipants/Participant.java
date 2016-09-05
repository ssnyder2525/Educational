package CourseParticipants;

//------------------------------------------------------------------------------------------
//  Participant.java		            Michael Gardiner
//
//  Represents a participant in the Motorcycle Safety Course.
//------------------------------------------------------------------------------------------


public class Participant 
{

	private String firstName, lastName, fullName;
	
	private boolean hasHelmet;
	
	
//--------------------------------------------------------------------------------------------
//  Constructor
//--------------------------------------------------------------------------------------------
	
	public Participant(String givenName, String familyName, boolean ownsHelmet)
	{
		firstName = givenName;
		
		lastName = familyName;
		
		fullName = firstName + " " + lastName;
		
		hasHelmet = ownsHelmet;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns the first name of the participant
//--------------------------------------------------------------------------------------------
	
	public String getFirstName()
	{
		return firstName;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns the last name of the participant
//--------------------------------------------------------------------------------------------
		
	public String getLastName()
	{
		return lastName;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns the full name of the participant
//--------------------------------------------------------------------------------------------
		
	public String getFullName()
	{
		return fullName;
	}
	
//--------------------------------------------------------------------------------------------
//  Returns whether the participant has a helmet or not.
//--------------------------------------------------------------------------------------------
	
	public boolean hasHelmet()
	{
		return hasHelmet;
	}
	
//--------------------------------------------------------------------------------------------
//  Determines if two participants are identical.
//--------------------------------------------------------------------------------------------	
	
	public boolean isEqualTo(Participant other)
	{
		boolean equal = false;
		
		String nameFirst1 = getFirstName();
		
		String nameFirst2 = other.getFirstName();
		
		String nameLast1 = getLastName();
		
		String nameLast2 = other.getLastName();
		
		boolean has1 = hasHelmet();
		
		boolean has2 = other.hasHelmet();
		
		
		if( ( nameFirst1.equals(nameFirst2) ) && ( nameLast1.equals(nameLast2) ) && (has1 == has2 ) )
		{
			equal = true;
		}
		
		return equal;
	}
	
	
	
//--------------------------------------------------------------------------------------------
//  Creates a string representation of the participant.
//--------------------------------------------------------------------------------------------
	
	public String toString()
	{	
		String participantString;
		
		if (hasHelmet == false)
		{
			participantString = ( fullName + " with no helmet" );
		}
		
		else
		{
			participantString = fullName;
		}
		
		return participantString;
	}
	
	
}
