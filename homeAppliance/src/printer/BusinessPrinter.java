package printer;

import users.BusinessUser;

/**
 * This class is responsible for printing a business type user.
 * 
 * @author 24862664
 */
public class BusinessPrinter extends Printer {
	
    /**
     * Constructs an BusinessPrinter object to print out a business user profile.
     * @param business user object
     */
	public BusinessPrinter(BusinessUser obj) {
		printBehaviour = new BusinessBehaviour(obj);
	}
}
