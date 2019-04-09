package fi.joniaromaa.p2pchat.utils;

import static org.junit.Assert.assertTrue;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import org.junit.Test;

public class EncryptUtilsTest
{
	@Test
	public void checkVerificationWorks() throws InvalidKeyException, SignatureException, NoSuchAlgorithmException
	{
		KeyPair keyPair = EncryptionUtils.generateKeyPair();
		
		byte[] challange = EncryptionUtils.requestRandomBytes(256);
		
		byte[] signed = EncryptionUtils.getSignedChallange(keyPair.getPrivate(), challange);
		
		assertTrue(EncryptionUtils.verifyChallange(keyPair.getPublic(), challange, signed));
	}
}
