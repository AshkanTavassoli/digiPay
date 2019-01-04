package ashkan.digiPay.task;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.Services.DatabaseReader;
import ashkan.digiPay.task.Services.ShoppingCartManager;
import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ExtraCost;
import ashkan.digiPay.task.dataHolders.ManagerResponse;
import ashkan.digiPay.task.enums.MessageType;
import ashkan.digiPay.task.enums.PrintType;
import ashkan.digiPay.task.utilities.Accountant;
import ashkan.digiPay.task.utilities.ConnectionManager;
import ashkan.digiPay.task.utilities.ExternalDataLoader;
import ashkan.digiPay.task.utilities.Printer;

public class RunThisOne {

	public static void main(String[] args) {
		//initializing database and require connection
		Connection conn = ConnectionManager.connect();
		InitialData.createDatabase(conn);
		ArrayList<ExtraCost> extraCostLists = ExternalDataLoader.extraCostsLoader();
		
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
		Accountant.createInvoice(localShoppingCart,reader,extraCostLists);
	}

}
