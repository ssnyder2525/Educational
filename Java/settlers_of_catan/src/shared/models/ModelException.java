package shared.models;

@SuppressWarnings("serial")
public class ModelException extends Exception {

	public ModelException() {
		return;
	}

	public ModelException(String message) {
		super(message);
	}

	public ModelException(Throwable throwable) {
		super(throwable);
	}

	public ModelException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
