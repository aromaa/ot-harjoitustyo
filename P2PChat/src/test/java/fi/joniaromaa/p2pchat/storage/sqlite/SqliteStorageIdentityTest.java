package fi.joniaromaa.p2pchat.storage.sqlite;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class SqliteStorageIdentityTest
{
	private static final File TEST_FOLDER = new File("automated-tests-temp");
	
	private SqliteStorage storage;
	
	@Before
	public void setup() throws ClassNotFoundException, SQLException
	{
		SqliteStorageIdentityTest.TEST_FOLDER.mkdirs();
		
		this.storage = new SqliteStorage(new File(SqliteStorageIdentityTest.TEST_FOLDER, "test.db"));
	}
	
	@Test
	public void canIdentity() throws InvalidKeySpecException
	{
		KeyPair keyPair = EncryptionUtils.generateKeyPair();
		
		MyIdentity identity = new MyIdentity("THIS-IS-TEST", keyPair);
		
		this.storage.getIdentityDao().save(identity);
		
		MyIdentity saved = this.storage.getIdentityDao().getIdentity().get();
		
		assertTrue(identity.getNickname().equals(saved.getNickname()));
		
		//Not sure whats the most ideal way to check for quality for these
		assertTrue(Arrays.equals(identity.getKeyPair().getPrivate().getEncoded(), saved.getKeyPair().getPrivate().getEncoded()));
		assertTrue(Arrays.equals(identity.getKeyPair().getPublic().getEncoded(), saved.getKeyPair().getPublic().getEncoded()));
	}
	
	@After
	public void cleanup() throws Exception
	{
		this.storage.close();
		
		FileUtils.deleteDirectory(SqliteStorageIdentityTest.TEST_FOLDER);
	}
}
