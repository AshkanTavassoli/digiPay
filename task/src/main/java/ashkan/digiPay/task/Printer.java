package ashkan.digiPay.task;

import java.util.LinkedHashMap;
//different output types
enum printType{
	ProductList,
	Invoice
}

public class Printer {
	public static void print(LinkedHashMap<Integer, DataStorage> dataList,printType pt) {
		switch(pt) {
		case ProductList:
			System.out.println("Available products:");
			Product pd;
			int countSelected = 0;
			for(int i : dataList.keySet()) {
				pd = (Product) dataList.get(i);
				countSelected = Accountant.getCount(pd.ID);
				if(countSelected>0) {
					System.out.println(pd.ID+" : "+pd.Name+" -- Price: "+pd.Price+"$ -- You have already requested "+countSelected+" of this.");
				}else {
					System.out.println(pd.ID+" : "+pd.Name+" -- Price: "+pd.Price+"$ --");
				}
			}
			System.out.println("0 : Checkout");
			System.out.println("Please enter selected product ID:");
			break;
		case Invoice:
			
			break;
	}
	}
}
