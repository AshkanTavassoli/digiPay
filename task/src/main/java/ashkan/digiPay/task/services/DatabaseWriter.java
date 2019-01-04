package ashkan.digiPay.task.services;

import java.sql.SQLException;
import java.sql.Statement;

import ashkan.digiPay.task.utilities.ConnectionManager;

public class DatabaseWriter extends ConnectionManager{
	public void addInvoiceLine(int userID, int productID, int count) {
		try {
			Statement st = conn.createStatement();
            st.execute("INSERT INTO shoppingCart (userID,productfk,count)"
            		+ " VALUES ("+userID+","+productID+","+count+")");
			st.close();
		} catch (SQLException e) {
			System.out.println("Could not save shopping cart into database!");
		}

	}
}
