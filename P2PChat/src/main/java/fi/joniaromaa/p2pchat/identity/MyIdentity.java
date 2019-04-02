package fi.joniaromaa.p2pchat.identity;

import java.security.KeyPair;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

import fi.joniaromaa.p2pchat.utils.EncryptionUtils;
import lombok.Getter;
import lombok.Setter;

public class MyIdentity implements Destroyable
{
	@Getter @Setter private String nickname;
	
	@Getter private final KeyPair keyPair;
	
	public MyIdentity(String nickname, KeyPair keyPair)
	{
		this.nickname = nickname;
		
		this.keyPair = keyPair;
	}
	
	public static MyIdentity generate(String nickname)
	{
		return new MyIdentity(nickname, EncryptionUtils.generateKeyPair());
	}
	
	public void destroy() throws DestroyFailedException
	{
		this.keyPair.getPrivate().destroy();
    }
	
	public boolean isDestroyed()
	{
        return this.keyPair.getPrivate().isDestroyed();
    }
}
