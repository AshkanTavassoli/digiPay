package ashkan.digiPay.task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InputParser extends ConnectionManager{
	private static Scanner scanner = new Scanner(System.in);

	public static void addToShoppingCart() {
		boolean done = false;
		String name = "";
		double price = 0;
		while(!done) {
			String input = scanner. nextLine();
			int id = 0;
			try {
				id = Integer.parseInt(input);
			}catch (Exception e) {
				System.out.println("Please enter number value only(ID):");
				continue;
			}
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM products WHERE id = "+id);
				if(rs.next()) {
					name = rs.getString("name");
					price = rs.getDouble("price");
					done = true;
				}else {
					System.out.println(id+" is not valid, please enter an ID from list above:");
					continue;
				}

			}catch (SQLException e) {
				System.out.println("Problem Connecting to database(adding to shopping list)");
			}	
		}
		
		System.out.println("How many "+name+" do you want?(each cost "+price+"$)");
		done = false;
		int count;
		
		while(!done) {
			String input = scanner. nextLine();
			try {
				count = Integer.parseInt(input);
			}catch (Exception e) {
				System.out.println("Please enter number value only:");
				continue;
			}
			if(count<1) {
				System.out.println("Item count should be bigger than 0! Please enter a valid number:");
			}else {
				done = true;
			}
		}
		
		
	}
}
