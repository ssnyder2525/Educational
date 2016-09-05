package shared.models.mapClasses;

@SuppressWarnings("serial")
public class InvalidTokenException extends Exception 
{
	public InvalidTokenException() {return;}

	public InvalidTokenException(String message) { super(message); }

	public InvalidTokenException(Throwable throwable) { super(throwable); }

	public InvalidTokenException(String message, Throwable throwable) { super(message, throwable); }
}
