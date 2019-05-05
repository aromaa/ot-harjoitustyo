package fi.joniaromaa.p2pchat.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;

/**
 * Helper class to deal with strong encryption.
 */
public class EncryptionUtils {
	/**
	 * The key size used to generate {@link KeyPair}.
	 */
	public static final int KEYSIZE = 2048;

	private static KeyPairGenerator generator;
	private static KeyFactory factory;

	@Getter
	private static boolean initialized;

	static {
		try {
			EncryptionUtils.generator = KeyPairGenerator.getInstance("RSA");
			EncryptionUtils.generator.initialize(EncryptionUtils.KEYSIZE);

			EncryptionUtils.factory = KeyFactory.getInstance("RSA");

			EncryptionUtils.initialized = true;
		} catch (Throwable ignore) {
		}
	}

	/**
	 * Generates RSA {@link KeyPair} with {@link EncryptionUtils#KEYSIZE}.
	 * 
	 * @return The generated {@link KeyPair}.
	 */
	public static KeyPair generateKeyPair() {
		return EncryptionUtils.generator.generateKeyPair();
	}

	/**
	 * Converts byte array to {@link PrivateKey}.
	 * 
	 * @param bytes The byte array which contains the private key.
	 * 
	 * @return The {@link PrivateKey} that was created.
	 * 
	 * @throws InvalidKeySpecException if the byte array was invalid.
	 */
	public static PrivateKey getPrivateKey(byte[] bytes) throws InvalidKeySpecException {
		return EncryptionUtils.factory.generatePrivate(new PKCS8EncodedKeySpec(bytes));
	}

	/**
	 * Converts byte array to {@link PublicKey}.
	 * 
	 * @param bytes The byte array which contains the public key.
	 * 
	 * @return The {@link PublicKey} that was created.
	 * 
	 * @throws InvalidKeySpecException if the byte array was invalid.
	 */
	public static PublicKey getPublicKey(byte[] bytes) throws InvalidKeySpecException {
		return EncryptionUtils.factory.generatePublic(new X509EncodedKeySpec(bytes));
	}

	/**
	 * Converts byte arrays to {@link KeyPair}.
	 * 
	 * @param privateBytes The byte array which contains the private key.
	 * @param publicBytes The byte array which contains the public key.
	 * 
	 * @return The {@link KeyPair} that was created.
	 * 
	 * @throws InvalidKeySpecException if the byte arrays were invalid.
	 */
	public static KeyPair getKeyPair(byte[] privateBytes, byte[] publicBytes) throws InvalidKeySpecException {
		return new KeyPair(EncryptionUtils.getPublicKey(publicBytes), EncryptionUtils.getPrivateKey(privateBytes));
	}

	/**
	 * Signs the challenge with {@link PrivateKey} using SHA 256 with RSA.
	 * 
	 * @param privateKey The private key to sign the challenge with.
	 * @param challenge The challenge to sign.
	 * 
	 * @return The signed challenge.
	 * 
	 * @throws InvalidKeyException If the {@link PrivateKey} was invalid.
	 * @throws NoSuchAlgorithmException If the platform does not support this type of algorithm.
	 * @throws SignatureException If the signature could not been updated.
	 */
	public static byte[] getSignedChallange(PrivateKey privateKey, byte[] challenge)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Signature signature = Signature.getInstance("SHA256withRSA");

		signature.initSign(privateKey);
		signature.update(challenge);

		return signature.sign();
	}

	/**
	 * Verifies that the signed challenge is generated with {@link PrivateKey} associated with the {@link PublicKey}.
	 * 
	 * @param publicKey The {@link PublicKey} associated with {@link PrivateKey}.
	 * @param challenge Challenge to sign.
	 * @param signedChallenge The challenge that was signed.
	 * 
	 * @return Whatever the signing is genius.
	 * 
	 * @throws InvalidKeyException If the {@link PrivateKey} was invalid.
	 * @throws NoSuchAlgorithmException If the platform does not support this type of algorithm.
	 * @throws SignatureException If the signature could not been updated.
	 */
	public static boolean verifyChallange(PublicKey publicKey, byte[] challenge, byte[] signedChallenge)
			throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
		Signature signature = Signature.getInstance("SHA256withRSA");

		signature.initVerify(publicKey);
		signature.update(challenge);

		return signature.verify(signedChallenge);
	}

	/**
	 * Generates selected amount of bytes using secure algorithm.
	 * 
	 * <b>FALLBACKS TO UNSECURE RANDOM WHEN UNAVAIBLE TO USE SECURE ONE</b>.
	 * 
	 * @param amount The amount of bytes to generate.
	 * 
	 * @return Returns the byte array which has the amount of selected bytes.
	 */
	public static byte[] requestRandomBytes(int amount) {
		Random random;

		try {
			random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			// TODO: Hm...

			random = ThreadLocalRandom.current();
		}

		byte[] challange = new byte[amount];

		random.nextBytes(challange);

		return challange;
	}
}
