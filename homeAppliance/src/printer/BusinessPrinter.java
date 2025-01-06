package printer;

import users.User;

/**
 * This class is responsible for printing a business type user.
 * 
 * @author 24862664
 */
public class BusinessPrinter extends Printer {
	
    /**
     * Constructs an BusinessPrinter object to print out a business user profile.
     * @param User object
     */
	public BusinessPrinter(User obj) {
		printBehaviour = new BusinessBehaviour(obj);
	}
}
