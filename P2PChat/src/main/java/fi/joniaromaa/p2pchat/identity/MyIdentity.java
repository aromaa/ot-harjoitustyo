package fi.joniaromaa.p2pchat.identity;

import java.security.KeyPair;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import lombok.Getter;
import lombok.Setter;

public class MyIdentity implements Identity, Destroyable {
	@Getter private final KeyPair keyPair;
	
	@Getter @Setter private String nickname;

	public MyIdentity(KeyPair keyPair, String nickname) {
		this.keyPair = keyPair;
		
		this.nickname = nickname;
	}

	public static MyIdentity generate(String nickname) {
		return new MyIdentity(EncryptionUtils.generateKeyPair(), nickname);
	}

	public void destroy() throws DestroyFailedException {
		this.keyPair.getPrivate().destroy();
	}

	public boolean isDestroyed() {
		return this.keyPair.getPrivate().isDestroyed();
	}

	public byte[] getPublicKeyBytes() {
		return this.keyPair.getPublic().getEncoded();
	}

	@Override
	public String getDisplayName() {
		return this.nickname;
	}
}
