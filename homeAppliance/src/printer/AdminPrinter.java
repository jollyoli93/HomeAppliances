package printer;

import users.AdminUser;

/**
 * This class is responsible for printing tasks specific to an admin user.
 * 
 * @author 24862664
 */
public class AdminPrinter extends Printer {

    /**
     * Constructs an AdminPrinter object for the specified admin user.
     * 
     * @param admin user
     */
    public AdminPrinter(AdminUser user) {
        printBehaviour = new AdminBehaviour(user);
    }
}
