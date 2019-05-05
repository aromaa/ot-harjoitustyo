package fi.joniaromaa.p2pchat.network.communication.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;

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
	
	@Test
	public void onlyOneChallengeTest()
	{
		ClientConnectionHandler handler = new ClientConnectionHandler(null);
		
		handler.createChallenge();
		
		boolean ex = false;
		
		try
		{
			handler.createChallenge();
		}
		catch(IllegalStateException e)
		{
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void noChallengeWhenContectTest()
	{
		ClientConnectionHandler handler = new ClientConnectionHandler(null);
		
		handler.setContact(new ContactIdentity(0, null, null));
		
		boolean ex = false;
		
		try
		{
			handler.createChallenge();
		}
		catch(IllegalStateException e)
		{
			ex = true;
		}
		
		assertTrue(ex);
	}
	
	@Test
	public void noContactChangeTest()
	{
		ClientConnectionHandler handler = new ClientConnectionHandler(null);
		
		handler.setContact(new ContactIdentity(0, null, null));
		
		boolean ex = false;
		
		try
		{
			handler.setContact(new ContactIdentity(0, null, null));
		}
		catch(IllegalStateException e)
		{
			ex = true;
		}
		
		assertTrue(ex);
	}
}
