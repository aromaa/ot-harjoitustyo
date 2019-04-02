package fi.joniaromaa.p2pchat.storage.sqlite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import fi.joniaromaa.p2pchat.storage.Storage;
import fi.joniaromaa.p2pchat.storage.sqlite.adapter.SqliteIdentityAdapter;
import lombok.Getter;

public class SqliteStorage implements Storage
{
	@Getter private SqliteIdentityAdapter identityDao;
	
	@Getter private Connection connection;
	
	public SqliteStorage(File file) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		this.identityDao = new SqliteIdentityAdapter(this);

		this.connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
		
		this.setup();
	}
	
	private void setup() throws SQLException
	{
		try(Statement statement = this.connection.createStatement())
		{
			statement.execute("CREATE TABLE IF NOT EXISTS `identity` (`id` INTEGER NOT NULL, `nickname` TEXT NOT NULL, `private_key` blob NOT NULL, `public_key` blob NOT NULL, PRIMARY KEY (`id`) ON CONFLICT REPLACE);");
		}
	}
	
	public void close() throws Exception
	{
		this.connection.close();
	}
}
