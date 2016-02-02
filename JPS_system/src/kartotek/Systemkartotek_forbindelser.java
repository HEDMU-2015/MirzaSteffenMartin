package kartotek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Systemkartotek_forbindelser {
	public static void main(String[] args) throws SQLException {
		
		
		
		
		
	}
	public Connection connectTilDB() throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/mydatabase", "SA", "");
		return connection;
		
	}
}
