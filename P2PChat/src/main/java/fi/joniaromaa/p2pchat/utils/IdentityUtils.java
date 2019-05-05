package fi.joniaromaa.p2pchat.utils;

import java.security.KeyPair;

import fi.joniaromaa.p2pchat.identity.MyIdentity;

/**
 * Helper class to deal with identities.
 */
public class IdentityUtils {
	/**
	 * Ensures that the nickname is not null and has length between 1 to 32.
	 * 
	 * @param nickname The nickname to check.
	 * 
	 * @return If the nickname was good.
	 */
	public static boolean isValidNickname(String nickname) {
		if (nickname == null) {
			return false;
		}
		
		if (nickname.length() <= 0) {
			return false;
		}
		
		if (nickname.length() > 32) {
			return false;
		}
		
		return true;
	}

	/**
	 * Generates new {@link MyIdentity} with the specific nickname.
	 * 
	 * @param nickname The nickname to use.
	 * 
	 * @return Returns newly generated {@link MyIdentity} with randomly generated {@link KeyPair}.
	 */
	public static MyIdentity generateMyIdentity(String nickname) {
		return new MyIdentity(EncryptionUtils.generateKeyPair(), nickname);
	}
}
