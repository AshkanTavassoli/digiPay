package ashkan.digiPay.task.dataHolders;

import java.util.ArrayList;

public class InvoiceLine extends DataStorage{
	public String name = "";
	public int count = 0;
	public double price = 0;
	public ArrayList<ExtraCost> extraCosts = new ArrayList<>();
}
