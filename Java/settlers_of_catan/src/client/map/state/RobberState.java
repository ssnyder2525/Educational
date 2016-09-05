package client.map.state;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.clientFacade.ClientFacade;
import client.data.RobPlayerInfo;
import client.map.IMapView;
import client.map.IRobView;
import client.map.MapView;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.models.mapClasses.Piece;
import shared.models.mapClasses.WaterHex;


/**
 * The player can only move the robber
 * 		Transitions back to MyTurn
 * 
 */
public class RobberState extends OutdatedState {
	
	private IRobView robView;
	private HexLocation newRobberLoc;

	public RobberState(IMapView view, IRobView robView) {
		super(view);
		this.robView = robView;
	}

	public void initFromModel() { 
		for (int x = 0; x <= 3; ++x) {
			
			int maxY = 3 - x;			
			for (int y = -3; y <= maxY; ++y) {				

				HexLocation hexLoc = new HexLocation(x, y);
				HexType hexType;
				if (ClientFacade.getInstance().getHex(hexLoc) == null) {
					hexType = HexType.WATER;
				}
				else {
					hexType = ClientFacade.getInstance().getHex(hexLoc).getHexType();
					if (hexType.equals(HexType.WATER)) {
						WaterHex hex = (WaterHex) ClientFacade.getInstance().getHex(hexLoc);
						PortType portType = hex.getPortType();
						if (x > 0) {
							if (y <= 0) {
								getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.SouthWest), portType);
							}
							else {
								getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.NorthWest), portType);
							}
						}
						else {
							if (y <= 0) {
								getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.South), portType);
							}
							else {
								getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.North), portType);
							}
						}
					}
				}
				getView().addHex(hexLoc, hexType);
				
				ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
				
				EdgeLocation nwEdge = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
				edges.add(nwEdge);
				EdgeLocation nEdge = new EdgeLocation(hexLoc, EdgeDirection.North);
				edges.add(nEdge);
				EdgeLocation neEdge = new EdgeLocation(hexLoc, EdgeDirection.NorthEast);
				edges.add(neEdge);
				EdgeLocation swEdge = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
				edges.add(swEdge);
				EdgeLocation sEdge = new EdgeLocation(hexLoc, EdgeDirection.South);
				edges.add(sEdge);
				EdgeLocation seEdge = new EdgeLocation(hexLoc, EdgeDirection.SouthEast);
				edges.add(seEdge);
				
				for (EdgeLocation edgeLoc : edges) {
					Piece edgePiece = ClientFacade.getInstance().getEdge(edgeLoc);
					if (edgePiece != null) {
						int owner = edgePiece.getOwner();
						getView().placeRoad(edgeLoc,
								ClientFacade.getInstance().getColorById(owner));	
					}
				}
				
				ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
					
				VertexLocation nwVertex = new VertexLocation(hexLoc,  VertexDirection.NorthWest);
				vertices.add(nwVertex);
				VertexLocation neVertex = new VertexLocation(hexLoc,  VertexDirection.NorthEast);
				vertices.add(neVertex);
				VertexLocation eVertex = new VertexLocation(hexLoc,  VertexDirection.East);
				vertices.add(eVertex);
				VertexLocation seVertex = new VertexLocation(hexLoc,  VertexDirection.SouthEast);
				vertices.add(seVertex);
				VertexLocation swVertex = new VertexLocation(hexLoc,  VertexDirection.SouthWest);
				vertices.add(swVertex);
				VertexLocation wVertex = new VertexLocation(hexLoc,  VertexDirection.West);
				vertices.add(wVertex);
				
				for (VertexLocation vertex: vertices) {
					Piece vertexPiece = ClientFacade.getInstance().getVertex(vertex);
					if (vertexPiece != null) {
						int owner = vertexPiece.getOwner();
						if (vertexPiece.getType().toString().equals("CITY")) {
							getView().placeCity(vertex, 
									ClientFacade.getInstance().getColorById(owner));
						}
						else {
							getView().placeSettlement(vertex, 
									ClientFacade.getInstance().getColorById(owner));
						}
						
					}
				}
				
				if (ClientFacade.getInstance().getHex(hexLoc) != null) {
					if (ClientFacade.getInstance().getHex(hexLoc).getToken() != -1) {
						getView().addNumber(hexLoc, ClientFacade.getInstance().getHex(hexLoc).getToken());
					}
				}
			}
			
			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					HexLocation hexLoc = new HexLocation(-x, y);
					HexType hexType;
					if (ClientFacade.getInstance().getHex(hexLoc) == null) {
						hexType = HexType.WATER;
					}
					else {
						hexType = ClientFacade.getInstance().getHex(hexLoc).getHexType();
						if (hexType.equals(HexType.WATER)) {
							WaterHex hex = (WaterHex) ClientFacade.getInstance().getHex(hexLoc);
							PortType portType = hex.getPortType();
							if (-x < 0) {
								if (y <= 0) {
									getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.SouthEast), portType);
								}
								else {
									getView().addPort(new EdgeLocation(hexLoc, EdgeDirection.NorthEast), portType);
								}
							}
						}
					}
					
					getView().addHex(hexLoc, hexType);
					
					ArrayList<EdgeLocation> edges = new ArrayList<EdgeLocation>();
					
					EdgeLocation nwEdge = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
					edges.add(nwEdge);
					EdgeLocation nEdge = new EdgeLocation(hexLoc, EdgeDirection.North);
					edges.add(nEdge);
					EdgeLocation neEdge = new EdgeLocation(hexLoc, EdgeDirection.NorthEast);
					edges.add(neEdge);
					EdgeLocation swEdge = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
					edges.add(swEdge);
					EdgeLocation sEdge = new EdgeLocation(hexLoc, EdgeDirection.South);
					edges.add(sEdge);
					EdgeLocation seEdge = new EdgeLocation(hexLoc, EdgeDirection.SouthEast);
					edges.add(seEdge);
					
					for (EdgeLocation edgeLoc : edges) {
						Piece edgePiece = ClientFacade.getInstance().getEdge(edgeLoc);
						if (edgePiece != null) {
							int owner = edgePiece.getOwner();
							getView().placeRoad(edgeLoc,
									ClientFacade.getInstance().getColorById(owner));	
						}
					}
					
					ArrayList<VertexLocation> vertices = new ArrayList<VertexLocation>();
						
					VertexLocation nwVertex = new VertexLocation(hexLoc,  VertexDirection.NorthWest);
					vertices.add(nwVertex);
					VertexLocation neVertex = new VertexLocation(hexLoc,  VertexDirection.NorthEast);
					vertices.add(neVertex);
					VertexLocation eVertex = new VertexLocation(hexLoc,  VertexDirection.East);
					vertices.add(eVertex);
					VertexLocation seVertex = new VertexLocation(hexLoc,  VertexDirection.SouthEast);
					vertices.add(seVertex);
					VertexLocation swVertex = new VertexLocation(hexLoc,  VertexDirection.SouthWest);
					vertices.add(swVertex);
					VertexLocation wVertex = new VertexLocation(hexLoc,  VertexDirection.West);
					vertices.add(wVertex);
					
					for (VertexLocation vertex: vertices) {
						Piece vertexPiece = ClientFacade.getInstance().getVertex(vertex);
						if (vertexPiece != null) {
							int owner = vertexPiece.getOwner();
							if (vertexPiece.getType().toString().equals("CITY")) {
								getView().placeCity(vertex, 
										ClientFacade.getInstance().getColorById(owner));
							}
							else {
								getView().placeSettlement(vertex, 
										ClientFacade.getInstance().getColorById(owner));
							}
							
						}
					}
					
					if (ClientFacade.getInstance().getHex(hexLoc) != null) {
						if (ClientFacade.getInstance().getHex(hexLoc).getToken() != -1) {
							getView().addNumber(hexLoc, ClientFacade.getInstance().getHex(hexLoc).getToken());
						}
					}
				}
			}
		}
		
		getView().placeRobber(ClientFacade.getInstance().getRobberLocation().getHexLoc());
		this.startMove(PieceType.ROBBER, true, true);
	}
	
	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		getView().startDrop(pieceType, this.color, false);
	}
	
	@Override
	public void placeRobber(HexLocation hexLoc) {
		newRobberLoc = hexLoc;
		RobPlayerInfo[] candidateVictims = new RobPlayerInfo[0];
		try {
		
			try {
				candidateVictims = ClientFacade.getInstance().getVictims(hexLoc);
			} catch (Exception e) {
				if (e.getMessage() == "placeRobber failure") {
					
				}
				else {
					throw e;
				}
			}
	
			if(candidateVictims.length == 0) {
				try {
					ClientFacade.getInstance().robPlayer(-1, hexLoc);
				} catch (Exception e) {}
			}
			else if(candidateVictims.length == 1) {
				try {
					ClientFacade.getInstance().robPlayer(candidateVictims[0].getPlayerIndex(), hexLoc);
				} catch (Exception e) {}
			}
			else {
				this.robView.setPlayers(candidateVictims);
				this.robView.showModal();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return ClientFacade.getInstance().canPlaceRobber(hexLoc);
	}
	
	@Override
	public void robPlayer(RobPlayerInfo victim) {
		try {
			ClientFacade.getInstance().robPlayer(victim.getPlayerIndex(), this.newRobberLoc);
		} catch (Exception e) {
			JOptionPane.showMessageDialog((MapView)this.getView(), "Failed to Place the robber");
		}
	}
}
