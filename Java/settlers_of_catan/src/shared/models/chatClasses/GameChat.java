package shared.models.chatClasses;

import java.util.ArrayList;
import java.util.List;

public class GameChat {
	/**An array that holds the messages in the game chat*/
	ArrayList<Message> messages = new ArrayList<Message>();

	/**
	 * This object keeps track of the chat between players during the course of a game
	 */
	public GameChat() {}
	
	/**
	 *  Creates the GameChat from existing Array of messages
	 * @param messages The messages already in the chat
	 */
	public GameChat(ArrayList<Message> messages) {
		this.messages = messages;
	}

	/**
	 * Replaces messages with the passed in copy
	 */
	public void importChat(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	/**
	 * Adds a message to the message list
	 *
	 * @param message The message to be added to the chat
	 */
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	/**Gets all messages*/
	public List<Message> getMessages() {
		return messages;
	}
}