package ashkan.digiPay.task;

import java.util.HashMap;
import java.util.LinkedHashMap;

import ashkan.digiPay.task.dataHolders.DataStorage;
import ashkan.digiPay.task.dataHolders.ExtraCost;
import ashkan.digiPay.task.dataHolders.InvoiceLine;
import ashkan.digiPay.task.dataHolders.PrintType;

public class Accountant{
	private static int fakeUserID = 1;
	public static HashMap<Integer, Integer> addItem(int id,int count,HashMap<Integer, Integer> localShoppingCart) {
		if(localShoppingCart.containsKey(id)){
			localShoppingCart.put(id, localShoppingCart.get(id) + count);
			return localShoppingCart;
		}else {
			localShoppingCart.put(id,count);
			return localShoppingCart;
		}
	}
	
	public static Integer getCount(int id,HashMap<Integer, Integer> localShoppingCart) {
		if(localShoppingCart.containsKey(id)) {
			return localShoppingCart.get(id);
		}else {
			return 0;
		}
	}
	
	public static void checkout(HashMap<Integer, Integer> localShoppingCart) {
			for(int i:localShoppingCart.keySet()) {
				DatabaseWriter.addInvoiceLine(fakeUserID, i, localShoppingCart.get(i));
			}
	}
	
	public static void createInvoice(HashMap<Integer, Integer> localShoppingCart, DatabaseReader reader) {
			InvoiceLine invoiceLine;
			LinkedHashMap<Integer , DataStorage> invoice = new LinkedHashMap<>();
			invoice = reader.readFinalInvoice(fakeUserID,invoice);
			for(int i :invoice.keySet()) {
				invoiceLine = (InvoiceLine) invoice.get(i);
				addExtraCosts(invoiceLine);
				invoice.put(i,invoiceLine);
			}
			Printer.print(invoice, PrintType.Invoice,localShoppingCart);

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
