package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Represents some part of the underlying {@link Storage} implementation.
 *
 * @param <T> The {@link Storage} that implements the functionality.
 */
public abstract class AbstractStorageAdapter<T extends Storage> {
	@Getter(AccessLevel.PROTECTED) private T storage;

	public AbstractStorageAdapter(T storage) {
		this.storage = storage;
	}
}
