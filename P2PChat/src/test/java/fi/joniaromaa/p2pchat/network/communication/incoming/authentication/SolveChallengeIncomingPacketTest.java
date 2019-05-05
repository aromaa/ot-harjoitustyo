package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.communication.handler.ClientConnectionHandler;
import fi.joniaromaa.p2pchat.network.communication.outgoing.authentication.WhoAreYouOutgoingPacket;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import fi.joniaromaa.p2pchat.utils.IdentityUtils;
import io.netty.channel.embedded.EmbeddedChannel;

public class SolveChallengeIncomingPacketTest
{
	@Test
	public void testPacket() throws Exception
	{
		MyIdentity identity = IdentityUtils.generateMyIdentity("Joni");
		
		ClientConnectionHandler handler = new ClientConnectionHandler(new ChatManager(identity, new SqliteStorage(":memory:")));
		
		EmbeddedChannel channel = new EmbeddedChannel(handler);

		byte[] challenge = EncryptionUtils.requestRandomBytes(256);
		
		SolveChallengeIncomingPacket packet = new SolveChallengeIncomingPacket(challenge);
		packet.handle(handler);
		
		WhoAreYouOutgoingPacket outgoing = (WhoAreYouOutgoingPacket)channel.readOutbound();
		
		assertTrue(EncryptionUtils.verifyChallange(identity.getKeyPair().getPublic(), challenge, outgoing.getChallenge()));
	}
}
