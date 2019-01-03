package ashkan.digiPay.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//different output types
enum printType{
	ProductList
}
public class ConnectionManager {
	//using in memory database side effects
    static Connection conn = null;

	public static void connect() {
        String url = "jdbc:h2:mem:";
        try {
        	conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
        	System.out.println("There was a problem connecting to h2 memory database!");
        	System.exit(1);
        }
	}

 
}
