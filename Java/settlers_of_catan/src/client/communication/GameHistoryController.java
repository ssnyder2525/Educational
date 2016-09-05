package client.communication;

import java.util.*;

import client.base.*;
import client.clientFacade.ClientFacade;
import shared.definitions.*;
import shared.models.chatClasses.Message;
import shared.models.playerClasses.Player;
import shared.observers.GameHistoryObserver;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {
	
	private GameHistoryObserver obs;
	private int logSize;

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		obs = new GameHistoryObserver(this);
		logSize = 0;
		ClientFacade.getInstance().game.addObserver(obs);
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		List<Message> messages = ClientFacade.getInstance().game.getGameLog().getMessages();
		
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

	public void update(GameState gameState) {
		initFromModel();
	}
	
}

