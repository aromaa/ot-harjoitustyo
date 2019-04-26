package fi.joniaromaa.p2pchat.storage.dao;

import java.security.PublicKey;
import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;

/**
 * Represents persistent data storage that deals with {@link ContactIdentity}.
 *
 * The way the data is saved differences from the underlying implementation.
 * For example, this could be file system based system or it could use databases.
 */
public interface ContactsDao {
	/**
	 * Adds the {@link PublicKey} as contact with the specified nickname.
	 * 
	 * @param publicKey The contact's public key, does not accept null.
	 * @param nickname The contact's nickname, does not accept null.
	 * 
	 * @return Returns the newly created {@link ContactIdentity} in case when new contact was created. If no contact was created then {@link java.util.Optional#empty()} is returned, this could be due to already existing contact with the same {@link PublicKey}.
	 */
	public Optional<ContactIdentity> addContact(@Nonnull PublicKey publicKey, @Nonnull String nickname);
	
	/**
	 * Updates the {@link ContactIdentity} values to the underlying storage.
	 * 
	 * @param contact The contact to be updated, does not accept null.
	 */
	public void updateContact(@Nonnull ContactIdentity contact);
	
	/**
	 * Gets {@link ContactIdentity} that represents the {@link PublicKey}.
	 * 
	 * @param publicKey The public key that the contact belongs to, does not accept null.
	 * 
	 * @return Returns the {@link ContactIdentity} that represents the {@link PublicKey}, or in case none, returns {@link java.util.Optional#empty()}.
	 */
	public Optional<ContactIdentity> getContact(@Nonnull PublicKey publicKey);
}
