package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.ApplianceDao;
import IOHandlers.MockIOHandler;
import mainConsole.ApplianceConsole;

class ApplianceConsoleTest {
    ApplianceConsole console;
	ApplianceDao dao;
	String dbpath = "HomeApplianceTest";

    @BeforeEach
    public void menuConsoleTest () {
    	System.out.println();
    	System.out.println("------ Start Test ------");
		dao = new ApplianceDao(dbpath);
		
		dao.dropApplianceTable(dbpath);
		
        console = new ApplianceConsole(dbpath);
    }
    
    void addMockTVAppliance () {
        String[] nums = {"3", "1", "1", "1"};
        console.setHandler(new MockIOHandler(nums));
        String statement = console.displayMenu();  
        System.out.println(statement);
    }
    
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
    @Test
    public void productIdDoesntExist() {
        String[] nums = {"2", "0"};
        console.setHandler(new MockIOHandler(nums));
        
        System.out.println();
        System.out.println("***********************************");
        System.out.println("Mock Test: No product ID");
        
        String statement = console.displayMenu();    
        
        assertEquals("No item found in the database", statement);
    }
    
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
}
