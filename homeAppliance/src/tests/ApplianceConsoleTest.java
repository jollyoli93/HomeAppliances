package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import IOHandlers.MockIOHandler;
import homeApplianceStoreConsole.ApplianceConsole;

class ApplianceConsoleTest {
	ApplianceConsole console;

	@BeforeEach
	public void menuConsoleTest () {
		console = new ApplianceConsole("HomeApplianceTest");
	}
	
	@Test 
	void addTVAppliance () {
		int[] addAppliance = {3, 1, 1, 1};
		
		console.setHandler(new MockIOHandler(addAppliance));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: Add Product");
		String statement = console.displayMenu();	
		
		assertEquals("Exiting", statement);
	}

	@Test
	public void testExit () {
		int[] enterThenExit = {6};
		
		console.setHandler(new MockIOHandler(enterThenExit));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: Test Exiting");
		
		String statement = console.displayMenu();	
		assertEquals("Exiting", statement);
	}
	
	@Test
	public void listProducts() {
		int[] listAll = {1, 6};
		console.setHandler(new MockIOHandler(listAll));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: List all products");
		
		String statement = console.displayMenu();	
		assertEquals("Exiting", statement);
	}
	
	
	public void getEmptyProductById() {
		int[] emptyProduct = {2, 0};
		console.setHandler(new MockIOHandler(emptyProduct));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: No product ID");
		
		String statement = console.displayMenu();	
		
		assertEquals("Exiting", statement);
	}
	
	@Test
	public void deleteProductById () { 
		int[] delete = {5, 0};
		
		console.setHandler(new MockIOHandler(delete));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: Deleting product ID: 1");
		
		String statement = console.displayMenu();	
		
		assertEquals("Exiting", statement);
	}
	
	@Test
	public void getProductById() {
		int[] product = {2, 1};
		console.setHandler(new MockIOHandler(product));
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Mock Test: Get product by ID: 1");
		
		String statement = console.displayMenu();	
		
		assertEquals("Exiting", statement);
	}
}
