package fi.joniaromaa.p2pchat.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import lombok.Getter;

public class EncryptionUtils
{
	private static final int KEYSIZE = 2048;
	
	private static KeyPairGenerator generator;
	private static KeyFactory factory;
	
	@Getter private static boolean initialized;
	
	static
	{
		try
		{
			EncryptionUtils.generator = KeyPairGenerator.getInstance("RSA");
			EncryptionUtils.generator.initialize(EncryptionUtils.KEYSIZE);
			
			EncryptionUtils.factory = KeyFactory.getInstance("RSA");
			
			EncryptionUtils.initialized = true;
		}
		catch (Throwable ignore)
		{
		}
	}
	
	public static KeyPair generateKeyPair()
	{
		return EncryptionUtils.generator.generateKeyPair();
	}
	
	public static PrivateKey getPrivateKey(byte[] bytes) throws InvalidKeySpecException
	{
		return EncryptionUtils.factory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}
	
	public static PublicKey getPublicKey(byte[] bytes) throws InvalidKeySpecException
	{
		return EncryptionUtils.factory.generatePublic(new X509EncodedKeySpec(bytes));
	}
	
	public static KeyPair getKeyPair(byte[] privateBytes, byte[] publicBytes) throws InvalidKeySpecException
	{
		return new KeyPair(EncryptionUtils.getPublicKey(publicBytes), EncryptionUtils.getPrivateKey(privateBytes));
	}
}
