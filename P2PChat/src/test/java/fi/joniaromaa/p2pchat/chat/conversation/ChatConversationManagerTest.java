package fi.joniaromaa.p2pchat.chat.conversation;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.security.PublicKey;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.chat.contacts.ContactIdentityManager;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class ChatConversationManagerTest
{
	private static final File TEST_FOLDER = new File("automated-tests-cvm");
	
	private SqliteStorage storage;
	
	private ChatConversationManager conversationManager;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		ChatConversationManagerTest.TEST_FOLDER.mkdirs();
		
		this.storage = new SqliteStorage(new File(ChatConversationManagerTest.TEST_FOLDER, "test.db"));
		
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
		
		FileUtils.deleteDirectory(ChatConversationManagerTest.TEST_FOLDER);
	}
}
