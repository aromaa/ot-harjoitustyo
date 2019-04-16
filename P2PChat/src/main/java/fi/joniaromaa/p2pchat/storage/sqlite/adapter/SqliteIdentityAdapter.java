package fi.joniaromaa.p2pchat.storage.sqlite.adapter;

import java.security.spec.InvalidKeySpecException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import fi.joniaromaa.p2pchat.identity.MyIdentity;
import fi.joniaromaa.p2pchat.storage.adapter.AbstractIdentityAdapter;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;
import fi.joniaromaa.p2pchat.utils.EncryptionUtils;

public class SqliteIdentityAdapter extends AbstractIdentityAdapter<SqliteStorage> {
	public SqliteIdentityAdapter(SqliteStorage storage) {
		super(storage);
	}

	@Override
	public boolean save(MyIdentity identity) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("INSERT INTO identity(id, private_key, public_key, nickname) VALUES(1, ?, ?, ?)")) {
			statement.setBytes(1, identity.getKeyPair().getPrivate().getEncoded());
			statement.setBytes(2, identity.getKeyPair().getPublic().getEncoded());
			statement.setString(3, identity.getNickname());
			statement.execute();

			return true;
		} catch (SQLException e) {
			// Log?
		}

		return false;
	}

	@Override
	public Optional<MyIdentity> getIdentity() {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("SELECT private_key, public_key, nickname FROM identity WHERE id = 1 LIMIT 1")) {
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					byte[] privateKey = resultSet.getBytes("private_key");
					byte[] publicKey = resultSet.getBytes("public_key");
					
					String nickname = resultSet.getString("nickname");

					return Optional.of(new MyIdentity(EncryptionUtils.getKeyPair(privateKey, publicKey), nickname));
				}
			} catch (InvalidKeySpecException e) {
				return Optional.empty();
			}
		} catch (SQLException e) {
			// Log?
		}

		return Optional.empty();
	}
}
