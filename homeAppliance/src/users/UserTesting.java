package users;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTesting {
    User customer;
    User admin;
    Address billingAddress;
    Address shippingAddress;

    @BeforeEach
    public void isInitialized () {
    	//String firstname, String lastName, String emailAddress, String password, String username, String telephoneNum
        customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble@woohoo.com", "ilovefootball", "jimbob98", "07498352989");
        admin = new AdminUser("Master", "Chief", "echo419@halo.com", "cortanaforlife", "hankychief", "07498352982");
        
        Address billingAddress = new ShippingAddress(15, "Oxford Rd", "Manchester", "M1 2BJ", customer.getCustomerId());
        Address shippingAddress = new BillingAddress(1, "Mancunian Way", "Manchester", "M1 1AA", customer.getCustomerId());
        
    }

    @Test
    public void usernameIsCorrect () {
        assertEquals("jimbob98", customer.getUsername());
    }

    @Test
    public void fullNameIsCorrect () {
        assertEquals("JimmyGrimble", customer.getFullName());
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
    public void isAdmin () {
    	assertTrue(admin.getAdminStatus());
    }
}

