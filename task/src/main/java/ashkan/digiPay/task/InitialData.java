package ashkan.digiPay.task;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialData {
	public static void createDatabase(Connection conn) {
        try {
        	Statement st = conn.createStatement();
        	
        	//creating Product table
            st.execute("CREATE TABLE products ("
            		+ "id INT NOT NULL AUTO_INCREMENT,"
            		+ "name VARCHAR(255),"
            		+ "price DOUBLE,"
            		+ "PRIMARY KEY (id)"
            		+ ")");
          //creating shopping cart table
            st.execute("CREATE TABLE shoppingCart ("
            		+ "userID INT NOT NULL,"
            		+ "productfk INT NOT NULL,"
            		+ "count INT NOT NULL,"
            		+ "FOREIGN KEY (productfk) REFERENCES products(id),"
            		+ "CHECK (count > 0)"
            		+ ")");
            
            //change this part to manipulate available products
            st.execute("INSERT INTO products (name,price) VALUES ('T-Shirt',40)");
            st.execute("INSERT INTO products (name,price) VALUES ('Jacket',110)");
            st.execute("INSERT INTO products (name,price) VALUES ('Jogger Pants',80)");
    		st.close();
            
        }catch(SQLException e) {
        	System.out.println("There was a problem inserting initial data!");
        	System.exit(1);
        }

	}
}
