package fi.joniaromaa.p2pchat.storage.dao;

import java.security.PublicKey;
import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;

public interface ContactsDao {
	public Optional<ContactIdentity> addContact(@Nonnull PublicKey publicKey, @Nonnull String nickname);
	
	public void updateContact(@Nonnull ContactIdentity contact);
	
	public Optional<ContactIdentity> getContact(@Nonnull PublicKey publicKey);
}
