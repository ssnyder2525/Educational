package client.resources;

import java.util.HashMap;
import java.util.Map;

import client.base.Controller;
import client.base.IAction;
import client.clientFacade.ClientFacade;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.models.Game;
import shared.observers.ResourceBarObserver;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	GameState state;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		ResourceBarObserver obs = new ResourceBarObserver(this);
		ClientFacade.getInstance().game.addObserver(obs);
		
		this.state = GameState.LOGIN;
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {
		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		if(this.state == GameState.MYTURN) {
			executeElementAction(ResourceBarElement.ROAD);
		}
	}

	@Override
	public void buildSettlement() {
		if(this.state == GameState.MYTURN) {
			executeElementAction(ResourceBarElement.SETTLEMENT);
		}
	}

	@Override
	public void buildCity() {
		if(this.state == GameState.MYTURN) {
			executeElementAction(ResourceBarElement.CITY);
		}
	}

	@Override
	public void buyCard() {
		if(this.state == GameState.MYTURN) {
			executeElementAction(ResourceBarElement.BUY_CARD);
		}
	}

	@Override
	public void playCard() {
		if(this.state == GameState.MYTURN) {
			executeElementAction(ResourceBarElement.PLAY_CARD);
		}
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {

			IAction action = elementActions.get(element);
			action.execute();
		}
	}
	
	public void update(GameState state) {
		Game game = ClientFacade.getInstance().game;
		getView().setElementAmount(ResourceBarElement.BRICK, 
				ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.BRICK));
		getView().setElementAmount(ResourceBarElement.WHEAT, 
				ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WHEAT));
		getView().setElementAmount(ResourceBarElement.WOOD, 
				ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WOOD));
		getView().setElementAmount(ResourceBarElement.ORE, 
				ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.ORE));
		getView().setElementAmount(ResourceBarElement.SHEEP, 
				ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.SHEEP));
				
		getView().setElementAmount(ResourceBarElement.ROAD, 
				ClientFacade.getInstance().getNumOfPlayerRoads());
		getView().setElementAmount(ResourceBarElement.CITY, 
				ClientFacade.getInstance().getNumOfPlayerCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, 
				ClientFacade.getInstance().getNumOfPlayerSettlements());
		getView().setElementAmount(ResourceBarElement.SOLDIERS, 
				ClientFacade.getInstance().getNumOfPlayerSoldiers());
		
		
		switch(state) {
		case SETUP1:
			if (ClientFacade.getInstance().getNumOfPlayerSettlements() == 4) {
				getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			}
		case MYTURN:
			getView().setElementEnabled(ResourceBarElement.ROAD, this.canBuildRoad());
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, this.canBuildSettlement());
			getView().setElementEnabled(ResourceBarElement.CITY, this.canBuildCity());
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, this.canBuyDevCard());
			this.state = GameState.MYTURN;
			break;
		default:
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
			getView().setElementEnabled(ResourceBarElement.CITY, false);
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
			this.state = GameState.NOTMYTURN;
			break;
		
		}
	}

	private boolean canBuyDevCard() {
		
		return ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.SHEEP) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.ORE) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WHEAT) >= 1;
	}

	private boolean canBuildCity() {
		
		return ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WHEAT) >= 2
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.ORE) >= 3
				&& ClientFacade.getInstance().getNumOfPlayerCities() >= 1;
	}

	private boolean canBuildSettlement() {
		
		return ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WOOD) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.BRICK) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.SHEEP) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WHEAT) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerSettlements() >= 1;
	}

	private boolean canBuildRoad() {
		
		return ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.WOOD) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerResource(ResourceType.BRICK) >= 1
				&& ClientFacade.getInstance().getNumOfPlayerRoads() >= 1;
	}

}

