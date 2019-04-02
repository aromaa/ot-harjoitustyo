package fi.joniaromaa.p2pchat.storage.adapter;

import fi.joniaromaa.p2pchat.storage.Storage;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractStorageAdapter<T extends Storage>
{
	@Getter(AccessLevel.PROTECTED) private T storage;
	
	public AbstractStorageAdapter(T storage)
	{
		this.storage = storage;
	}
}
