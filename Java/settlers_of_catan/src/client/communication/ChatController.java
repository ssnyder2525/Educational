package client.communication;

import java.util.ArrayList;
import java.util.List;

import client.base.*;
import client.clientFacade.ClientFacade;
import shared.definitions.CatanColor;
import shared.definitions.GameState;
import shared.models.chatClasses.Message;
import shared.models.playerClasses.Player;
import shared.observers.ChatObserver;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {
	
	private ChatObserver obs;
	private int logSize;

	public ChatController(IChatView view) {
		super(view);
		logSize = 0;
		obs = new ChatObserver(this);
		ClientFacade.getInstance().game.addObserver(obs);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		ClientFacade.getInstance().sendChat(message);
	}

	public void update(GameState state) {
		initFromModel();
	}
	
	private void initFromModel() {
		List<Message> messages = ClientFacade.getInstance().game.getGameChat().getMessages();
		
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		for (Message m : messages) {
			logEntries.add(new LogEntry(getColor(m.getSource()), m.getMessage()));
		}
		boolean empty = false;
		if (logEntries.isEmpty()) {
			logEntries.add(new LogEntry(CatanColor.WHITE, "No Messages"));
			empty = true;
		}
		if (logEntries.size() != logSize) {
			if (empty) {
				getView().setEntries(logEntries);
			} else {
				logSize = logEntries.size();
				getView().setEntries(logEntries);
			}
		}
	}

	private CatanColor getColor(String source) {
		List<Player> players = ClientFacade.getInstance().game.getPlayers().getPlayers();
		for (Player p : players) {
			if (source.equals(p.getName())) {
				return p.getColor();
			}
		}
		// Shouldn't ever get here... But had to have all paths covered
		// Should I throw an Exception instead?
		return null;
	}
}

