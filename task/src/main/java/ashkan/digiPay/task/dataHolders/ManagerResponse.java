package ashkan.digiPay.task.dataHolders;

import ashkan.digiPay.task.enums.MessageType;

public class ManagerResponse {
	public int count = 0;
	public MessageType messageType = MessageType.EMPTY;
	public boolean error = false;
	public Product product = new Product();
}
