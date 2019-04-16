package fi.joniaromaa.p2pchat.utils;

public class IdentityUtils {
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
}
