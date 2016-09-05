package client.maritime;

import shared.definitions.*;
import shared.models.cardClasses.ResourceCards;
import shared.models.playerClasses.Player;
import shared.observers.MaritimeTradeObserver;

import java.util.ArrayList;
import java.util.List;

import client.base.*;
import client.clientFacade.ClientFacade;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	private MaritimeTradeObserver obs;
	ResourceType[] resourcesToGive;
	ResourceType[] resourcesToGet;
	ResourceType giveResource;
	ResourceType getResource;
	int ratio;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		super(tradeView);
		setTradeOverlay(tradeOverlay);
		obs = new MaritimeTradeObserver(this);
		ClientFacade.getInstance().game.addObserver(obs);
		this.getTradeView().enableMaritimeTrade(false);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		
		Player p = ClientFacade.getInstance().game.getPlayers().getPlayerByIndex(
						ClientFacade.getInstance().getUserData().getPlayerIndex());
		
		// These need to be set to the ratio available
		
		// 4 if no harbor, 3 if a norm, and 2 if a special
		int playerIndex = ClientFacade.getInstance().getUserData().getPlayerIndex();
		int brickRatio = ClientFacade.getInstance().game.getMaritimeTradeRatio(playerIndex, ResourceType.BRICK);
		int woodRatio = ClientFacade.getInstance().game.getMaritimeTradeRatio(playerIndex, ResourceType.WOOD);
		int wheatRatio = ClientFacade.getInstance().game.getMaritimeTradeRatio(playerIndex, ResourceType.WHEAT);
		int oreRatio = ClientFacade.getInstance().game.getMaritimeTradeRatio(playerIndex, ResourceType.ORE);
		int sheepRatio = ClientFacade.getInstance().game.getMaritimeTradeRatio(playerIndex, ResourceType.SHEEP);
		
		List<ResourceType> toGive = new ArrayList<ResourceType>();
		if (p.getNumOfResource(ResourceType.BRICK) >= brickRatio) {
			toGive.add(ResourceType.BRICK);
		}
		if (p.getNumOfResource(ResourceType.WOOD) >= woodRatio) {
			toGive.add(ResourceType.WOOD);
		}
		if (p.getNumOfResource(ResourceType.WHEAT) >= wheatRatio) {
			toGive.add(ResourceType.WHEAT);
		}
		if (p.getNumOfResource(ResourceType.SHEEP) >= sheepRatio) {
			toGive.add(ResourceType.SHEEP);
		}
		if (p.getNumOfResource(ResourceType.ORE) >= oreRatio) {
			toGive.add(ResourceType.ORE);
		}
		
		resourcesToGive = new ResourceType[toGive.size()];
		for (int i = 0; i < toGive.size(); i++) {
			resourcesToGive[i] = toGive.get(i);
		}
		
		List<ResourceType> resourcesAvailable = new ArrayList<ResourceType>();
		ResourceCards resources = ClientFacade.getInstance().game.getBank().getResources();
		for (ResourceType r : ResourceType.values()) {
			if (resources.canRemove(r, 1)) {
				resourcesAvailable.add(r);
			}
		}
		resourcesToGet = new ResourceType[resourcesAvailable.size()];
		for (int i = 0; i < resourcesAvailable.size(); i++) {
			resourcesToGet[i] = resourcesAvailable.get(i);
		}
		getTradeOverlay().showGiveOptions(resourcesToGive);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		try {
			ClientFacade.getInstance().tradeHarbor(giveResource, getResource, ratio);
		} catch (Exception e) {
			
		}
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource = resource;
		getTradeOverlay().selectGetOption(getResource, 1);
		getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		giveResource = resource;
		ratio = ClientFacade.getInstance().game.getMaritimeTradeRatio(
				ClientFacade.getInstance().getUserData().getPlayerIndex(), giveResource);
		getTradeOverlay().selectGiveOption(giveResource, ratio);
		getTradeOverlay().showGetOptions(resourcesToGet);
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGetOptions(resourcesToGet);
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().showGiveOptions(resourcesToGive);
		getTradeOverlay().setTradeEnabled(false);
	}

	public void update(GameState gameState) {
		if (gameState == GameState.MYTURN) {
			this.getTradeView().enableMaritimeTrade(true);
		} else {
			this.getTradeView().enableMaritimeTrade(false);
		}
	}
}