package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import users.Address;
import users.AdminUser;
import users.BillingAddress;
import users.BusinessUser;
import users.CustomerUser;
import users.ShippingAddress;
import users.User;

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
    
    // Password validation tests
    @Test
    public void passwordIsHashedAndValid_whenSetCorrectly() {
        // Set the password
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);

        // Validate that the hashed password matches
        assertTrue(customer.validatePassword(plainPassword));
    }

    @Test
    public void passwordIsInvalid_whenIncorrectPassword() {
        // Set the password
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);

        // Validate with incorrect password
        assertFalse(customer.validatePassword("wrongpassword"));
    }

    @Test
    public void passwordIsInvalid_whenNullPassword() {
        // Set the password
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);

        // Validate with null password
        assertFalse(customer.validatePassword(null));
    }

    @Test
    public void passwordIsInvalid_whenEmptyPassword() {
        // Set the password
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);

        // Validate with empty password
        assertFalse(customer.validatePassword(""));
    }
}
