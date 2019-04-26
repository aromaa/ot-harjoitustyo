package fi.joniaromaa.p2pchat.network.communication.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConnectionHandlerTest
{
	@Test
	public void testLongChallange()
	{
		ClientConnectionHandler handler = new ClientConnectionHandler(null);
		
		assertEquals(null, handler.getPendingChallenge());
		
		handler.createChallenge();
		
		assertTrue(handler.getPendingChallenge().length >= 256);
	}
}
