package fi.joniaromaa.p2pchat.storage.sqlite;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.security.PublicKey;
import java.sql.SQLException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class SqliteStorageConversationAdapterTest
{
	private static final File TEST_FOLDER = new File("automated-tests-conversation");
	
	private SqliteStorage storage;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		SqliteStorageConversationAdapterTest.TEST_FOLDER.mkdirs();
		
		this.storage = new SqliteStorage(new File(SqliteStorageConversationAdapterTest.TEST_FOLDER, "test.db"));
	}
	
	@Test
	public void createTest()
	{
		ConversationDao dao = storage.getConversationDao();
		
		PublicKey key = EncryptionUtils.generateKeyPair().getPublic();
		
		ContactIdentity fakeContact = new ContactIdentity(66, key, "Joni");

		assertTrue(dao.create(fakeContact).isPresent());
		assertFalse(dao.create(fakeContact).isPresent());
	}
	
	@Test
	public void getTest()
	{
		ConversationDao dao = storage.getConversationDao();
		
		PublicKey key = EncryptionUtils.generateKeyPair().getPublic();
		
		ContactIdentity fakeContact = new ContactIdentity(66, key, "Joni");
		
		assertFalse(dao.get(fakeContact).isPresent());
		assertTrue(dao.create(fakeContact).isPresent());
		assertTrue(dao.get(fakeContact).isPresent());
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
		
		FileUtils.deleteDirectory(SqliteStorageConversationAdapterTest.TEST_FOLDER);
	}
}
