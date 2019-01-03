package ashkan.digiPay.task;
public class RunThisOne {

	public static void main(String[] args) {
		//initializing database and require connection
		ConnectionManager.connect();
		InitialData.createDatabase();
		
		DatabaseReader reader = new DatabaseReader();
		reader.productList();
		reader.printProductList();
		
		while(!InputParser.addToShoppingCart()) {
			reader.printProductList();
		}
		
		Accountant.checkout();
		Accountant.createInvoice();
	}

}
