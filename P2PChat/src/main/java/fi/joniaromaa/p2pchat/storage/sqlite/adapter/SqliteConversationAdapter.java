package fi.joniaromaa.p2pchat.storage.sqlite.adapter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import fi.joniaromaa.p2pchat.chat.conversation.ChatConversation;
import fi.joniaromaa.p2pchat.identity.ContactIdentity;
import fi.joniaromaa.p2pchat.storage.adapter.AbstractConversationAdapter;
import fi.joniaromaa.p2pchat.storage.sqlite.SqliteStorage;

public class SqliteConversationAdapter extends AbstractConversationAdapter<SqliteStorage> {

	public SqliteConversationAdapter(SqliteStorage storage) {
		super(storage);
	}

	@Override
	public Optional<ChatConversation> create(ContactIdentity contact) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("INSERT OR IGNORE INTO conversations(contact_id) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, contact.getId());

			if (statement.executeUpdate() > 0) {
				try (ResultSet result = statement.getGeneratedKeys()) {
					if (result.next()) {
						int id = result.getInt(1);
	
						return Optional.of(new ChatConversation(id, contact));
					}
				}
			}
		} catch (SQLException e) {
			// Log?
		}

		return Optional.empty();
	}

	@Override
	public Optional<ChatConversation> get(ContactIdentity contact) {
		try (PreparedStatement statement = this.getStorage().getConnection().prepareStatement("SELECT id FROM conversations WHERE contact_id = ? LIMIT 1")) {
			statement.setInt(1, contact.getId());
			
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					int id = result.getInt("id");
					
					return Optional.of(new ChatConversation(id, contact));
				}
			}
		} catch (SQLException e) {
			//Log
		}
		
		return Optional.empty();
	}

}
