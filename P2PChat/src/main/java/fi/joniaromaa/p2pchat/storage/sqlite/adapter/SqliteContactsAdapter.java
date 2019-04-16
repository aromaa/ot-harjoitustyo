package fi.joniaromaa.p2pchat.storage.sqlite.adapter;

import java.security.PublicKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.adapter.AbstractContactsAdapter;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;

public class SqliteContactsAdapter extends AbstractContactsAdapter<SqliteStorage> {
	public SqliteContactsAdapter(SqliteStorage storage) {
		super(storage);
	}

	@Override
	public Optional<ContactIdentity> addContact(PublicKey publicKey, String nickname) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("INSERT INTO contacts(public_key, nickname) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setBytes(1, publicKey.getEncoded());
			statement.setString(2, nickname);
			statement.execute();

			try (ResultSet result = statement.getGeneratedKeys()) {
				if (result.next()) {
					int id = result.getInt(1);

					return Optional.of(new ContactIdentity(id, publicKey, nickname));
				}
			}
		} catch (SQLException e) {
			// Log?
		}
		
		return Optional.empty();
	}

	@Override
	public void updateContact(ContactIdentity contact) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("UPDATE contacts SET nickname = ?, contact_name = ? WHERE id = ? LIMIT 1 ")) {
			statement.setString(1, contact.getNickname());
			statement.setString(2, contact.getContactName().orElse(""));
			statement.setInt(3, contact.getId());
			statement.execute();
		} catch (SQLException e) {
			// Log?
		}
	}

	@Override
	public Optional<ContactIdentity> getContact(PublicKey publicKey) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("SELECT id, nickname, contact_name FROM contacts WHERE public_key = ? LIMIT 1")) {
			statement.setBytes(1, publicKey.getEncoded());
			
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					int id = result.getInt("id");
					
					String nickname = result.getString("nickname");
					String contactName = result.getString("contact_name");
					
					if (contactName.length() <= 0) {
						contactName = null;
					}
					
					return Optional.of(new ContactIdentity(id, publicKey, nickname, contactName));
				}
			}
		} catch (SQLException e) {
			// Log?
		}
		
		return Optional.empty();
	}
}
