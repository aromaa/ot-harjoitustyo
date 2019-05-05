package fi.joniaromaa.p2pchat.storage.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.sqlite.adapter.SqliteContactsAdapter;
import fi.joniaromaa.p2pchat.storage.sqlite.adapter.SqliteConversationAdapter;
import fi.joniaromaa.p2pchat.storage.sqlite.adapter.SqliteIdentityAdapter;
import lombok.Getter;

/**
 * Implementation of {@link Storage} using SQLite.
 */
public class SqliteStorage implements Storage {
	@Getter private SqliteIdentityAdapter identityDao;
	@Getter private SqliteContactsAdapter contactsDao;
	@Getter private SqliteConversationAdapter conversationDao;

	@Getter private Connection connection;
	
	public SqliteStorage() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		this.identityDao = new SqliteIdentityAdapter(this);
		this.contactsDao = new SqliteContactsAdapter(this);
		this.conversationDao = new SqliteConversationAdapter(this);
	}

	public SqliteStorage(File file) throws ClassNotFoundException, SQLException {
		this();

		this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());

		this.setup();
	}
	
	public SqliteStorage(String connectionString) throws ClassNotFoundException, SQLException {
		this();
		
		this.connection = DriverManager.getConnection("jdbc:sqlite:" + connectionString);

		this.setup();
	}

	private void setup() throws SQLException {
		try (Statement statement = this.connection.createStatement()) {
			statement.execute("CREATE TABLE IF NOT EXISTS `contacts` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `public_key` blob NOT NULL, `nickname` TEXT(32) NOT NULL, `contact_name` TEXT(32) NOT NULL DEFAULT '', CONSTRAINT `public_key` UNIQUE (`public_key` COLLATE BINARY ASC));");
			statement.execute("CREATE TABLE IF NOT EXISTS `conversations` (`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `contact_id` integer NOT NULL, CONSTRAINT `contact_id` UNIQUE (`contact_id` ASC));");
			statement.execute("CREATE TABLE IF NOT EXISTS `identity` (`id` INTEGER NOT NULL, `private_key` blob NOT NULL, `public_key` blob NOT NULL, `nickname` TEXT(32) NOT NULL, PRIMARY KEY (`id`) ON CONFLICT REPLACE);");
		}
	}

	public void close() throws Exception {
		this.connection.close();
	}
}
