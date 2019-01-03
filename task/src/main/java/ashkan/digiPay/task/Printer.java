package ashkan.digiPay.task;

import java.util.LinkedHashMap;

public class Printer {
	public static void print(LinkedHashMap<Integer, DataStorage> dataList,printType pt) {
		switch(pt) {
		case ProductList:
			System.out.println("Available products:");
			Product pd;
			for(int i : dataList.keySet()) {
				pd = (Product) dataList.get(i);
				System.out.println(pd.ID+" : "+pd.Name+" -- Price: "+pd.Price+"$ --");
			}
			System.out.println("Please enter selected product ID:");
			break;
	}
	}
}
