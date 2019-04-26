package fi.joniaromaa.p2pchat.storage.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class SqliteStorageContactsAdapterTest
{
	private SqliteStorage storage;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		this.storage = new SqliteStorage(":memory:");
	}

	@Test
	public void trySaveIdentity()
	{
		ContactsDao dao = this.storage.getContactsDao();
		
		PublicKey key = EncryptionUtils.generateKeyPair().getPublic();
		
		assertTrue(dao.addContact(key, "Joni").isPresent());
		assertFalse(dao.addContact(key, "Janne").isPresent());
		
		Optional<ContactIdentity> identity = dao.getContact(key);
		
		assertEquals("Joni", identity.get().getNickname());
	}
	
	@Test
	public void tryGetIdentity()
	{
		ContactsDao dao = this.storage.getContactsDao();

		PublicKey key = EncryptionUtils.generateKeyPair().getPublic();
		
		Optional<ContactIdentity> contact = dao.getContact(key);
		
		assertFalse(contact.isPresent());

		contact = dao.addContact(key, "Joni");
		
		assertTrue(contact.isPresent());
		assertTrue(Arrays.equals(contact.get().getPublicKey().getEncoded(), key.getEncoded()));
		assertEquals("Joni", contact.get().getNickname());
	}
	
	@Test
	public void tryUpdateIdentity()
	{
		ContactsDao dao = this.storage.getContactsDao();
		
		PublicKey key = EncryptionUtils.generateKeyPair().getPublic();
		
		ContactIdentity fakeContact = new ContactIdentity(66, key, "Joni");
		
		dao.updateContact(fakeContact);
		
		assertFalse(dao.getContact(key).isPresent());
		
		dao.addContact(fakeContact.getPublicKey(), "Janne");
		
		ContactIdentity realContact = dao.getContact(fakeContact.getPublicKey()).get();
		
		assertEquals("Janne", realContact.getNickname());
		
		realContact.setNickname("Joni");
		
		dao.updateContact(realContact);
		
		assertEquals("Joni", dao.getContact(realContact.getPublicKey()).get().getNickname());
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
	}
}
