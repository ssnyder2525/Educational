package shared.communication.proxy;

public class SendChat
{

	/**
	 * The index of the player sending the message
	 */
	public int playerIndex;
	
	/**
	 * The message content
	 */
	public String content;
	
	public SendChat() 
	{}

	public SendChat(int playerIndex, String content) {
		this.playerIndex = playerIndex;
		this.content = content;
	}


	
}
