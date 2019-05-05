package fi.joniaromaa.p2pchat.network.communication.incoming.authentication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.chat.ChatManager;
import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.network.communication.handler.ClientConnectionHandler;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import fi.joniaromaa.p2pchat.utils.IdentityUtils;
import io.netty.channel.embedded.EmbeddedChannel;

public class WhoAreYouIncomingPacketTest
{
	private MyIdentity identity;
	
	private ClientConnectionHandler handler;
	private EmbeddedChannel channel;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		this.identity = IdentityUtils.generateMyIdentity("Joni");
		this.handler = new ClientConnectionHandler(new ChatManager(this.identity, new SqliteStorage(":memory:")));
		
		this.channel = new EmbeddedChannel(this.handler);
	}
	
	@Test
	public void testInvalidNick() throws Exception
	{
		WhoAreYouIncomingPacket packet = new WhoAreYouIncomingPacket(null, "", null);
		packet.handle(this.handler);
		
		assertFalse(this.channel.isActive());
	}
	
	@Test
	public void testSameUser() throws Exception
	{
		byte[] challenge = EncryptionUtils.requestRandomBytes(256);
		
		byte[] signed = EncryptionUtils.getSignedChallange(this.identity.getKeyPair().getPrivate(), challenge);
		
		WhoAreYouIncomingPacket packet = new WhoAreYouIncomingPacket(this.identity.getPublicKeyBytes(), "Joni", signed);
		packet.handle(this.handler);
		
		assertFalse(this.channel.isActive());
	}
	
	@Test
	public void testOther() throws Exception
	{
		MyIdentity identity = IdentityUtils.generateMyIdentity("Janne");
		
		this.handler.createChallenge();
		
		byte[] signed = EncryptionUtils.getSignedChallange(identity.getKeyPair().getPrivate(), this.handler.getPendingChallenge());
		
		WhoAreYouIncomingPacket packet = new WhoAreYouIncomingPacket(identity.getPublicKeyBytes(), "Janne", signed);
		packet.handle(this.handler);
		
		assertTrue(this.channel.isActive());
	}
	
	@Test
	public void testOtherInvalid() throws Exception
	{
		MyIdentity identity = IdentityUtils.generateMyIdentity("Janne");
		
		this.handler.createChallenge();
		
		byte[] signed = EncryptionUtils.getSignedChallange(identity.getKeyPair().getPrivate(), this.handler.getPendingChallenge());
		signed[0]++; //Becomes invalid
		
		WhoAreYouIncomingPacket packet = new WhoAreYouIncomingPacket(identity.getPublicKeyBytes(), "Janne", signed);
		packet.handle(this.handler);
		
		assertFalse(this.channel.isActive());
	}
}
