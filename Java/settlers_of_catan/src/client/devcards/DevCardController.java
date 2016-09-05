package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.GameState;
import shared.definitions.ResourceType;
import shared.models.Game;
import shared.models.cardClasses.InsufficientCardNumberException;
import shared.models.playerClasses.Player;
import shared.observers.DevCardObserver;

import javax.swing.JOptionPane;

import client.base.*;
import client.clientFacade.ClientFacade;
import client.map.IMapView;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private GameState state;
	private DevCardObserver obs;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.obs = new DevCardObserver(this);	
		ClientFacade.getInstance().game.addObserver(obs);
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		try {
			ClientFacade.getInstance().buyDevCard();
			JOptionPane.showMessageDialog((BuyDevCardView)this.getBuyCardView(), "You bought a dev card");
			this.getBuyCardView().closeModal();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog((BuyDevCardView)this.getBuyCardView(), "Error buying a dev card");
		}
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		if(state == GameState.MYTURN) {
			Game game = ClientFacade.getInstance().game;
			boolean canPlayCard = !game.getTurnManager().hasPlayedDevCard();
			Player p = ClientFacade.getInstance().game.getPlayers().getPlayerByID(ClientFacade.getInstance().getUserData().getId());

			this.getPlayCardView().setCardAmount(DevCardType.MONOPOLY, p.getNumOfDevCard(DevCardType.MONOPOLY) + p.getNewDevCards().getMonopolyCards());
			if (p.getNumOfDevCard(DevCardType.MONOPOLY) > 0 && canPlayCard) {
				this.getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			}
			else {
				this.getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, false);
			}
			this.getPlayCardView().setCardAmount(DevCardType.MONUMENT, p.getNumOfDevCard(DevCardType.MONUMENT) + p.getNewDevCards().getMonumentCards());
			if (p.getNumOfDevCard(DevCardType.MONUMENT) + p.getNewDevCards().getMonumentCards() > 0 && canPlayCard) {
				this.getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
			}
			else {
				this.getPlayCardView().setCardEnabled(DevCardType.MONUMENT, false);
			}
			this.getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, p.getNumOfDevCard(DevCardType.ROAD_BUILD) + p.getNewDevCards().getRoadBuilderCards());
			if (p.getNumOfDevCard(DevCardType.ROAD_BUILD) > 0 && canPlayCard) {
				this.getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
			}
			else {
				this.getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, false);
			}
			this.getPlayCardView().setCardAmount(DevCardType.SOLDIER, p.getNumOfDevCard(DevCardType.SOLDIER) + p.getNewDevCards().getSoldierCards());
			if (p.getNumOfDevCard(DevCardType.SOLDIER) > 0 && canPlayCard) {
				this.getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
			}
			else {
				this.getPlayCardView().setCardEnabled(DevCardType.SOLDIER, false);
			}
			this.getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, p.getNumOfDevCard(DevCardType.YEAR_OF_PLENTY) + p.getNewDevCards().getYearOfPlentyCards());
			if (p.getNumOfDevCard(DevCardType.YEAR_OF_PLENTY) > 0 && canPlayCard) {
				this.getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
			}
			else {
				this.getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, false);
			}
			getPlayCardView().showModal();
		}
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		try {
			ClientFacade.getInstance().playMonopoly(resource);
		} catch (Exception e) {
			JOptionPane.showMessageDialog((PlayDevCardView)this.getPlayCardView(), "You cannot play this card now.");
		}
	}

	@Override
	public void playMonumentCard() {
		try {
			ClientFacade.getInstance().playMonument();
		} catch (Exception e) {
			JOptionPane.showMessageDialog((PlayDevCardView)this.getPlayCardView(), "You cannot play this card now.");
		}
	}

	@Override
	public void playRoadBuildCard() {
		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		try {
			ClientFacade.getInstance().playYearOfPlenty(resource1, resource2);
		} catch (Exception e) {
			JOptionPane.showMessageDialog((PlayDevCardView)this.getPlayCardView(), "You cannot play this card now.");
		}
	}
	
	public void update(GameState state) {
		switch(state) {
		case MYTURN:
			this.state = GameState.MYTURN;
			break;
		default:
			this.state = GameState.NOTMYTURN;
			break;
		
		}
	}

}

