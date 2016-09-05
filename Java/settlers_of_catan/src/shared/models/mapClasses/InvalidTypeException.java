package shared.models.mapClasses;

@SuppressWarnings("serial")
public class InvalidTypeException extends Exception 
{
	public InvalidTypeException() {return;}

	public InvalidTypeException(String message) { super(message); }

	public InvalidTypeException(Throwable throwable) { super(throwable); }

	public InvalidTypeException(String message, Throwable throwable) { super(message, throwable); }
}
