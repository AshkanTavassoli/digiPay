package ashkan.digiPay.task.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.InvoiceLine;
import ashkan.digiPay.task.dataHolders.Product;
import ashkan.digiPay.task.utilities.ConnectionManager;

public class DatabaseReader extends ConnectionManager{
	public LinkedHashMap<Integer , DataStorage> productList() {
		LinkedHashMap<Integer , DataStorage> dataList = new LinkedHashMap<>();
		try {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM products");
		Product pd;
		while(rs.next()) {
			pd = new Product();
			pd.ID = rs.getInt("id");
			pd.Name = rs.getString("name");
			pd.Price = rs.getDouble("price");
			dataList.put(rs.getInt("id"), pd);
		}
		st.close();
		}catch(SQLException e) {
			System.out.println("Could not read product list!");
			System.exit(1);
		}
		return dataList;
	}
	public Product getProductData(Product product) {
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM products WHERE id = "+product.ID);
			if(rs.next()) {
				product.Name = rs.getString("name");
				product.Price = rs.getDouble("price");
			}else {
				return null;
			}
			st.close();
		}catch(SQLException e) {
			System.out.println("Error getting product data!");
		}
		return product;
	}
	public LinkedHashMap<Integer, DataStorage> readFinalInvoice(int fakeUserID, LinkedHashMap<Integer, DataStorage> invoice) {
		Statement st;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT shoppingCart.count count, products.name name, products.price price"
					+ " FROM shoppingCart"
					+ " LEFT JOIN products ON shoppingCart.productfk = products.id"
					+ " WHERE userID = "+fakeUserID);
			InvoiceLine invoiceLine;
			int i = 0;
			while(rs.next()) {
				i++;
				invoiceLine = new InvoiceLine();
				invoiceLine.name = rs.getString("name");
				invoiceLine.count = rs.getInt("count");
				invoiceLine.price = rs.getDouble("price");
				invoice.put(i,invoiceLine);
			}
		} catch (SQLException e) {
			System.out.println("Error loading invoice data!");
		}
		
		return invoice;
	}
	
}
