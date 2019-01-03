package ashkan.digiPay.task;


import java.sql.SQLException;
import java.sql.Statement;

public class InitialData {
	public static void createDatabase() {
        try {
        	Statement st = ConnectionManager.conn.createStatement();
        	
        	//creating Product table
            st.execute("CREATE TABLE products ("
            		+ "id INT NOT NULL AUTO_INCREMENT,"
            		+ "name VARCHAR(255),"
            		+ "price DOUBLE)");
            
            //change this part to manipulate available products
            st.execute("INSERT INTO products (name,price) VALUES ('T-Shirt',40)");
            st.execute("INSERT INTO products (name,price) VALUES ('Jacket',110)");
            st.execute("INSERT INTO products (name,price) VALUES ('Jogger Pants',80)");
            
            
        }catch(SQLException e) {
        	System.out.println("There was a problem inserting initial data!");
        	System.exit(1);
        }

	}
}
