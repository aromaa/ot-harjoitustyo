package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;

/**
 * Represents {@link AbstractStorageAdapter} that implements the {@link ConversationDao}.
 *
 * @param <T> {@link Storage} that implements the functionality.
 */
public abstract class AbstractConversationAdapter<T extends Storage> extends AbstractStorageAdapter<T> implements ConversationDao {

	public AbstractConversationAdapter(T storage) {
		super(storage);
	}

}
