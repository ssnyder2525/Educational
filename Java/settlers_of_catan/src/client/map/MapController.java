package client.map;

import client.base.Controller;
import client.clientFacade.ClientFacade;
import client.data.RobPlayerInfo;
import client.map.state.BaseState;
import client.map.state.DiscardState;
import client.map.state.EndOfGameState;
import client.map.state.LoginState;
import client.map.state.MyTurnState;
import client.map.state.NotMyTurnState;
import client.map.state.OutdatedState;
import client.map.state.RobberState;
import client.map.state.Setup1State;
import client.map.state.Setup2State;
import client.map.state.TradeAcceptState;
import client.map.state.TradeOfferState;
import shared.communication.proxy.RoadBuilding;
import shared.communication.proxy.SoldierMove;
import shared.definitions.GameState;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.observers.MapObserver;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
	private BaseState state;
	private SoldierMove sm = new SoldierMove();
	private RoadBuilding rb = new RoadBuilding();
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		MapObserver obs = new MapObserver(this);
		ClientFacade.getInstance().game.addObserver(obs);
		
		setRobView(robView);
		
		this.setState(GameState.LOGIN);
		
		initFromModel();
		
	}
	
	
	
// SETTERS
	public void setState (BaseState state)
	{
		this.state = state;
	}
	
	
	public void setState (GameState gameState)
	{
		switch(gameState)
		{
			case SETUP1:
				if (ClientFacade.getInstance().game.getPlayers().getNumberOfPlayers() != 4)
				{
					this.state = new LoginState(this.getView());
				}
				else if (this.state.getClass() != Setup1State.class)
				{
					this.state = new Setup1State(this.getView());
					this.initFromModel();
				}
				break;
			case SETUP2:
				if (this.state.getClass() != Setup2State.class)
				{
					this.state = new Setup2State(this.getView());
					this.initFromModel();
				}
				break;
			case MYTURN:
				this.state = new MyTurnState(this.getView(), this.robView, sm, rb);
				this.initFromModel();
				break;
			case NOTMYTURN:
				this.state = new NotMyTurnState(this.getView());
				this.initFromModel();
				break;
			case ROBBER:
				if (this.state.getClass() != RobberState.class)
				{
					this.state = new RobberState(this.getView(), this.getRobView());
					this.initFromModel();
				}
				break;
			case TRADEOFFER:
				this.state = new TradeOfferState(this.getView());
				this.initFromModel();
				break;
			case TRADEACCEPT:
				this.state = new TradeAcceptState(this.getView());
				this.initFromModel();
				break;
			case OUTDATED:
				this.state = new OutdatedState(this.getView());
				this.initFromModel();
				break;
			case DISCARD:
				this.state = new DiscardState(this.getView());
				this.initFromModel();
				break;
			case ENDOFGAME:
				this.state = new EndOfGameState(this.getView());
				this.initFromModel();
				break;
			case LOGIN:
			case ROLLING:
			default:
				this.state = new LoginState(this.getView());
		}
	}
	
	
	private void setRobView(IRobView robView) 
	{
		this.robView = robView;
	}
	
	
	
// GETTERS
	public BaseState getState ()
	{
		return this.state;
	}
	
	
	public IMapView getView()
	{
		return (IMapView)super.getView();
	}
	
	
	private IRobView getRobView() {
		return robView;
	}
	
	
	
// Protected METHODS
	public void update(GameState gameState)
	{
		this.setState(gameState);
	}
	
	protected void initFromModel() {
		
		this.state.initFromModel();
	}

	
	
// Public METHODS
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return this.state.canPlaceRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return this.state.canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return this.state.canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return this.state.canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		this.state.placeRoad(edgeLoc);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		this.state.placeSettlement(vertLoc);
	}

	public void placeCity(VertexLocation vertLoc) {
		this.state.placeCity(vertLoc);
	}

	public void placeRobber(HexLocation hexLoc) {
		this.state.placeRobber(hexLoc);
	}
	
	// allowConnected == true for setup1 and setup2 ======== isFree == true for setup1 and setup2
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
		this.state.startMove(pieceType, isFree, allowDisconnected);
	}
	
	public void cancelMove() {
		
		this.state.cancelMove();
	}
	
	public void playSoldierCard() {	
		this.state.playSoldierCard();
	}
	
	public void playRoadBuildingCard() {	
		
		this.state.playRoadBuildingCard();
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
		this.state.robPlayer(victim);
	}	
}

