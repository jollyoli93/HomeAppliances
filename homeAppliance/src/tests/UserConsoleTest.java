package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.UserDao;
import IOHandlers.MockIOHandler;
import homeApplianceStore.UserConsole;
import users.AdminUser;

class UserConsoleTest {
	UserConsole console;
	UserDao dao;
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
		String output;
		
		console.setHandler(new MockIOHandler(customer));
		output = console.userMenu();
		
		assertEquals("Succesfully added to the database", output);
	}
	
	@Test
	public void addUserAdmin () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password
		String[] customer = {"3","admin", "Man", "Human", "Manhuman@yahoo.com", "ManHuman", "Useruserman"};
		String output;
		
		console.setHandler(new MockIOHandler(customer));
		output = console.userMenu();
		
		assertEquals("Succesfully added to the database", output);
	}
	
	@Test
	public void addUserBusiness () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum, String businessNamw
		String[] customer = {"3","business", "Jimmy", "Jean", "jimmyjean@mjm.com", "BillyJeansLover", "Music Shop"};
		String output;
		
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
		
//		System.out.println("Full Name: " + man.getFullName());
	}
//	
//	@Test
//	public void userListIsEmpty () {
//		UserDao dao = new UserDao(dbpath);
//		String[] findAllUsers = {"1"};
//		dao.dropUserTable();
//		
//		console.setHandler(new MockIOHandler(findAllUsers));
//		console.userMenu();
//	}
	
}
