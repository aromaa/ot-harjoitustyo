package fi.joniaromaa.p2pchat.network.communication.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import fi.joniaromaa.p2pchat.network.communication.outgoing.PingOutgoingPacket;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ClientConnectionHandlerTest
{
	@Test
	public void testCloseConnection() throws InterruptedException
	{
		EmbeddedChannel channel = new EmbeddedChannel(new IdleStateHandler(1, 0, 0, TimeUnit.SECONDS), new ClientConnectionHandler(null));
		
		assertTrue(channel.isActive());
		
		TimeUnit.SECONDS.sleep(2);
		
		channel.runPendingTasks();
		
		assertFalse(channel.isActive());
	}
	
	@Test
	public void testAsksPing() throws InterruptedException
	{
		EmbeddedChannel channel = new EmbeddedChannel(new IdleStateHandler(0, 1, 0, TimeUnit.SECONDS), new ClientConnectionHandler(null));
		
		TimeUnit.SECONDS.sleep(2);
		
		channel.runPendingTasks();

		assertEquals(PingOutgoingPacket.class, channel.readOutbound().getClass());
	}
}
