package printer;

import users.User;

public class AdminPrinter extends Printer {
	public AdminPrinter(User user) {
		printBehaviour = new AdminBehaviour(user);
	}
}
