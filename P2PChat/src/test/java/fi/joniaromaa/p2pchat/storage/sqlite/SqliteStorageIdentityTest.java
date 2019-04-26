package fi.joniaromaa.p2pchat.storage.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.storage.dao.IdentityDao;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class SqliteStorageIdentityTest
{
	private SqliteStorage storage;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		this.storage = new SqliteStorage(":memory:");
	}
	
	@Test
	public void canIdentity() throws InvalidKeySpecException
	{
		IdentityDao dao = this.storage.getIdentityDao();
		
		//There should be no default value
		assertFalse(dao.getIdentity().isPresent());
		
		KeyPair keyPair = EncryptionUtils.generateKeyPair();
		
		MyIdentity identity = new MyIdentity(keyPair, "Joni");
		
		assertTrue(this.storage.getIdentityDao().save(identity));
		
		MyIdentity saved = this.storage.getIdentityDao().getIdentity().get();
		
		assertEquals(identity.getNickname(), saved.getNickname());
		
		//Not sure whats the most ideal way to check for quality for these
		assertTrue(Arrays.equals(identity.getKeyPair().getPrivate().getEncoded(), saved.getKeyPair().getPrivate().getEncoded()));
		assertTrue(Arrays.equals(identity.getKeyPair().getPublic().getEncoded(), saved.getKeyPair().getPublic().getEncoded()));
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
	}
}
