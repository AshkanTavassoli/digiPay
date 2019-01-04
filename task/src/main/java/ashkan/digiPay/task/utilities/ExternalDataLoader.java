package ashkan.digiPay.task.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import ashkan.digiPay.task.dataHolders.ExtraCost;

public class ExternalDataLoader {
	public static ArrayList<ExtraCost> extraCostsLoader() {
		ArrayList<ExtraCost> extraCostLists = new ArrayList<>();
		BufferedReader br = null;
		ExtraCost cost;
		String data[];
		String line = "";
		try {
			br = new BufferedReader(new FileReader("ExtraCosts"));
	    	line = br.readLine();
		    while(!line.equalsIgnoreCase("/INPUT/")) {
		    	line = br.readLine();
		    }

		    while ((line = br.readLine()) != null) {
		    	data = line.split("/");
		    	if(data.length != 3) {
		    		System.out.println("Setting file pattern not followed!");
		    	}
		    	cost = new ExtraCost();
		    	cost.name = data[0];
		    	cost.percentage = Double.parseDouble(data[1]);
		    	if(cost.percentage == 0) {
		    		cost.isPercentage = false;
		    		cost.fixCost = Double.parseDouble(data[2]);
		    	}else {
		    		cost.isPercentage = true;
		    	}
		    	extraCostLists.add(cost);
		    }
		    br.close();
		    return extraCostLists;

		} catch (Exception e) {
			System.out.println("Setting file Not FOUND!");
			System.out.println(e.getMessage());
			System.exit(1);
		}
		return null;

	}

}
