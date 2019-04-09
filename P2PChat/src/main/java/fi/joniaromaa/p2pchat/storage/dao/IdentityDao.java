package fi.joniaromaa.p2pchat.storage.dao;

import java.util.Optional;

import fi.joniaromaa.p2pchat.identity.MyIdentity;

public interface IdentityDao {
	public boolean save(MyIdentity identity);

	public Optional<MyIdentity> getIdentity();
}
