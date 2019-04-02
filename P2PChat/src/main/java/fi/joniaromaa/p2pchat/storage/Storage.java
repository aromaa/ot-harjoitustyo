package fi.joniaromaa.p2pchat.storage;

import fi.joniaromaa.p2pchat.storage.dao.IdentityDao;

public interface Storage
{
	public IdentityDao getIdentityDao();
	
	public void close() throws Exception;
}
