package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.joniaromaa.p2pchat.network.communication.handler.ClientConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.SolveChallengeOutgoingPacket;
import io.netty.channel.embedded.EmbeddedChannel;

public class RequestChallengeIncomingPacketTest
{
	@Test
	public void createsChallange() throws Exception
	{
		ClientConnectionHandler handler = new ClientConnectionHandler(null);
		
		EmbeddedChannel channel = new EmbeddedChannel(handler);
		
		RequestChallengeIncomingPacket packet = new RequestChallengeIncomingPacket();
		
		assertEquals(null, handler.getPendingChallenge());
		
		packet.handle(handler);
		
		assertTrue(handler.getPendingChallenge().length >= 256);
		
		assertEquals(SolveChallengeOutgoingPacket.class, channel.readOutbound().getClass());
	}
}
