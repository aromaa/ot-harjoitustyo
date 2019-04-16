package fi.joniaromaa.p2pchat.chat.contacts;

import static org.junit.Assert.assertEquals;
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
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class ContactIdentityManagerTest
{
	private static final File TEST_FOLDER = new File("automated-tests-cim");
	
	private SqliteStorage storage;
	
	private ContactIdentityManager contactIdentity;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		ContactIdentityManagerTest.TEST_FOLDER.mkdirs();
		
		this.storage = new SqliteStorage(new File(ContactIdentityManagerTest.TEST_FOLDER, "test.db"));
		
		this.contactIdentity = new ContactIdentityManager(this.storage.getContactsDao());
	}
	
	@Test
	public void testAdd()
	{
		PublicKey publicKey = EncryptionUtils.generateKeyPair().getPublic();
		
		ContactIdentity contactNew = this.contactIdentity.getOrAdd(publicKey, "Joni");
		ContactIdentity contactOld = this.contactIdentity.getOrAdd(publicKey, "Joni");
		
		assertTrue(contactNew == contactOld);
	}
	
	@Test
	public void testGet()
	{
		PublicKey publicKey = EncryptionUtils.generateKeyPair().getPublic();
		
		assertFalse(this.contactIdentity.getContact(publicKey).isPresent());
		
		this.contactIdentity.getOrAdd(publicKey, "Joni");
		
		assertTrue(this.contactIdentity.getContact(publicKey).isPresent());
	}
	
	@Test
	public void testUpdate()
	{
		PublicKey publicKey = EncryptionUtils.generateKeyPair().getPublic();
		
		this.contactIdentity.getOrAdd(publicKey, "Joni");
		
		assertEquals("Janne", this.contactIdentity.addOrUpdate(publicKey, "Janne").getNickname());
		assertEquals("Janne", this.contactIdentity.getContact(publicKey).get().getNickname());
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
		
		FileUtils.deleteDirectory(ContactIdentityManagerTest.TEST_FOLDER);
	}
}
