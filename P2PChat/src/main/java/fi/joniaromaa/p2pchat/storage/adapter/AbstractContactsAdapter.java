package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.dao.ContactsDao;

/**
 * Represents {@link AbstractStorageAdapter} that implements the {@link ContactsDao}.
 *
 * @param <T> {@link Storage} that implements the functionality.
 */
public abstract class AbstractContactsAdapter<T extends Storage> extends AbstractStorageAdapter<T> implements ContactsDao {
	public AbstractContactsAdapter(T storage) {
		super(storage);
	}

}
