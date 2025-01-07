package printer;

import users.User;

/**
 * This class is responsible for printing an ordinary customer type user.
 * 
 * @author 24862664
 */
public class CustomerPrinter extends Printer {
	
    /**
     * Constructs an CustomerPrinter object to print out a customer user profile.
     * @param User obj
     */	
	public CustomerPrinter(User obj) {
		printBehaviour = new CustomerBehaviour(obj);
	}
	
}
