package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.dao.ConversationDao;

public abstract class AbstractConversationAdapter<T extends Storage> extends AbstractStorageAdapter<T> implements ConversationDao {

	public AbstractConversationAdapter(T storage) {
		super(storage);
	}

}
