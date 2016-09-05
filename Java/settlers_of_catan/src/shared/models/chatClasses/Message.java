package shared.models.chatClasses;

public class Message {
	String source;
	String message;

	/**
	 * This class stores a single message between two players.
	 * @param from The author of the message
	 * @param message What is said
	 */
	public Message(String source, String message) {
		this.source = source;
		this.message = message;
	}

	/**
	 * @return The name of the player the message is from
	 */
	public String getSource() {
		return source;
	}
	
	public void setSource() {
		this.source = source;
	}
	/**
	 * @return The text contained in this message
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage() {
		this.message = message;
	}
}
