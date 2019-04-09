package fi.joniaromaa.p2pchat.identity;

import java.security.PublicKey;
import java.util.Optional;

import lombok.Getter;

public class ContactIdentity {
	@Getter private String nickname;
	private String contactNickname;

	@Getter private final PublicKey publicKey;

	public ContactIdentity(String nickname, PublicKey publicKey) {
		this(nickname, null, publicKey);
	}

	public ContactIdentity(String nickname, String contactNickname, PublicKey publicKey) {
		this.nickname = nickname;
		this.contactNickname = contactNickname;

		this.publicKey = publicKey;
	}

	public Optional<String> getContactNickname() {
		return Optional.ofNullable(this.contactNickname);
	}
}
