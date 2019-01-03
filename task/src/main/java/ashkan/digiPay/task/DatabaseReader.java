package ashkan.digiPay.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DatabaseReader extends ConnectionManager{
	LinkedHashMap<Integer , DataStorage> dataList = new LinkedHashMap<>();
	public void productList() {
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

	}
	public void printProductList() {
		Printer.print(dataList, printType.ProductList);
	}
	
}
