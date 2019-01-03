package ashkan.digiPay.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ExtraCost;
import ashkan.digiPay.task.dataHolders.InvoiceLine;

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
			InvoiceLine invoiceLine;
			LinkedHashMap<Integer , DataStorage> invoice = new LinkedHashMap<>();
			int i = 0;
			while(rs.next()) {
				i++;
				invoiceLine = new InvoiceLine();
				invoiceLine.name = rs.getString("name");
				invoiceLine.count = rs.getInt("count");
				invoiceLine.price = rs.getDouble("price");
				addExtraCosts(invoiceLine);
				invoice.put(i,invoiceLine);
			}
			Printer.print(invoice, printType.Invoice);
			st.close();
		} catch (SQLException e) {
			System.out.println("Error loading shopping cart data!");
		}
	}
	
	private static void addExtraCosts(InvoiceLine input) {
		//there is no limit on how many extra costs can be added, zero extra cost is also acceptable
		// Fixed additional cost: Delivery for each item
		
		ExtraCost ec = new ExtraCost();
		ec.name = "Delivery";
		ec.fixCost = 5;
		ec.isPercentage = false;
		input.extraCosts.add(ec);
		
		// Percentage based additional cost: Delivery for each item
		ec = new ExtraCost();
		ec.name = "TAX";
		ec.percentage = 9;
		ec.isPercentage = true;
		input.extraCosts.add(ec);
		
	}
	
	public static double calculateExtraCosts(ExtraCost input , double cost, int count) {
		if(input.isPercentage) {
			return input.percentage * count * cost / 100;
		}else {
			return input.fixCost * count;
		}
	}
}
