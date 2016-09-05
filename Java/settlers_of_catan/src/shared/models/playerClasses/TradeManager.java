package shared.models.playerClasses;
import java.util.ArrayList;
import java.util.HashMap;

import shared.communication.proxy.AcceptTrade;
import shared.communication.proxy.OfferTrade;
import shared.definitions.ResourceType;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.cardClasses.ResourceCards;
import shared.models.tradeClasses.*;

public class TradeManager {
	
	ArrayList<Trade> trades;
	GamePlayers players;
	OfferTrade trade;
	boolean tradeOffered = false;

	/**This class handles the specifics of trades made between players*/
	public TradeManager(GamePlayers players) {
		trades = new ArrayList<Trade>();
		this.players = players;
	}
	
	public void importTradeManager(String json) {
		
	}
	
	public void offerTrade(OfferTrade trade) {
		tradeOffered = true;
		this.trade = trade;
	}
	
	public OfferTrade getTrade() {
		return trade;
	}
	
	public boolean isTradeOffered() {
		return tradeOffered;
	}
	
	public void acceptTrade(AcceptTrade acceptTrade) {
		if (acceptTrade.response) {
			try {
				ExecuteTrade();
			} catch (InsufficientCardNumberException e) {
			}
		} else {
			rejectTrade();
		}
	}
	
	public void rejectTrade() {
		tradeOffered = false;
		trade = null;
	}
	
	
	/**
	 * Verifies that the players are able to trade the proposed trade
	 * @return A boolean representing whether the trade is a valid trade.
	 */
	public boolean canTrade(int traderIndex, int tradeeIndex, HashMap<ResourceType, Integer> out, HashMap<ResourceType, Integer> in) {
		//it must be the trader's turn
		if(!players.getPlayerByIndex(traderIndex).isTurn()) {
			return false;
		}
		
		//check if each part of the trade is consistent with what each player has in his/her hand.
		for(ResourceType type : out.keySet()) {
			int num = out.get(type);
			if(players.getPlayerByIndex(traderIndex).getNumOfResource(type) < num) {
				return false;
			}
		}
		
		for(ResourceType type : in.keySet()) {
			int num = in.get(type);
			if(players.getPlayerByIndex(tradeeIndex).getNumOfResource(type) < num) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Exchange two player's resources
	 * @throws InsufficientCardNumberException 
	 */
	public void ExecuteTrade() throws InsufficientCardNumberException {
		tradeOffered = false;
		//move the trader's resources to the tradee
		Player trader = players.getPlayerByIndex(trade.playerIndex);
		Player tradee = players.getPlayerByIndex(trade.receiverIndex);
		
		tradee.addResourceToHand(ResourceType.BRICK, trade.brick);
		
		tradee.addResourceToHand(ResourceType.ORE, trade.ore);
		
		tradee.addResourceToHand(ResourceType.SHEEP, trade.sheep);
		
		tradee.addResourceToHand(ResourceType.WHEAT, trade.wheat);
		
		tradee.addResourceToHand(ResourceType.WOOD, trade.wood);
		
		//move the tradee's resources to the trader
		
		trader.addResourceToHand(ResourceType.BRICK, 0 - trade.brick);
		
		trader.addResourceToHand(ResourceType.ORE, 0 - trade.ore);
		
		trader.addResourceToHand(ResourceType.SHEEP, 0 - trade.sheep);
		
		trader.addResourceToHand(ResourceType.WHEAT, 0 - trade.wheat);
		
		trader.addResourceToHand(ResourceType.WOOD, 0 - trade.wood);
		
		trade = null;
	}
	
	/**
	 * Exchange four of this player's resources for
	 * one of another.
	 * @throws InsufficientCardNumberException 
	 */
	public void tradeFour(int traderIndex, ResourceType tradeIn, ResourceType tradeOut) throws InsufficientCardNumberException {
		players.getPlayerByIndex(traderIndex).removeResourceFromHand(tradeIn, 4);
		players.getPlayerByIndex(traderIndex).addResourceToHand(tradeOut, 1);
	}
	
	/**
	 * Trade this player's resources using a harbor
	 * he/she has built on.
	 * @throws InsufficientCardNumberException 
	 */
	public void tradeTwoWithPort(int traderIndex, ResourceType portType, ResourceType returnType) throws InsufficientCardNumberException {
		players.getPlayerByIndex(traderIndex).removeResourceFromHand(portType, 2);
		players.getPlayerByIndex(traderIndex).addResourceToHand(returnType, 1);
	}
	
	/**
	 * Trade this player's resources using a harbor
	 * he/she has built on.
	 * @throws InsufficientCardNumberException 
	 */
	public void tradeThreeWithPort(int traderIndex, ResourceType portType, ResourceType returnType) throws InsufficientCardNumberException {
		players.getPlayerByIndex(traderIndex).removeResourceFromHand(portType, 3);
		players.getPlayerByIndex(traderIndex).addResourceToHand(returnType, 1);	
	}

}
