package fi.joniaromaa.p2pchat.storage.dao;

import java.util.Optional;

import javax.annotation.Nonnull;

import fi.joniaromaa.p2pchat.identity.MyIdentity;

public interface IdentityDao {
	public boolean save(@Nonnull MyIdentity identity);

	public Optional<MyIdentity> getIdentity();
}
