package ashkan.digiPay.task.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionManager {
	//using in memory database side effects
    protected static Connection conn = null;

	public static Connection connect() {
        String url = "jdbc:h2:mem:";
        try {
        	conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
        	System.out.println("There was a problem connecting to h2 memory database!");
        	System.exit(1);
        }
        return conn;
	}

 
}
