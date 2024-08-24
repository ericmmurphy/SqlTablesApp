package ericmm.maven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection connection;
	private Statement statement;
	
	Database(String db) {
		try {
        	this.connection = DriverManager.getConnection(db);
        	this.statement = connection.createStatement();
		} catch (SQLException err) {
			// if the error message is "out of memory",
			// it probably means no database file is found
			err.printStackTrace(System.err);
		}
	}
	
	private static String quote(String str) { return "\"" + str + "\""; }
		
	// ResultSet executeQuery(String sql) throws SQLException;
	// int executeUpdate(String sql) throws SQLException;
	// boolean execute(String sql) throws SQLException;
	
	public void createTestTables() {
		try {
			this.statement.execute("CREATE TABLE decks (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL);");
			this.statement.execute("CREATE TABLE flashcards (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, description TEXT NOT NULL);");
		} catch (SQLException e) {
			e.printStackTrace(System.err);
		}
	}
	
	public ResultSet getTableNames() throws SQLException {
		return this.statement.executeQuery("SELECT name FROM sqlite_master WHERE type = \"table\" and name <> \"sqlite_sequence\";");
	}
	
	public ResultSet getHeaderNames(String tableName) throws SQLException {
		return this.statement.executeQuery("PRAGMA table_info(" + quote(tableName) + ");");
	}
	
	public boolean tableExists(String tableName) throws SQLException {
		String sql = "SELECT name FROM sqlite_master WHERE type = \"table\" and name == " + quote(tableName) + ";";
		try {
			ResultSet results = this.statement.executeQuery(sql);
			while(results.next()) {
				if (results.getString("name").equals(tableName)) {
					return true;
				} 
			}
		} catch (SQLException e) {
			throw e;
		}
		return false;
	}
	
	public ResultSet selectTable(String tableName) throws SQLException {
		String sql = "SELECT * FROM " + tableName + ";";
		return this.statement.executeQuery(sql);
	}
	
	public void createTable(String tableName) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT);";
		this.statement.execute(sql);
	}
	
	public void renameTable(String prevTableName, String newTableName) throws SQLException {
		String sql = "ALTER TABLE " + prevTableName + " RENAME TO " + newTableName + ";";
		this.statement.execute(sql);
	}
	
	public void dropTable(String tableName) throws SQLException {
		String sql = "DROP TABLE " + tableName + ";";
		this.statement.execute(sql);
	}
	
	/*
	ResultSet getDecks() throws SQLException {
		return this.statement.executeQuery("SELECT * FROM decks");
	}
	
	boolean deckExists(String deckName) throws SQLException {
		String checkSql = "SELECT * FROM decks WHERE name = " + quote(deckName) + ";";
		return this.statement.execute(checkSql);		
	}
	
	void addDeck(String deckName) throws SQLException {
		String sql = "INSERT INTO decks (name) VALUES (" + quote(deckName) + ");";
		this.statement.execute(sql);
	}
	
	void updateDeck(String prevDeckName, String newDeckName) throws SQLException {
		if (!this.deckExists(prevDeckName)) return;
		
		String sql = "UPDATE decks SET name = " + quote(newDeckName) + "WHERE name = " + quote(prevDeckName) + ";";
		this.statement.execute(sql);
	}
	
	void deleteDeck(String deckName) throws SQLException {
		if (!this.deckExists(deckName)) return;
		
		String deleteSql = "DELETE FROM decks WHERE name = " + quote(deckName) + ";";
		this.statement.execute(deleteSql);
	}
	*/
}
