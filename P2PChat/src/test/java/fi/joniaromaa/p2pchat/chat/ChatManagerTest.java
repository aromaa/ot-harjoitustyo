package fi.joniaromaa.p2pchat.chat;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.network.communication.handler.ClientConnectionHandler;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import fi.joniaromaa.p2pchat.utils.IdentityUtils;
import io.netty.channel.embedded.EmbeddedChannel;

public class ChatManagerTest
{
	private SqliteStorage storage;
	private ChatManager chatManager;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		this.storage = new SqliteStorage(":memory:");
		
		this.chatManager = new ChatManager(IdentityUtils.generateMyIdentity("Joni"), this.storage);
	}
	
	@Test
	public void getConnectionTest()
	{
		ContactIdentity contact = this.chatManager.getContacts().getOrAdd(EncryptionUtils.generateKeyPair().getPublic(), "Janne");
		
		assertEquals(null, this.chatManager.getChannel(contact));
		
		ClientConnectionHandler connectionHandler = new ClientConnectionHandler(null);
		connectionHandler.setContact(contact);
		
		EmbeddedChannel channel = new EmbeddedChannel(connectionHandler);
		
		this.chatManager.onConnection(connectionHandler);
		
		assertEquals(channel, this.chatManager.getChannel(contact));
		
		this.chatManager.onDisconnect(contact);
		
		assertEquals(null, this.chatManager.getChannel(contact));
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
	}
}
