package client.serverPoller;

import static org.junit.Assert.*;

import org.junit.Test;

import client.serverProxy.FakeProxy;
import shared.models.Game;

public class ServerPollerTest {

	//if this works, that means the game is being updated successfully. To test accuracy, see the deserializer test.
	@Test
	public void testServerPoller() {
		Game game = new Game();
		FakeProxy proxy = new FakeProxy();
		ServerPoller poller = new ServerPoller(game, proxy, 1000);
		String gameModelString = proxy.getGameModel(0);
		poller.updateModelTester(gameModelString);
		assertTrue(poller.GameModel.getPlayers().getPlayerByIndex(0).getName().equals("Sam"));
		assertTrue(true);
	}

}
