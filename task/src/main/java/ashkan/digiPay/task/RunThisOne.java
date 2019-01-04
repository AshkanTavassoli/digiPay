package ashkan.digiPay.task;

import java.util.HashMap;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ManagerResponse;
import ashkan.digiPay.task.dataHolders.MessageType;
import ashkan.digiPay.task.dataHolders.PrintType;

public class RunThisOne {

	public static void main(String[] args) {
		//initializing database and require connection
		ConnectionManager.connect();
		InitialData.createDatabase();
		
		DatabaseReader reader = new DatabaseReader();
		LinkedHashMap<Integer , DataStorage> dataList;
		dataList = reader.productList();
		MessageType messageType = MessageType.NormalInput;
		ShoppingCartManager manager = new ShoppingCartManager();
		ManagerResponse response = null;
		LinkedHashMap<Integer, DataStorage> productDataList = new LinkedHashMap<>();
		HashMap<Integer, Integer> localShoppingCart = new HashMap<>();
		
		while(messageType != MessageType.Checkout) {
			if(messageType == MessageType.NormalInput) {
				Printer.print(dataList, PrintType.ProductList,localShoppingCart);
			}
			
			Printer.printMessage(messageType);
			messageType = MessageType.NormalInput;
			
			response = manager.doIt(response, productDataList, localShoppingCart, reader);
			messageType = response.messageType;
			if(response.error) {
				continue;
			}
			messageType = MessageType.NormalInput;
			localShoppingCart = Accountant.addItem(response.product.ID, response.count,localShoppingCart);

			
		}
		
		Accountant.checkout(localShoppingCart);
		Accountant.createInvoice(localShoppingCart,reader);
	}

}
