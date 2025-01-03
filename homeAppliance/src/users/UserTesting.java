package users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTesting {
    User customer;
    User admin;
    User business;
    Address billingAddress;
    Address shippingAddress;

    @BeforeEach
    public void setUp () {
        // Initialize test data
        customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble@woohoo.com", "jimbob98", "ilovefootball", "07498352989");
        admin = new AdminUser("Master", "Chief", "echo419@halo.com", "cortanaforlife", "mrchief");
        business = new BusinessUser("Aladin", "Ababwa", "princeali@magiccarpet.com", "princeali", "jasmine123", "07438273647", "Alibaba");

        // Create addresses for the customer
        billingAddress = new ShippingAddress("15", "Oxford Rd", "Manchester", "M1 2BJ", "United Kingdom", customer.getCustomerId(), false);
        shippingAddress = new BillingAddress("1", "Mancunian Way", "Manchester", "M1 1AA", "United Kingdom", customer.getCustomerId(), true);
    }

    @Test
    public void usernameIsCorrect () {
        assertEquals("jimbob98", customer.getUsername());
    }

    @Test
    public void fullNameIsCorrect () {
        assertEquals("Jimmy Grimble", customer.getFullName());
    }

    @Test
    public void emailIsCorrect () {
        assertEquals("jimmygrimble@woohoo.com", customer.getEmailAddress());
    }

    @Test
    public void telephoneNumberIsCorrect () {
        assertEquals("07498352989", customer.getTelephoneNum());
    }

    @Test
    public void addMultipleAddresses () {
        customer.addAddress(billingAddress);
        customer.addAddress(shippingAddress);
        
        assertEquals(2, customer.getAddresses().size());
    }

    @Test 
    public void removeAddress () {
        customer.addAddress(billingAddress);
        assertEquals(1, customer.getAddresses().size());
        
        customer.removeAddress(billingAddress);
        assertEquals(0, customer.getAddresses().size());
    }

    @Test
    public void isCustomer () {
        assertEquals("customer", customer.getRole());
    }

    @Test
    public void isAdmin () {
        assertEquals("admin", admin.getRole());
    }

    @Test
    public void isBusiness () {
        assertEquals("business", business.getRole());
    }
}
