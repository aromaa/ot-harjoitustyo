package fi.joniaromaa.p2pchat.identity;

import java.security.KeyPair;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents {@link Identity} that can be considered as someone who is the source of the {@link Identity}.
 */
public class MyIdentity implements Identity, Destroyable {
	@Getter private final KeyPair keyPair;
	
	@Getter @Setter private String nickname;

	public MyIdentity(KeyPair keyPair, String nickname) {
		this.keyPair = keyPair;
		
		this.nickname = nickname;
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
