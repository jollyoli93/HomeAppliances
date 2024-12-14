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
		//clear user table before each test
		dao = new UserDao(dbpath);
		dao.dropUserTable();
		
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
		
		System.out.println("________________________");
		System.out.println("TEST - User list is Empty");
		System.out.println("________________________");
		
		console.setHandler(new MockIOHandler(findAllUsers));
		console.userMenu();
		
		assertEquals(null, output);
	}
	
	@Test
	public void deleteUserByID () {
		String[] deleteUserOne = {"5", "1"};
		
		System.out.println("________________________");
		System.out.println("TEST - Delete user 1");
		System.out.println("________________________");
		
		addUserCustomer();
		
		console.setHandler(new MockIOHandler(deleteUserOne));
		output = console.userMenu();
		
		assertEquals("User deleted successfully.", output);
	}
	
	@Test
	public void FailedTodeleteUserByID () {
		String[] deleteUserOne = {"5"};
		
		System.out.println("________________________");
		System.out.println("TEST - Delete user failed");
		System.out.println("________________________");
		
		console.setHandler(new MockIOHandler(deleteUserOne));
		output = console.userMenu();
		
		assertEquals("Failed to delete user.", output);
	}
	
}
