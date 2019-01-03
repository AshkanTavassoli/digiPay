package ashkan.digiPay.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Accountant extends ConnectionManager{
	private static HashMap<Integer, Integer> localShoppingCart = new HashMap<>();
	private static int fakeUserID = 1;
	public static void addItem(int id,int count) {
		if(localShoppingCart.containsKey(id)){
			localShoppingCart.put(id, localShoppingCart.get(id) + count);
		}else {
			localShoppingCart.put(id,count);
		}
	}
	
	public static Integer getCount(int id) {
		if(localShoppingCart.containsKey(id)) {
			return localShoppingCart.get(id);
		}else {
			return 0;
		}
	}
	
	public static void checkout() {
		try {
			Statement st = conn.createStatement();
			for(int i:localShoppingCart.keySet()) {
	            st.execute("INSERT INTO shoppingCart (userID,productfk,count)"
	            		+ " VALUES ("+fakeUserID+","+i+","+localShoppingCart.get(i)+")");
			}
			st.close();
		} catch (SQLException e) {
			System.out.println("Could not save shopping cart into database!");
		}
	}
	
	public static void createInvoice() {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT shoppingCart.count count, products.name name, products.price price"
					+ " FROM shoppingCart"
					+ " LEFT JOIN products ON shoppingCart.productfk = products.id"
					+ " WHERE userID = "+fakeUserID);
			st.close();
			while(rs.next()) {
				System.out.println(rs.getInt("count"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error loading shopping cart data!");
		}
	}
}
