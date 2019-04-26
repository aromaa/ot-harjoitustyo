package fi.joniaromaa.p2pchat.storage.dao;

import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.identity.MyIdentity;

/**
 * Represents persistent data storage that deals with {@link MyIdentity}.
 *
 * The way the data is saved differences from the underlying implementation.
 * For example, this could be file system based system or it could use databases.
 */
public interface IdentityDao {
	/**
	 * Saves the {@link MyIdentity} as primary user.
	 * 
	 * @param identity The identity that should be saved, does not accept null.
	 * @return Whatever the save was successfully.
	 */
	public boolean save(@Nonnull MyIdentity identity);

	/**
	 * Returns the primary user.
	 * 
	 * @return Returns the primary user, or in case none, returns {@link java.util.Optional#empty()}.
	 */
	public Optional<MyIdentity> getIdentity();
}
