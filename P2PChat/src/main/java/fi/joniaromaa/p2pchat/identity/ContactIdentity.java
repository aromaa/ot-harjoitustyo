package fi.joniaromaa.p2pchat.identity;

import java.security.PublicKey;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents {@link Identity} that can be considered as someone else that we know of.
 */
public class ContactIdentity implements Identity {
	@Getter private final int id;
	@Getter private final PublicKey publicKey;
	
	@Getter @Setter private String nickname;
	@Setter private String contactName;

	public ContactIdentity(int id, PublicKey publicKey, String nickname) {
		this(id, publicKey, nickname, null);
	}

	public ContactIdentity(int id, PublicKey publicKey, String nickname, String contactName) {
		this.id = id;
		this.publicKey = publicKey;
		
		this.nickname = nickname;
		this.contactName = contactName;
	}

	public Optional<String> getContactName() {
		return Optional.ofNullable(this.contactName);
	}

	@Override
	public String getDisplayName() {
		return this.getContactName().orElse(this.nickname);
	}
}
