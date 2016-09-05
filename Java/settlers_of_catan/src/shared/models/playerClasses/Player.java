package shared.models.playerClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.models.cardClasses.DevCards;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.cardClasses.ResourceCards;

public class Player {
	
	private final int MAX_ROADS = 15;
	private final int MAX_SETTLEMENTS = 5;
	private final int MAX_CITIES = 4;
	
	/**The id of the client that is controlling this player*/
	private int id;
	
	/**String with the player's name*/
	private String name;
	
	/**The player's color*/
	private CatanColor color;
	
	/**This players index in the games player list*/
	private int index;
	
	/**Set to true if it is this player's turn*/
	private boolean isTurn = false;
	
	/**The number of victory points the player has earned. The goal is 10.*/
	private int victoryPoints = 0;
	
	/**The number of cities this player may build*/
	private int cities = MAX_CITIES;
	
	/**The number of settlements this player may build*/
	private int settlements = MAX_SETTLEMENTS;
	
	/**The number of roads this player may build*/
	private int roads = MAX_ROADS;
	
	/**Boolean indicating whether the player is discarding or not*/
	private boolean discarding = false;
	
	/**A container for this player's resource cards.*/
	private ResourceCards resourceCards = new ResourceCards(0,0,0,0,0);
	
	/**A container for this player's development cards*/
	private DevCards devCards = new DevCards(0,0,0,0,0);
	
	/**A container for this player's used dev cards*/
	private ArrayList<DevCardType> newDevCards = new ArrayList<DevCardType>();
	
	/**The number of soldierCards this player has played*/
	private int army = 0;
	
	/**The number of Monument cards this player has played.*/
	private int monuments = 0;
	
	//we need a way to represent Harbor benefits

	public Player(int id, String name, CatanColor color, int index) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.index = index;
	}
	
	/**
	 * Constructor used by the deserializer
	 */
	public Player(HashMap<ResourceType, Integer> resources, HashMap<DevCardType, Integer> devCards, HashMap<DevCardType, Integer> newDevCards,
			int roads, int cities, int settlements, int soldiers, int victoryPoints, int monuments, int id, String name, CatanColor color,
			int index, boolean largestArmy, boolean longestRoad) {
		for(ResourceType type : resources.keySet()) {
			this.resourceCards.addCard(type, resources.get(type));
		}
		for(DevCardType type : devCards.keySet()) {
			this.devCards.addCards(type, devCards.get(type));
		}
		for(DevCardType type : newDevCards.keySet()) {
			for(int i = 0; i < newDevCards.get(type); i++) {
				this.newDevCards.add(type);
			}
		}
		this.roads = roads;
		this.settlements = settlements;
		this.cities = cities;
		this.army = soldiers;
		this.victoryPoints = victoryPoints;
		this.monuments = monuments;
		this.id = id;
		this.name = name;
		this.color = color;
		this.index = index;
	}

	/**Gets this players ID */
	public int getID() {
		return id;
	}
	/**Gets this player's name*/
	public String getName() {
		return name;
	}
	/**Gets this players Color*/
	public CatanColor getColor() {
		return color;
	}
	/**gets this players index*/
	public int getIndex() {
		return this.index;
	}
	/**Gets this players victory points*/
	public int getVictoryPoints() {
		return victoryPoints;
	}
	/**Gets this players army */
	public int getArmy() {
		return army;
	}
	/**Gets this players monuments */
	public int getMonuments() {
		return monuments;
	}
	/**Gets this players roads */
	public int getRoads() {
		return roads;
	}
	/**Gets this players settlements */
	public int getSettlements() {
		return settlements;
	}
	/**Gets this players cities */
	public int getCities() {
		return cities;
	}
	/**Gets this players devCardsBought this turn*/
	public DevCards getNewDevCards() {
		DevCards group = new DevCards(0,0,0,0,0);
		for (DevCardType card : newDevCards) {
			group.addCard(card);
		}
		return group;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void addVictoryPoint() {
		this.victoryPoints++;
	}
	
	public void subtractVictoryPoint() {
		this.victoryPoints--;
	}
	
	public void removeRoad() {
		this.roads--;
	}
	
	public void addMonument() {
		this.monuments++;
	}
	
	public void addToArmy() {
		this.army++;
	}
	
	
	
	/** Check if it is this player's turn
	 * @return Returns true if it's the player's turn,
	 * otherwise, returns false;
	 */
	public boolean isTurn() {
		return this.isTurn;
	}
	
	/**Set isTurn to true*/
	public void startTurn() {
		this.isTurn = true;
	}
	
	/**
	 * Set isTurn to false
	 */
	public void finishTurn() {
		this.isTurn = false;
		for (DevCardType card : newDevCards) {
			this.devCards.addCard(card);
		}
		this.newDevCards.clear();
	}
	
	/**
	 * Adds resources to this player's resourceCards object
	 * @param type the type of resource to add.
	 * @param num the number to add.
	 */
	public void addResourceToHand(ResourceType type, int num) {
		resourceCards.addCard(type, num);
	}
	
	/**
	 * Removes resources from this player's resourceCards object
	 * @param type the type of resource to remove
	 * @param num the number of resources to remove
	 * @throws InsufficientCardNumberException 
	 */
	public void removeResourceFromHand(ResourceType type, int num) throws InsufficientCardNumberException {
		resourceCards.removeCard(type, num);
	}
	
	public ResourceType removeRandomResource() throws InsufficientCardNumberException {
		if(this.resourceCards.getTotal() == 0) {
			return null;
		}
		Random rand = new Random(System.currentTimeMillis());
		Integer randNum = rand.nextInt(this.resourceCards.getTotal()) + 1;
		randNum -= this.resourceCards.getBrickCards();
		if (randNum <= 0) {
			this.resourceCards.removeCard(ResourceType.BRICK, 1);
			return ResourceType.BRICK;
		}
		randNum -= this.resourceCards.getWheatCards();
		if (randNum <= 0) {
			this.resourceCards.removeCard(ResourceType.WHEAT, 1);
			return ResourceType.WHEAT;
		}
		randNum -= this.resourceCards.getWoodCards();
		if (randNum <= 0) {
			this.resourceCards.removeCard(ResourceType.WOOD, 1);
			return ResourceType.WOOD;
		}
		randNum -= this.resourceCards.getOreCards();
		if (randNum <= 0) {
			this.resourceCards.removeCard(ResourceType.ORE, 1);
			return ResourceType.ORE;
		}
		this.resourceCards.removeCard(ResourceType.SHEEP, 1);
		return ResourceType.SHEEP;
	}
	
	/**
	 * Returns the number of a type of resource in the player's hand.
	 * @param type The type of resource to get
	 * @return the number of the type of resources in the player's hand
	 */
	public int getNumOfResource(ResourceType type) {
		int number = 0;
		switch(type){
		case BRICK:
			number = resourceCards.getBrickCards();
			break;
		case ORE:
			number = resourceCards.getOreCards();
			break;
		case SHEEP:
			number = resourceCards.getSheepCards();
			break;
		case WHEAT:
			number = resourceCards.getWheatCards();
			break;
		case WOOD:
			number = resourceCards.getWoodCards();
			break;
		}
		return number;
	}
	
	public int getTotalResources() {
		return resourceCards.getTotal();
	}
	
	/**
	 * Deducts resources from this player
	 * in order to buy a development card.
	 * @throws InsufficientCardNumberException 
	 */
	public void buyRoad() throws InsufficientCardNumberException {
		resourceCards.removeCard(ResourceType.WOOD, 1);
		resourceCards.removeCard(ResourceType.BRICK, 1);
		roads--;
	}
	
	/**
	 * Deducts resources from this player
	 * in order to buy a development card.
	 * @throws InsufficientCardNumberException 
	 */
	public void buySettlement() throws InsufficientCardNumberException {
		resourceCards.removeCard(ResourceType.WHEAT, 1);
		resourceCards.removeCard(ResourceType.WOOD, 1);
		resourceCards.removeCard(ResourceType.SHEEP, 1);
		resourceCards.removeCard(ResourceType.BRICK, 1);
		settlements--;
		victoryPoints++;
	}
	
	/**
	 * Deducts resources from this player
	 * in order to buy a development card.
	 * @throws InsufficientCardNumberException 
	 */
	public void buyCity() throws InsufficientCardNumberException {
		resourceCards.removeCard(ResourceType.ORE, 3);
		resourceCards.removeCard(ResourceType.WHEAT, 2);
		cities--;
		victoryPoints++;
	}
	
	/**
	 * Deducts resources from this player
	 * in order to buy a development card.
	 * @throws InsufficientCardNumberException 
	 */
	public void buyDevCard(DevCardType card) throws InsufficientCardNumberException {
		resourceCards.removeCard(ResourceType.WHEAT, 1);
		resourceCards.removeCard(ResourceType.ORE, 1);
		resourceCards.removeCard(ResourceType.SHEEP, 1);
		this.newDevCards.add(card);
	}
	
	/**
	 * Returns the number of a type of development Card in the player's hand. Mostly for testing purposes
	 * @param type The type of card to get
	 * @return the number of the type of card in the player's hand
	 */
	public int getNumOfDevCard(DevCardType type) {
		int number = 0;
		switch(type){
		case SOLDIER:
			number = devCards.getSoldierCards();
			break;
		case MONUMENT:
			number = devCards.getMonumentCards();
			break;
		case MONOPOLY:
			number = devCards.getMonopolyCards();
			break;
		case ROAD_BUILD:
			number = devCards.getRoadBuilderCards();
			break;
		case YEAR_OF_PLENTY:
			number = devCards.getYearOfPlentyCards();
			break;	
		}
		return number;
	}
	
	public void removeDevCard(DevCardType type) throws InsufficientCardNumberException {
		try {
			this.devCards.removeCard(type);
		} catch (InsufficientCardNumberException e) {
			if (type == DevCardType.MONUMENT) {
				for (int i = 0; i < this.newDevCards.size(); i++) {
					if (this.newDevCards.get(i) == type) {
						this.newDevCards.remove(i);
						return;
					}
				}
			}
			throw e;
		}
	}
	
	
	
	//**************************************************************************************************************************************
	//													Can Methods
	//**************************************************************************************************************************************
	
	
	
	public boolean canDiscardCards(ResourceType type, int num) {
		return this.resourceCards.canRemove(type, num);
	}
	
	/**
	 * Checks if this player can roll the dice
	 * @return returns true if they can, false if
	 * they can't
	 */
	public boolean canRollDice() {
		return this.isTurn;
	}
	
	/**
	 * Checks if this player has the resources to
	 * build a road.
	 * @return returns true if they can, false if
	 * they can't.
	 */
	public boolean canBuildRoad() {
		if(this.isTurn && resourceCards.getBrickCards() >= 1
				&& resourceCards.getWoodCards() >= 1 && roads > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if this player has the resources to
	 * build a settlement
	 * @return returns true if they can, false if
	 * they can't.
	 */
	public boolean canBuildSettlement() {
		if(this.isTurn && resourceCards.getBrickCards() >= 1
				&& resourceCards.getSheepCards() >= 1
				&& resourceCards.getWoodCards() >= 1
				&& resourceCards.getWheatCards() >= 1 && settlements > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if this player has the resources to
	 * build a city.
	 * @return returns true if they can, false if
	 * they can't.
	 */
	public boolean canBuildCity() {
		if(this.isTurn && resourceCards.getOreCards() >= 3
				&& resourceCards.getWheatCards() >= 2 && cities > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if this player has the resources to
	 * buy a development card
	 * @return returns true if they can, false if
	 * they can't.
	 */
	public boolean canBuyDevCard() {
		if(this.isTurn && resourceCards.getOreCards() >= 1
				&& resourceCards.getSheepCards() >= 1
				&& resourceCards.getWheatCards() >= 1) {
			return true;
		}
		return false;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}

	public void removeSettlement() {
		this.settlements--;
		this.victoryPoints++;
	}

	public boolean isDiscarding() {
		return discarding;
	}
	
	public void setDiscarding(boolean discarding) {
		this.discarding = discarding;
	}
}
