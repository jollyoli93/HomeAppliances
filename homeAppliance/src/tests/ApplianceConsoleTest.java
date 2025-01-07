package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.ApplianceDao;
import IOHandlers.MockIOHandler;
import mainConsole.ApplianceConsole;

/**
 * Unit tests for the ApplianceConsole class. These tests simulate the various
 * operations a user might perform with the appliance console, such as adding,
 * listing, retrieving, and deleting appliances from the system.
 * 
 * This class uses a mock I/O handler to simulate user input and test the functionality
 * of the `ApplianceConsole` class in a controlled, automated environment.
 * 
 * @author 24862664
 */
class ApplianceConsoleTest {
    ApplianceConsole console;
    ApplianceDao dao;
    String dbpath = "HomeApplianceTest";

    /**
     * Initialises the test environment before each test. This method creates a new
     * instance of the ApplianceDao and ApplianceConsole and clears any existing
     * appliance data by dropping the appliance table before each test.
     */
    @BeforeEach
    public void menuConsoleTest () {
        System.out.println();
        System.out.println("------ Start Test ------");
        dao = new ApplianceDao(dbpath);

        dao.dropApplianceTable(dbpath);

        console = new ApplianceConsole(dbpath);
    }

    /**
     * Adds a mock TV appliance for testing purposes.
     * This method simulates user input to add a TV appliance to the system.
     */
    void addMockTVAppliance () {
        String[] nums = {"3", "1", "1", "1"};
        console.setHandler(new MockIOHandler(nums));
        String statement = console.displayMenu();  
        System.out.println(statement);
    }

    /**
     * Test case for adding a TV appliance to the system.
     * Simulates the user input to add a basic TV appliance and asserts that the
     * expected result matches the output.
     */
    @Test 
    void addTVAppliance () {
        String[] nums = {"3", "1", "1", "1"};

        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Add Product");
        String statement = console.displayMenu();    

        assertEquals("You have added 1 x Basic Television", statement);
    }

    /**
     * Test case for listing all products in the system.
     * Simulates the user input to view all appliances in the system, then asserts
     * that the expected result matches the output.
     */
    @Test
    public void listProducts() {
        String[] nums = {"1", " "};

        addTVAppliance();
        addTVAppliance();

        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: List all products");

        String statement = console.displayMenu();    
        assertEquals("Product list returned", statement);
    }

    /**
     * Test case for when no products are available in the system.
     * Simulates the user input to list products when no appliances are present,
     * then asserts that the correct error message is returned.
     */
    @Test
    public void emptyProductList() {
        String[] nums = {"1"};
        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Empty product list");

        String statement = console.displayMenu();    

        System.out.println(statement);

        assertEquals("No products in the database", statement);
    }

    /**
     * Test case for retrieving a product by its ID.
     * Simulates the user input to retrieve a TV appliance by its ID, then asserts
     * that the correct appliance is found.
     */
    @Test
    public void getProductById() {
        String[] nums = {"2", "1"};

        addTVAppliance();

        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Get product by ID: 1");

        String statement = console.displayMenu();    

        assertEquals("Basic Television found.", statement);
    }

    /**
     * Test case for when a non-existent product ID is provided.
     * Simulates the user input to attempt retrieving an appliance by an invalid ID,
     * then asserts that the correct error message is returned.
     */
    @Test
    public void productIdDoesntExist() {
        String[] nums = {"2", "0"};
        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: No product ID");

        String statement = console.displayMenu();    

        assertEquals("No item found in the database.", statement);
    }

    /**
     * Test case for deleting a product by its ID.
     * Simulates the user input to delete a product by its ID and asserts that the
     * correct success message is returned.
     */
    @Test
    public void deleteProductById () { 
        addMockTVAppliance();

        String[] nums = {"5", "1"};

        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Deleting product ID: 1");

        String statement = console.displayMenu();    

        assertEquals("ID: 1 Deleted", statement);
    }

    /**
     * Test case for failing to delete a product by its ID.
     * Simulates the user input to attempt deleting a non-existent product and asserts
     * that the correct error message is returned.
     */
    @Test
    public void failedtoDeleteProductById () { 
        String[] nums = {"5", "0"};

        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Failed to Delete product ID: 1");

        String statement = console.displayMenu();    

        assertEquals("Failed to delete item.", statement);
    }
    
    /**
     * Test case for updating a product by its ID.
     * Simulates the user input updating a products price
     */
    @Test
    public void updateProductPriceById () { 
        String[] nums = {"4", "1" ,"1", "50"};
        
        addMockTVAppliance();
        
        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Updated product ID: 1");

        String statement = console.displayMenu();    

        assertEquals("Rows updated: 1", statement);
    }
    
    /**
     * Test case for failing to delete a product by its ID.
     * Simulates the user input to attempt deleting a non-existent product and asserts
     * that the correct error message is returned.
     */
    @Test
    public void FailedToUpdateProductPriceById () { 
        String[] nums = {"4", "1" ,"10", "50"};
        
        addMockTVAppliance();
        
        console.setHandler(new MockIOHandler(nums));

        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: Updated product ID: 1");

        String statement = console.displayMenu();    

        assertEquals("Rows updated: 0", statement);
    }
}
