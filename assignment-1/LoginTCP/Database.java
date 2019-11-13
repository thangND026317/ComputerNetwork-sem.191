package LoginTCP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {
	public static Connection connect() {
		Connection c = null;
		try {
			Class.forName("");
			String url = "";
			String username = "userList";
			String password = "1234@#56";
			
			c = DriverManager.getConnection(url, username, password);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}
		
		return c;
	}
	
	public static void disconect(Connection c) {
		if (c != null) {
			try {
				c.close();
			}
		
			catch (SQLException e) {
				System.out.println(e);
			}
		}
	}
}
