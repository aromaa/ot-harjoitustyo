package fi.joniaromaa.p2pchat.network.communication.handler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.RequestChallengeOutgoingPacket;
import io.netty.channel.embedded.EmbeddedChannel;

public class ServerConnectionHandlerTest
{
	@Test
	public void testRequestsChallenge()
	{
		EmbeddedChannel channel = new EmbeddedChannel(new ServerConnectionHandler(null));

		assertEquals(RequestChallengeOutgoingPacket.class, channel.readOutbound().getClass());
	}
}
