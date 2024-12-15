package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.UserDao;
import IOHandlers.MockIOHandler;
import homeApplianceStore.UserConsole;

class UserConsoleTest {
	UserConsole console;
	UserDao dao;
	String output;
	String dbpath = "HomeApplianceTest";
	
	@BeforeEach
	public void userConsoleTest () {
		System.out.println("DEBUG: Console - Connect to DAO");
		//clear user table before each test
		dao = new UserDao(dbpath);
		
		System.out.println("DEBUG: Console - dropping tables");

		dao.dropUserTable();
		
		System.out.println("DEBUG: Console - Initialise console");
		//Initialise user console
		console = new UserConsole(dbpath);

	}
	
	@Test
	public void addUserCustomer () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum
		String[] customer = {"3","customer", "Billy", "Jean", "billyjean@mjm.com", "NotMyLover", "07523435456"};
		
		console.setHandler(new MockIOHandler(customer));
		output = console.userMenu();
		
		assertEquals("Succesfully added to the database", output);
	}
	
	@Test
	public void addUserAdmin () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password
		String[] customer = {"3","admin", "Man", "Human", "Manhuman@yahoo.com", "ManHuman", "Useruserman"};
		
		console.setHandler(new MockIOHandler(customer));
		output = console.userMenu();
		
		assertEquals("Succesfully added to the database", output);
	}
	
	@Test
	public void addUserBusiness () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessNamw
		String[] customer = {"3","business", "Jimmy", "Jean", "jimmyjean@mjm.com", "BillyJeansLover", "Music Shop"};
		
		console.setHandler(new MockIOHandler(customer));
		output = console.userMenu();
		
		assertEquals("Succesfully added to the database", output);
	}
	
	
	@Test
	public void findAllUsers () {
		String[] findAllUsers = {"1"};

		addUserCustomer();
		addUserBusiness();
		addUserAdmin();
		
		console.setHandler(new MockIOHandler(findAllUsers));
		console.userMenu();
		
	}
	
	@Test
	public void userListIsEmpty () {
		String[] findAllUsers = {"1"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - User list is Empty");
		System.out.println("_________________________________________________________________________________");
		
		console.setHandler(new MockIOHandler(findAllUsers));
		console.userMenu();
		
		assertEquals(null, output);
	}
	
	@Test
	public void deleteUserByID () {
		String[] deleteUserOne = {"5", "1"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - Delete user 1");
		System.out.println("_________________________________________________________________________________");
		
		addUserCustomer();
		
		console.setHandler(new MockIOHandler(deleteUserOne));
		output = console.userMenu();
		
		assertEquals("User deleted successfully.", output);
	}
	
	@Test
	public void FailedTodeleteUserByID () {
		String[] deleteUserOne = {"5", "1"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - Delete user failed");
		System.out.println("_________________________________________________________________________________");
		
		console.setHandler(new MockIOHandler(deleteUserOne));
		output = console.userMenu();
		
		assertEquals("Failed to delete user.", output);
	}
	
	@Test
	public void CorrectUpdateFirstUsersFirstname () {
		String[] UpdateFirstUser = {"4", "1","1", "Bobby"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - Update User First Name");
		System.out.println("_________________________________________________________________________________");
		
		addUserCustomer();
		
		console.setHandler(new MockIOHandler(UpdateFirstUser));
		output = console.userMenu();
		
		assertEquals("User updated successfully.", output);
	}
	
	//@Test
	public void CorrectUpdateFirstUsersSecondName () {
		String[] UpdateFirstUser = {"4", "1","2", "Bobby"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - Update User First Name");
		System.out.println("_________________________________________________________________________________");
		
		addUserCustomer();
		
		console.setHandler(new MockIOHandler(UpdateFirstUser));
		output = console.userMenu();
		
		assertEquals("User updated successfully.", output);
	}

	//@Test
	public void UpdateInvalidUsersIDFirstname () {
		String[] UpdateFirstUser = {"4", "0","1", "Bobby"};
		
		System.out.println("_________________________________________________________________________________");
		System.out.println("TEST - Update User First Name");
		System.out.println("_________________________________________________________________________________");
		
		addUserCustomer();
		
		console.setHandler(new MockIOHandler(UpdateFirstUser));
		output = console.userMenu();
		
		assertEquals("Failed to update user.", output);
	}
	
}
