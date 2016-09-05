package shared.models.cardClasses;

@SuppressWarnings("serial")
public class InsufficientCardNumberException extends Exception {

	public InsufficientCardNumberException() {return;}

	public InsufficientCardNumberException(String message) {
		super(message);
	}

	public InsufficientCardNumberException(Throwable throwable) {
		super(throwable);
	}

	public InsufficientCardNumberException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
