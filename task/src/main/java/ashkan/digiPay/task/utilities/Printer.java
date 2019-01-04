package ashkan.digiPay.task.utilities;

import java.util.HashMap;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ExtraCost;
import ashkan.digiPay.task.dataHolders.InvoiceLine;
import ashkan.digiPay.task.dataHolders.Product;
import ashkan.digiPay.task.enums.MessageType;
import ashkan.digiPay.task.enums.PrintType;


public class Printer {
	public static void print(LinkedHashMap<Integer, DataStorage> dataList,PrintType pt,HashMap<Integer, Integer> localShoppingCart) {
		switch(pt) {
		case ProductList:
			System.out.println("Available products:");
			Product pd;
			int countSelected = 0;
			for(int i : dataList.keySet()) {
				pd = (Product) dataList.get(i);
				countSelected = Accountant.getCount(pd.ID,localShoppingCart);
				if(countSelected>0) {
					System.out.println(pd.ID+" : "+pd.Name+" -- Price: "+pd.Price+"$ -- You have already requested "+countSelected+" of this.");
				}else {
					System.out.println(pd.ID+" : "+pd.Name+" -- Price: "+pd.Price+"$ --");
				}
			}
			System.out.println("0 : Checkout");
			break;
		case Invoice:
			System.out.println("-----------------------------------------------------");
			System.out.println("Your Final Invoice:");
			InvoiceLine il;
			double itemTotalPrice = 0;
			double sum = 0;
			double itemExtraCost = 0;
			for(int i : dataList.keySet()) {
				il = (InvoiceLine) dataList.get(i);
				itemTotalPrice = il.price * il.count;
				System.out.println(i+": "+il.count+" item of "+il.name
						+"(each one costs: "+il.price+"$) - Total Price : " + itemTotalPrice+"$");
				for(ExtraCost ec:il.extraCosts) {
					itemExtraCost = Accountant.calculateExtraCosts(ec, il.price , il.count);
					itemTotalPrice = itemTotalPrice + itemExtraCost;
					if(ec.isPercentage) {
						System.out.println("-- "+ec.percentage+"% for "+ec.name+" - "+itemExtraCost+"$");
					}else {
						System.out.println("-- fixed cost for "+ec.name+" - "+itemExtraCost+"$");
					}
				}
				if(!il.extraCosts.isEmpty()) {
					System.out.println("**  Item Final Price is: "+ itemTotalPrice+"$ **");
				}
				sum = sum + itemTotalPrice;
			}
			System.out.println("###### Required Payment: "+sum+"$ ######");
			break;
		case HowMany:
			Product product = (Product) dataList.get(0);
			System.out.println("How many "+product.Name+" do you want?(each cost "+product.Price+"$)");
			break;
	}
	}
	
	public static void printMessage(MessageType type) {
		switch(type) {
			case NormalInput:
				System.out.println("Please enter selected product ID:");
				break;
			case WrongInput:
				System.out.println("Please enter number values only(ID):");
				break;
			case InvalidID:
				System.out.println("Please choose between IDs listed above:");
				break;
			case InvalidCount:
				System.out.println("Item count should be bigger than 0! Please enter a valid number:");
				break;
			case EMPTY:
				break;
		default:
			break;
		}
	}
}
