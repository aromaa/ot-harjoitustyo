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
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("INSERT INTO identity(id, nickname, private_key, public_key) VALUES(1, ?, ?, ?)")) {
			statement.setString(1, identity.getNickname());
			statement.setBytes(2, identity.getKeyPair().getPrivate().getEncoded());
			statement.setBytes(3, identity.getKeyPair().getPublic().getEncoded());
			statement.execute();

			return true;
		} catch (SQLException e) {
			// Log?
		}

		return false;
	}

	@Override
	public Optional<MyIdentity> getIdentity() {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("SELECT nickname, private_key, public_key FROM identity WHERE id = 1 LIMIT 1")) {
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String nickname = resultSet.getString("nickname");

					byte[] privateKey = resultSet.getBytes("private_key");
					byte[] publicKey = resultSet.getBytes("public_key");

					return Optional.of(new MyIdentity(nickname, EncryptionUtils.getKeyPair(privateKey, publicKey)));
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
