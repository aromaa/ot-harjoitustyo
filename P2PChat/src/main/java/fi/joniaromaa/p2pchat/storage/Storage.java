package fi.joniaromaa.p2pchat.storage;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;
import fi.joniaromaa.p2pchat.storage.dao.IdentityDao;

public interface Storage {
	public @Nonnull IdentityDao getIdentityDao();
	public @Nonnull ContactsDao getContactsDao();
	public @Nonnull ConversationDao getConversationDao();

	public void close() throws Exception;
}
