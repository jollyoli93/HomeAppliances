package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appliances.Appliance;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;
import shoppingCart.ShoppingCart;
import shoppingCart.ShoppingCartItem;
import users.BusinessUser;
import users.CustomerUser;
import users.User;

/**
 * Unit tests for shopping cart functionality, testing various operations like adding, 
 * removing, clearing items, and calculating total prices for different user types.
 * 
 * @author 24862664
 */
class ShoppingCartTesting {
    User customer;
    User business;
    ShoppingCart customerCart;
    ShoppingCart businessCart;
    HomeCleaningFactory homeDept;
    EntertainmentFactory entertainDept;

    /**
     * Initializes test data before each test.
     */
    @BeforeEach
    public void setUp () {
        customer = new CustomerUser("Jimmy", "Grimble", "jimmygrimble@woohoo.com", "jimbob98", "ilovefootball", "07498352989");
        business = new BusinessUser("Aladin", "Ababwa", "princeali@magiccarpet.com", "princeali", "jasmine123", "07438273647", "Alibaba");
        
        customerCart = new ShoppingCart(customer);
        businessCart = new ShoppingCart(business);
        
        homeDept = new HomeCleaningFactory();
        entertainDept = new EntertainmentFactory(); 
    }

    /**
     * Tests adding a single item to the shopping cart.
     */
    @Test
    public void addItemToCart () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        ShoppingCartItem item1 = new ShoppingCartItem(customer, tv);
        customerCart.addItem(item1);
        
        assertEquals(1, customerCart.getItems().size());
    }

    /**
     * Tests removing an item from the shopping cart.
     */
    @Test
    public void RemoveItemFromCart () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        ShoppingCartItem item1 = new ShoppingCartItem(customer, tv);
        customerCart.addItem(item1);
        
        customerCart.removeItem(1);
        
        assertEquals(0, customerCart.getItems().size());
    }

    /**
     * Tests adding multiple items to the shopping cart.
     */
    @Test
    public void addTwoItemsToCart () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        Appliance lcd = entertainDept.selectAppliance("lcd television");
        tv.setId(2);
        
        ShoppingCartItem item1 = new ShoppingCartItem(customer, tv);
        customerCart.addItem(item1);
        
        ShoppingCartItem item2 = new ShoppingCartItem(customer, lcd);
        customerCart.addItem(item2);
        
        assertEquals(2, customerCart.getItems().size());
    }

    /**
     * Tests clearing all items from the shopping cart.
     */
    @Test
    public void clearCart () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        Appliance lcd = entertainDept.selectAppliance("lcd television");
        tv.setId(2);
        
        ShoppingCartItem item1 = new ShoppingCartItem(customer, tv);
        customerCart.addItem(item1);
        
        ShoppingCartItem item2 = new ShoppingCartItem(customer, lcd);
        customerCart.addItem(item2);
        
        customerCart.clearCart();
        
        assertEquals(0, customerCart.getItems().size());
    }

    /**
     * Tests calculating the total price for a customer's shopping cart.
     */
    @Test
    public void getCustomerCartTotalCustomer () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        ShoppingCartItem item1 = new ShoppingCartItem(customer, tv);
        customerCart.addItem(item1);
        
        assertEquals(100, customerCart.getTotalPrice());
    }

    /**
     * Tests calculating the total price for a business customer's shopping cart.
     */
    @Test
    public void getBusinessCartTotalCustomer () {
        Appliance tv = entertainDept.selectAppliance("basic television");
        tv.setId(1);
        
        ShoppingCartItem item1 = new ShoppingCartItem(business, tv);
        businessCart.addItem(item1);
            
        assertEquals(80, businessCart.getTotalPrice());
    }
}
