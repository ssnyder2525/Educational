package shared.models.mapClasses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.RobberLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class MapTest {
	Map map;

	@Before
	public void setUp() throws Exception 
	{
		HexLocation hexLoc;
		
		// Build HexMap (Terrain Hex)
		Hex hex;
		HexMap hexMap = new HexMap();

		hexLoc = new HexLocation(0, -2);
		hex = new Hex(HexType.DESERT, -1);
		hexMap.setHex(hexLoc, hex);
		
		RobberLocation robber = new RobberLocation(hexLoc);

		hexLoc = new HexLocation(1, -2);
		hex = new Hex(HexType.BRICK, 4);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(2, -2);
		hex = new Hex(HexType.WOOD, 11);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-1, -1);
		hex = new Hex(HexType.BRICK, 8);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(0, -1);
		hex = new Hex(HexType.WOOD, 3);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(1, -1);
		hex = new Hex(HexType.ORE, 9);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(2, -1);
		hex = new Hex(HexType.SHEEP, 12);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-2, 0);
		hex = new Hex(HexType.ORE, 5);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-1, 0);
		hex = new Hex(HexType.SHEEP, 10);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(0, 0);
		hex = new Hex(HexType.WHEAT, 11);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(1, 0);
		hex = new Hex(HexType.BRICK, 5);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(2, 0);
		hex = new Hex(HexType.WHEAT, 6);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-2, 1);
		hex = new Hex(HexType.WHEAT, 2);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-1, 1);
		hex = new Hex(HexType.SHEEP, 9);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(0, 1);
		hex = new Hex(HexType.WOOD, 4);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(1, 1);
		hex = new Hex(HexType.SHEEP, 10);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-2, 2);
		hex = new Hex(HexType.WOOD, 6);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-1, 2);
		hex = new Hex(HexType.ORE, 3);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(0, 2);
		hex = new Hex(HexType.WHEAT, 8);
		hexMap.setHex(hexLoc, hex);
		
		
		
		// Build Ports (WaterHex)
		
		hexLoc = new HexLocation(1, -3);
		hex = new WaterHex(PortType.ORE, EdgeDirection.South);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(3, -3);
		hex = new WaterHex(PortType.THREE, EdgeDirection.SouthWest);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(2, 1);
		hex = new WaterHex(PortType.THREE, EdgeDirection.NorthWest);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(-2, 3);
		hex = new WaterHex(PortType.BRICK, EdgeDirection.NorthEast);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(-1, -2);
		hex = new WaterHex(PortType.WHEAT, EdgeDirection.South);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(-3, 2);
		hex = new WaterHex(PortType.WOOD, EdgeDirection.NorthEast);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(-3, 0);
		hex = new WaterHex(PortType.THREE, EdgeDirection.SouthEast);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(3, -1);
		hex = new WaterHex(PortType.SHEEP, EdgeDirection.NorthWest);
		hexMap.setHex(hexLoc, hex);
		
		hexLoc = new HexLocation(0, 3);
		hex = new WaterHex(PortType.THREE, EdgeDirection.North);
		hexMap.setHex(hexLoc, hex);
		
		
		
		// Build remaining WaterHex
		
		hexLoc = new HexLocation(-3, 1);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-3, 3);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-2, -1);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(-1, 3);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(3, 0);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(3, -2);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(2, -3);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(1, 2);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);

		hexLoc = new HexLocation(0, -3);
		hex = new WaterHex(null, null);
		hexMap.setHex(hexLoc, hex);
		
		
		
		// EdgeMap
		
		PlayerMap pm = new PlayerMap();
		
		EdgeLocation edgeLoc;
		Piece p;
		EdgeMap edges = new EdgeMap();
		
		hexLoc = new HexLocation(-1, -1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.South);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(3);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 3);
		
		hexLoc = new HexLocation(-1, 1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(3);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 3);
		
		hexLoc = new HexLocation(-1, 1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.NorthWest);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(3);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 3);
		
		hexLoc = new HexLocation(2, -2);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(3);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 3);
		
		hexLoc = new HexLocation(1, -1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.South);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(2);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 2);
		
		hexLoc = new HexLocation(0, 1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.South);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(0);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 0);
		
		hexLoc = new HexLocation(0, 0);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.South);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(2);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 2);
		
		hexLoc = new HexLocation(-2, 1);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(1);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 1);
		
		hexLoc = new HexLocation(2, 0);
		edgeLoc = new EdgeLocation(hexLoc, EdgeDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.ROAD);
		p.setOwner(0);
		edges.setEdge(edgeLoc, p);
		pm.addRoad(edgeLoc, 0);
		
		
		// Set Vertexes
		
		VertexLocation vertexLoc;
		VertexMap vertexes = new VertexMap();
		
		hexLoc = new HexLocation(-1, 1);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(3);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 3);
		
		hexLoc = new HexLocation(1, -2);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthEast);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(3);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 3);
		
		hexLoc = new HexLocation(0, 0);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(2);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 2);
		
		hexLoc = new HexLocation(1, -1);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(2);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 2);
		
		hexLoc = new HexLocation(-2, 1);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(1);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 1);
		
		hexLoc = new HexLocation(0, 1);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthEast);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(0);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 0);
		
		hexLoc = new HexLocation(-1, -1);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(1);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 1);
		
		hexLoc = new HexLocation(2, 0);
		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
		p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(0);
		vertexes.setVertex(vertexLoc, p);
		pm.addSettlement(vertexLoc, 0);
		

		this.map = new Map(hexMap, vertexes, edges, robber, pm);
		
	}

	@After
	public void tearDown() throws Exception 
	{
		this.map = null;
	}


	@Test
	public void testCanMaritimeTrade()
	{
		assertFalse("Error: should be false", this.map.canMaritimeTrade(0, ResourceType.BRICK));
		assertFalse("Error: should be false", this.map.canMaritimeTrade(0, ResourceType.ORE));
		assertFalse("Error: should be false", this.map.canMaritimeTrade(0, ResourceType.SHEEP));
		assertFalse("Error: should be false", this.map.canMaritimeTrade(0, ResourceType.WHEAT));
		assertFalse("Error: should be false", this.map.canMaritimeTrade(0, ResourceType.WOOD));
		assertTrue("Error: should be true", this.map.canMaritimeTrade(0, null));
		
		// Add a Settlement to a harbor
		HexLocation hexLoc = new HexLocation(-2, 3);
		VertexLocation vertexLoc = new VertexLocation(hexLoc, VertexDirection.NorthEast);
		Piece p = new Piece();
		p.setType(PieceType.SETTLEMENT);
		p.setOwner(0);
		try {
			this.map.setVertex(vertexLoc, p);
		} catch (InvalidTypeException e) {
			fail("InvalidTypeException");
		}
		this.map.addSettlementToPlayerMap(vertexLoc, 0);
		assertTrue("Error: should be true", this.map.canMaritimeTrade(0, ResourceType.BRICK));	
	}
	
//	
//
//	@Test
//	public void testCanPlaceCity() throws InvalidTypeException
//	{
//		assertFalse("Error: should be false", this.map.canPlaceCity(1, -1, "SW", 3)); // On top of someone elses settlement
//		assertTrue("Error: should be true", this.map.canPlaceCity(-1, 1, "SW", 3)); // On top of their settlement
//		assertFalse("Error: should be false", this.map.canPlaceCity(-1, 1, "SE", 3)); // Their City is one away
//		assertFalse("Error: should be false", this.map.canPlaceCity(1, -1, "SE", 3)); // A City is one away
//		assertTrue("Error: should be true", this.map.canPlaceCity(1, -2, "SE", 3)); // On top of their settlement
//
//		// Add a city to a legal position and test if you can add another one
//		HexLocation hexLoc;
//		Piece p;
//		VertexLocation vertexLoc;
//		
//		hexLoc = new HexLocation(-1, 1);
//		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
//		p = new Piece();
//		p.setType(PieceType.CITY);
//		p.setOwner(3);
//		this.map.setVertex(vertexLoc, p);
//		this.map.addSettlementToPlayerMap(vertexLoc, 3);
//		
//		assertFalse("Error: should be false", this.map.canPlaceCity(-1, 1, "SW", 3));
//		
//		// add a city to someone else and try to build a city over it
//		
//		hexLoc = new HexLocation(1, -1);
//		vertexLoc = new VertexLocation(hexLoc, VertexDirection.SouthWest);
//		p = new Piece();
//		p.setType(PieceType.CITY);
//		p.setOwner(2);
//		this.map.setVertex(vertexLoc, p);
//		this.map.addSettlementToPlayerMap(vertexLoc, 2);
//		
//		assertFalse("Error: should be false", this.map.canPlaceCity(1, -1, "SW", 3));
//		
//	}
//
//	@Test
//	public void testCanPlaceSettlement()
//	{
//		assertFalse("Error: should be false", this.map.canPlaceSettlement(1, -1, "SW", 3)); // On top of someone elses settlement
//		assertFalse("Error: should be false", this.map.canPlaceSettlement(-1, 1, "SW", 3)); // On top of their settlement
//		assertFalse("Error: should be false", this.map.canPlaceSettlement(-1, 1, "SE", 3)); // Their City is one away
//		assertFalse("Error: should be false", this.map.canPlaceSettlement(1, -1, "SE", 3)); // A City is one away
//		assertTrue("Error: should be true", this.map.canPlaceSettlement(-1, 1, "NW", 3)); // A valid location		
//		
//	}
//
//	@Test
//	public void testCanPlaceRoad()
//	{
//		assertTrue("Error: should be true", this.map.canPlaceRoad(-2, 1, "NW", 1)); // Next to their road
//		assertFalse("Error: should be false", this.map.canPlaceRoad(-2, 1, "SW", 1)); // On their road
//		assertFalse("Error: should be false", this.map.canPlaceRoad(2, 0, "SW", 1)); // On another road
//		assertFalse("Error: should be false", this.map.canPlaceRoad(2, 0, "NW", 1)); // Next to another road
//	}
//
//	@Test
//	public void testCanPlaceRobber()
//	{
//		assertFalse("Error: should be false", this.map.canPlaceRobber(0, -2));
//		assertFalse("Error: should be false", this.map.canPlaceRobber(0, -3));
//		assertTrue("Error: should be true", this.map.canPlaceRobber(1, 1));
//	}

}
