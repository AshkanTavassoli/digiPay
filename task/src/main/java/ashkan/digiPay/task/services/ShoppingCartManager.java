package ashkan.digiPay.task.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ManagerResponse;
import ashkan.digiPay.task.dataHolders.Product;
import ashkan.digiPay.task.enums.MessageType;
import ashkan.digiPay.task.enums.PrintType;
import ashkan.digiPay.task.utilities.Printer;

public class ShoppingCartManager{
	private Scanner scanner = new Scanner(System.in);
	
	public ManagerResponse doIt(ManagerResponse response,LinkedHashMap<Integer, DataStorage> productDataList,
			HashMap<Integer, Integer> localShoppingCart,DatabaseReader reader) {
		response = parseInputID();
		if(response.error) {
			return response;
		}
		if(response.product.ID == 0) {
			response.error = true;
			response.messageType = MessageType.Checkout;
			return response;
		}
		response = getProductData(response,reader);
		if(response.error) {
			return response;
		}
		
		productDataList.put(0, response.product);
		Printer.print(productDataList,PrintType.HowMany,localShoppingCart);
		do {
			response.error = false;
			response = getCount(response);
			if(response.error) {
				Printer.printMessage(response.messageType);
			}
		}while(response.error);
		return response;
	}
	
	
	public ManagerResponse parseInputID() {
		ManagerResponse response = new ManagerResponse();
		String input = scanner.nextLine();
		int id;
		try {
			id = Integer.parseInt(input);
			response.product.ID = id;
		}catch (Exception e) {
			response.messageType = MessageType.WrongInput;
			response.error = true;
		}
		return response;
	}
	
	
	public ManagerResponse getProductData(ManagerResponse response,DatabaseReader reader) {
		Product product = reader.getProductData(response.product);
		if(product == null) {
			response.error = true;
			response.messageType = MessageType.InvalidID;
		}else {
			response.product = product;
		}
		return response;
	}
	
	public ManagerResponse getCount(ManagerResponse response) {
		String input = scanner.nextLine();
		int count;
		try {
			count = Integer.parseInt(input);
			if(count<1) {
				response.messageType = MessageType.InvalidCount;
				response.error = true;
				return response;
			}
			response.count = count;
		}catch (Exception e) {
			response.messageType = MessageType.WrongInput;
			response.error = true;
		}
		return response;
	}

}
