package shared.models.playerClasses;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.models.cardClasses.DevCards;

public class GamePlayers {
	ArrayList<Player> players = new ArrayList<Player>();
	
	int longestRoad = 0;
	int largestArmy = 0;
	
	public GamePlayers() {}

	/**
	 * This gets the number of players currently registered to this game
	 * @return the number of players currently registered to this game.
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}
	
	/**
	 * This accepts a client and adds it as a player object to an array.
	 * @throws Exception 
	 */
	public void addPlayer(int playerID, String name, CatanColor color) throws Exception {
		if(this.getNumberOfPlayers() == 4) {
			throw new Exception("There are already four players in this game");
		}
		Player newPlayer = new Player(playerID, name, color, this.getNumberOfPlayers() - 1);
		players.add(newPlayer);
	}
	
	/**
	 * Overloaded the addPlayer method to add a player that has been
	 * deserialized.
	 */
	public void addPlayer(Player player) throws Exception {
		players.add(player);
		player.setIndex(getNumberOfPlayers() - 1);
	}
	
	/**
	 * Gets a player by his/her playerID
	 */
	public Player getPlayerByID(int playerID) {
		for (Player p : players) {
			if(p.getID() == playerID) {
				return p;
			}
		}
		return null;
	}
	
	/**
	 * This returns a player in connection with the given playerIndex
	 * @param playerIndex the index connected with this player
	 * @return A player object
	 */
	public Player getPlayerByIndex(int playerIndex) {
		return players.get(playerIndex);
	}	
	
	/**
	 * End a player's turn and mark that it is the
	 * next player's turn.
	 */
	public int finishTurn(int playerIndex) {
		//finish the current players turn
		players.get(playerIndex).finishTurn();
		
		playerIndex++;
		if(playerIndex > 3) {
			playerIndex = 0;
		}
		
		//start the next player's turn.
		players.get(playerIndex).startTurn();
		return playerIndex;
	}
	public int reverseFinishTurn(int playerIndex) {
		//finish the current players turn
		players.get(playerIndex).finishTurn();
		
		playerIndex--;
		
		
		if(playerIndex < 0) {
			playerIndex = 3;
		}
		
		//start the next player's turn.
		players.get(playerIndex).startTurn();
		return playerIndex;
	}
	public int repeatFinishTurn(int playerIndex) {
		//finish the current players turn
		players.get(playerIndex).finishTurn();
		
		//start the next player's turn.
		players.get(playerIndex).startTurn();
		return playerIndex;
	}
	public int finishRound2Turn(int playerIndex) {
		//finish the current players turn
		players.get(playerIndex).finishTurn();
		
		//start the next player's turn.
		players.get(playerIndex).startTurn();
		return playerIndex;
	}
	
	public int checkForLargestArmy() {
		int playerIndex = 0;
		for (Player p : players) {
			if(p.getArmy() > largestArmy && p.getArmy() > 2) {
				largestArmy = p.getArmy();
				return playerIndex;
			}
			playerIndex++;
		}
		return -1;
	}
	
	public int checkForWinner() {
		int playerIndex = 0;
		for (Player p : players) {
			if(p.getVictoryPoints() > 9) {
				return playerIndex;
			}
			playerIndex++;
		}
		return -1;
	}
	
	public String serialize(int indexOfPlayerWhoseTurnItIs, boolean playedDevCard, boolean discarded) {
		int index = 0;
		String json = "players: [";
		for(Player player : players) {
			json += "{";
			
			json += "resources: {";
			json += "brick: " + player.getNumOfResource(ResourceType.BRICK) + ", ";
			json += "wood: " + player.getNumOfResource(ResourceType.WOOD) + ", ";
			json += "sheep: " + player.getNumOfResource(ResourceType.SHEEP) + ", ";
			json += "wheat: " + player.getNumOfResource(ResourceType.WHEAT) + ", ";
			json += "ore: " + player.getNumOfResource(ResourceType.ORE);
			json += "},";
			
			json += "oldDevCards: {";
			json += "yearOfPlenty: " + player.getNumOfDevCard(DevCardType.YEAR_OF_PLENTY) + ", ";
			json += "monopoly: " + player.getNumOfDevCard(DevCardType.MONOPOLY) + ", ";
			json += "soldier: " + player.getNumOfDevCard(DevCardType.SOLDIER) + ", ";
			json += "roadBuilding: " + player.getNumOfDevCard(DevCardType.ROAD_BUILD) + ", ";
			json += "monument: " + player.getNumOfDevCard(DevCardType.MONUMENT) + ", ";
			json += "},";
			
			DevCards newDevCards = player.getNewDevCards();
			json += "newDevCards: {";
			json += "yearOfPlenty: " + newDevCards.getYearOfPlentyCards() + ", ";
			json += "monopoly: " + newDevCards.getMonopolyCards() + ", ";
			json += "soldier: " + newDevCards.getSoldierCards() + ", ";
			json += "roadBuilding: " + newDevCards.getRoadBuilderCards() + ", ";
			json += "monument: " + newDevCards.getMonumentCards() + ", ";
			json += "},";
			
			json += "roads: " + player.getRoads() + ", ";
			json += "cities: " + player.getCities() + ", ";
			json += "settlements: " + player.getSettlements() + ", ";
			json += "soldiers: " + player.getArmy() + ", ";
			json += "victoryPoints: " + player.getVictoryPoints() + ", ";
			json += "monuments: " + player.getMonuments() + ", ";
			json += "playedDevCard: " + "false" + ", ";
			json += "discarded: " + "false" + ", ";
			json += "playerID: " + player.getID() + ", ";
			json += "playerIndex: " + index + ", ";
			json += "name: " + player.getName() + ", ";
			json += "color: " + player.getColor().toString() + ", ";
			
			json += "},";
			index++;
		}
		
		json += "]";
		
		return json;
	}

	public List<Player> getPlayers() {
		return players;
	}
}
