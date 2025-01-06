/**
 * Defines behavior for printing details of different entities like appliances, users (customer, admin, business).
 * Implements the {@link PrintBehaviour} interface, providing different print implementations for each entity.
 * 
 * @author 24862664
 */

package printer;

import appliances.Appliance;
import users.User;

/**
 * Interface for defining print behaviour.
 */
public interface PrintBehaviour {
    /**
     * Prints the details of an object.
     */
    void print();
}

/**
 * Prints details of an appliance.
 */
class ApplianceBehaviour implements PrintBehaviour {
    private final Appliance appliance;
    
    /**
     * Constructor to initialise with appliance details.
     * 
     * @param appliance The appliance whose details need to be printed.
     */
    ApplianceBehaviour(Appliance appliance){
        this.appliance = appliance;
    }

    @Override
    public void print() {
        System.out.println("ID: " + appliance.getId());
        System.out.println("SKU: " + appliance.getSku());
        System.out.println("Description: " + appliance.getDescription());
        System.out.println("Category: " + appliance.getCategory());
        System.out.println("Price: £" + appliance.getPrice());
        System.out.println("-----------------------");
    }
}

/**
 * Prints details of a customer.
 */
class CustomerBehaviour implements PrintBehaviour {
    private final User user;
    
    /**
     * Constructor to initialise with customer details.
     * 
     * @param user The customer whose details need to be printed.
     */
    CustomerBehaviour(User user){
        this.user = user;
    }

    @Override
    public void print() {
        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email Address: " + user.getEmailAddress());
        System.out.println("Telephone Number: " + user.getTelephoneNum());
        System.out.println("-----------------------");
    }
}

/**
 * Prints details of an admin user.
 */
class AdminBehaviour implements PrintBehaviour {
    private final User user;
    
    /**
     * Constructor to initialise with admin user details.
     * 
     * @param user The admin user whose details need to be printed.
     */
    AdminBehaviour(User user){
        this.user = user;
    }

    @Override
    public void print() {
        System.out.println("Full Name: " + user.getFullName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email Address: " + user.getEmailAddress());
        System.out.println("-----------------------");
    }
}

/**
 * Prints details of a business user.
 */
class BusinessBehaviour implements PrintBehaviour {
    private final User user;
    
    /**
     * Constructor to initialise with business user details.
     * 
     * @param user The business user whose details need to be printed.
     */
    BusinessBehaviour(User user){
        this.user = user;
    }

    @Override
    public void print() {
        System.out.println("Business Name: " + user.getBusinessName());
        System.out.println("Account Holder: " + user.getFullName());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email Address: " + user.getEmailAddress());
        System.out.println("Telephone Number: " + user.getTelephoneNum());
        System.out.println("-----------------------");
    }
}
