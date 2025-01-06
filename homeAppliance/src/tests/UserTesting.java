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

/**
 * Unit tests for the User class and its subclasses, including CustomerUser, AdminUser, and BusinessUser.
 * These tests verify the correct functionality of user attributes, address management, password validation,
 * and user role handling.
 * 
 * @author 24862664
 */
class UserTesting {
    User customer;
    User admin;
    User business;
    Address billingAddress;
    Address shippingAddress;

    /**
     * Initialises test data by creating instances of different user types (CustomerUser, AdminUser, and BusinessUser),
     * and creates billing and shipping addresses for the customer.
     */
    @BeforeEach
    public void setUp () {
        customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble@woohoo.com", "jimbob98", "ilovefootball", "07498352989");
        admin = new AdminUser("Master", "Chief", "echo419@halo.com", "cortanaforlife", "mrchief");
        business = new BusinessUser("Aladin", "Ababwa", "princeali@magiccarpet.com", "princeali", "jasmine123", "07438273647", "Alibaba");
        
        billingAddress = new ShippingAddress("15", "Oxford Rd", "Manchester", "M1 2BJ", "United Kingdom", customer.getCustomerId(), false);
        shippingAddress = new BillingAddress("1", "Mancunian Way", "Manchester", "M1 1AA", "United Kingdom", customer.getCustomerId(), true);
    }

    /**
     * Verifies that the username of the customer is correctly set.
     */
    @Test
    public void usernameIsCorrect () {
        assertEquals("jimbob98", customer.getUsername());
    }

    /**
     * Verifies that the full name of the customer is correctly formatted.
     */
    @Test
    public void fullNameIsCorrect () {
        assertEquals("Jimmy Grimble", customer.getFullName());
    }

    /**
     * Verifies that the email address of the customer is correctly set.
     */
    @Test
    public void emailIsCorrect () {
        assertEquals("jimmygrimble@woohoo.com", customer.getEmailAddress());
    }

    /**
     * Verifies that the telephone number of the customer is correctly set.
     */
    @Test
    public void telephoneNumberIsCorrect () {
        assertEquals("07498352989", customer.getTelephoneNum());
    }

    /**
     * Tests adding multiple addresses (billing and shipping) to the customer's account and verifies the size of the address list.
     */
    @Test
    public void addMultipleAddresses () {
        customer.addAddress(billingAddress);
        customer.addAddress(shippingAddress);
        
        assertEquals(2, customer.getAddresses().size());
    }

    /**
     * Tests removing an address from the customer's account and verifies the address list size.
     */
    @Test 
    public void removeAddress () {
        customer.addAddress(billingAddress);
        assertEquals(1, customer.getAddresses().size());
        
        customer.removeAddress(billingAddress);
        assertEquals(0, customer.getAddresses().size());
    }

    /**
     * Verifies that the customer's role is correctly identified as "customer".
     */
    @Test
    public void isCustomer () {
        assertEquals("customer", customer.getRole());
    }

    /**
     * Verifies that the admin's role is correctly identified as "admin".
     */
    @Test
    public void isAdmin () {
        assertEquals("admin", admin.getRole());
    }

    /**
     * Verifies that the business user's role is correctly identified as "business".
     */
    @Test
    public void isBusiness () {
        assertEquals("business", business.getRole());
    }
    
    // Password validation tests

    /**
     * Verifies that the password is hashed correctly and is valid when checked against the correct plain text password.
     */
    @Test
    public void passwordIsHashedAndValid_whenSetCorrectly() {
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);
        assertTrue(customer.validatePassword(plainPassword));
    }

    /**
     * Verifies that the password validation returns false when an incorrect password is provided.
     */
    @Test
    public void passwordIsInvalid_whenIncorrectPassword() {
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);
        assertFalse(customer.validatePassword("wrongpassword"));
    }

    /**
     * Verifies that the password validation returns false when a null password is provided.
     */
    @Test
    public void passwordIsInvalid_whenNullPassword() {
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);
        assertFalse(customer.validatePassword(null));
    }

    /**
     * Verifies that the password validation returns false when an empty password is provided.
     */
    @Test
    public void passwordIsInvalid_whenEmptyPassword() {
        String plainPassword = "ilovefootball";
        customer.setPassword(plainPassword);
        assertFalse(customer.validatePassword(""));
    }
}
