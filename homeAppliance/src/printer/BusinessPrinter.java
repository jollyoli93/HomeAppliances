package printer;

import users.User;

public class BusinessPrinter extends Printer {
	public BusinessPrinter(User obj) {
		printBehaviour = new BusinessBehaviour(obj);
	}
}
