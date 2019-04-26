package fi.joniaromaa.p2pchat.chat.conversation;

import static org.junit.Assert.assertTrue;

import java.security.PublicKey;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.chat.contacts.ContactIdentityManager;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class ChatConversationManagerTest
{
	private SqliteStorage storage;
	
	private ChatConversationManager conversationManager;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		this.storage = new SqliteStorage(":memory:");
		
		this.conversationManager = new ChatConversationManager(this.storage.getConversationDao(), new ContactIdentityManager(this.storage.getContactsDao()));
	}
	
	@Test
	public void testCreate()
	{
		PublicKey publicKey = EncryptionUtils.generateKeyPair().getPublic();
		
		ContactIdentity identity = new ContactIdentity(66, publicKey, "Joni");
		
		ChatConversation newConvo = this.conversationManager.getOrCreate(identity);
		ChatConversation oldConvo = this.conversationManager.getOrCreate(identity);
		
		assertTrue(newConvo == oldConvo); //Local cache should return the same
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
	}
}
