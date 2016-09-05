package client.discard;

import shared.definitions.*;
import shared.models.playerClasses.Player;
import shared.observers.DiscardObserver;
import client.base.*;
import client.clientFacade.ClientFacade;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	private DiscardObserver obs;
	
	private int maxBrick;
	private int maxOre;
	private int maxSheep;
	private int maxWheat;
	private int maxWood;
	private boolean firstTime;
	
	private int brickToDiscard;
	private int oreToDiscard;
	private int sheepToDiscard;
	private int wheatToDiscard;
	private int woodToDiscard;
	
	private int totalToDiscard;
	private int currTotal;
	
	private boolean discarding;
	private boolean waiting;
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		this.obs = new DiscardObserver(this);
		ClientFacade.getInstance().game.addObserver(obs);
		this.waitView = waitView;
		discarding = false;
		waiting = false;
		firstTime = true;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource) {
		case BRICK:
			if(++brickToDiscard < maxBrick)this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, brickToDiscard);
			break;
		case ORE:
			if(++oreToDiscard < maxOre) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, oreToDiscard);
			break;
		case SHEEP:
			if(++sheepToDiscard < maxSheep) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, sheepToDiscard);
			break;
		case WHEAT:
			if(++wheatToDiscard < maxWheat) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, wheatToDiscard);
			break;
		case WOOD:
			if(++woodToDiscard < maxWood) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
			this.getDiscardView().setResourceDiscardAmount(resource, woodToDiscard);
			break;
		}
		
		if (++currTotal == totalToDiscard) {
			if (brickToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
			if (sheepToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
			if (wheatToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
			if (woodToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
			if (oreToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
			this.getDiscardView().setStateMessage("Click to Discard");
			this.getDiscardView().setDiscardButtonEnabled(true);
		} else {
			this.getDiscardView().setStateMessage("Still " + (totalToDiscard-currTotal) + " cards to discard");
		}
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource) {
		case BRICK:
			if(--brickToDiscard > 0)this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, brickToDiscard);
			break;
		case ORE:
			if(--oreToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, oreToDiscard);
			break;
		case SHEEP:
			if(--sheepToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, sheepToDiscard);
			break;
		case WHEAT:
			if(--wheatToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, wheatToDiscard);
			break;
		case WOOD:
			if(--woodToDiscard > 0) this.getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
			else this.getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
			this.getDiscardView().setResourceDiscardAmount(resource, woodToDiscard);
			break;
		}
		
		if (--currTotal < totalToDiscard) {
			if (woodToDiscard < maxWood) {
				if (woodToDiscard > 0) {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
				} else {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
				}
			}
			if (brickToDiscard < maxBrick) {
				if (brickToDiscard > 0) {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
				} else {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
				}
			}
			if (wheatToDiscard < maxWheat) {
				if (wheatToDiscard > 0) {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
				} else {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
				}
			}
			if (sheepToDiscard < maxSheep) {
				if (sheepToDiscard > 0) {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
				} else {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
				}
			}
			if (oreToDiscard < maxOre) {
				if (oreToDiscard > 0) {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
				} else {
					this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
				}
			}
		}
		
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.getDiscardView().setStateMessage("Still " + (totalToDiscard-(currTotal)) + " cards to discard");
	}
	
	private void initialize(Player p) {
		
		brickToDiscard = 0;
		wheatToDiscard = 0;
		woodToDiscard = 0;
		oreToDiscard = 0;
		sheepToDiscard = 0;
		
		maxBrick = p.getNumOfResource(ResourceType.BRICK);
		maxSheep = p.getNumOfResource(ResourceType.SHEEP);
		maxWood = p.getNumOfResource(ResourceType.WOOD);
		maxOre = p.getNumOfResource(ResourceType.ORE);
		maxWheat = p.getNumOfResource(ResourceType.WHEAT);
		
		totalToDiscard = p.getTotalResources() / 2;
		currTotal = 0;
		
		this.getDiscardView().setResourceMaxAmount(ResourceType.BRICK, maxBrick);
		this.getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, maxSheep);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, maxWood);
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, maxOre);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, maxWheat);
		
		this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brickToDiscard);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheepToDiscard);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, woodToDiscard);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE, oreToDiscard);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheatToDiscard);
		
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, maxBrick > 0, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, maxSheep > 0, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, maxWood > 0, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, maxOre > 0, false);
		this.getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, maxWheat > 0, false);
		
		this.getDiscardView().setStateMessage("Still " + totalToDiscard + " cards to discard");
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.getDiscardView().showModal();
	}

	public void update(GameState gameState) {
		if(gameState == GameState.DISCARD) {
			int pIndex = ClientFacade.getInstance().getUserData().getPlayerIndex();
			Player p = ClientFacade.getInstance().game.getPlayers().getPlayerByIndex(pIndex);
			int res = p.getTotalResources();
			if (!discarding && res > 7 && !firstTime) {
				discarding = true;
				initialize(p);
			} else if (waiting) {
				waitView.setMessage("Waiting for slow pokes to discard");
				waitView.showModal();
			} else if (firstTime) {
				firstTime = false;
			} else if (discarding) {
				if (!this.getDiscardView().isModalShowing()) {
					this.getDiscardView().showModal();
				}
			} else if (!discarding) {
				waiting = true;
				waitView.setMessage("Waiting for slow pokes to discard");
				waitView.showModal();
			}
		}
		else {
			waiting = false;
			if(waitView.isModalShowing()) {
				waitView.closeModal();
			}
		}
	}

	@Override
	public void discard() {
		discarding = false;
		waiting = true;
		firstTime = true;
		try {
			ClientFacade.getInstance().discardCards(sheepToDiscard, woodToDiscard, brickToDiscard, wheatToDiscard, oreToDiscard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getDiscardView().closeModal();
	}

}

