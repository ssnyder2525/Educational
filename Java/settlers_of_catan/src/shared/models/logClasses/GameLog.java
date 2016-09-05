package shared.models.logClasses;

import java.util.ArrayList;
import java.util.List;

import shared.models.chatClasses.Message;

public class GameLog {
	/**An array that holds the messages in the game log*/
	ArrayList<Message> messages = new ArrayList<Message>();
	
	/**
	 * This object keeps track of the logs entered during the course of the game
	 */
	public GameLog() {}
	
	/**
	 * Creates the GameLog from existing Array of Messages
	 * @param messages The messages already in the Log
	 */
	public GameLog(ArrayList<Message> messages) {
		this.messages = messages;
	}

	/**
	 * Replaces messages with the passed in copy
	 */
	public void importLog(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	/**
	 * Adds a log message to the messages list
	 * 
	 * @param message The message to be added to the log
	 */
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	/**Gets all log messages*/
	public List<Message> getMessages() {
		return messages;
	}
}