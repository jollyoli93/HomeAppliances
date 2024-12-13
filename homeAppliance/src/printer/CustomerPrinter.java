package printer;

import users.User;

public class CustomerPrinter extends Printer {
		
		public CustomerPrinter(User obj) {
			printBehaviour = new CustomerBehaviour(obj);
		}
		
}
