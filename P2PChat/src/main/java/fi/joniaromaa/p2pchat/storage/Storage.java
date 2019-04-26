package fi.joniaromaa.p2pchat.storage;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;
import fi.joniaromaa.p2pchat.storage.dao.IdentityDao;

/**
 * Represents persistent data storage.
 * 
 * The way the data is saved differences from the underlying implementation.
 * For example, this could be file system based system or it could use databases.
 */
public interface Storage {
	/**
	 * Returns implementation specific {@link IdentityDao}.
	 * 
	 * @return Returns implementation specific {@link IdentityDao}. This should never be null and suck cases should be considered as faulty implementation.
	 */
	public @Nonnull IdentityDao getIdentityDao();
	
	/**
	 * Returns implementation specific {@link ContactsDao}.
	 * 
	 * @return Returns implementation specific {@link ContactsDao}. This should never be null and suck cases should be considered as faulty implementation.
	 */
	public @Nonnull ContactsDao getContactsDao();
	
	/**
	 * Returns implementation specific {@link ConversationDao}.
	 * 
	 * @return Returns implementation specific {@link ConversationDao}. This should never be null and suck cases should be considered as faulty implementation.
	 */
	public @Nonnull ConversationDao getConversationDao();

	/***
	 * Closes all resources that the implementation could be using and all further access leads to undocumented behavior.
	 * .
	 * @throws Exception If process was not successfully, different implementations may return more specific type of {@link Exception}.
	 */
	public void close() throws Exception;
}
