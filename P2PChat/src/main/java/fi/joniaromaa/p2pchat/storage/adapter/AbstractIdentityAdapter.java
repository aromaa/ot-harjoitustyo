package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.dao.IdentityDao;

/**
 * Represents {@link AbstractStorageAdapter} that implements the {@link IdentityDao}.
 *
 * @param <T> {@link Storage} that implements the functionality.
 */
public abstract class AbstractIdentityAdapter<T extends Storage> extends AbstractStorageAdapter<T> implements IdentityDao {
	public AbstractIdentityAdapter(T storage) {
		super(storage);
	}

}
