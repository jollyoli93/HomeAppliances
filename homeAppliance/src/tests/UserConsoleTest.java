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
	String dbpath = "HomeApplianceTest";
	
	@BeforeEach
	public void userConsoleTest () {
		console = new UserConsole(dbpath);
	}
	
	@Test
	public void addCustomerUser () {
		//CustomerUser(String firstName, String lastName, String emailAddress, String username, String password, String telephoneNum
		int[] findAllUsers = {1};
		String[] customer = {"Billy", "Jean", "billyjean@mjm.com", "NotMyLover", "07523435456"};
		
		console.setHandler(findAllUsers, customer);
	}
	
	@Test
	public void findAllUsers () {
		int[] findAllUsers = {1};

		AdminUser man = new AdminUser("Man", "Human", "Manhuman@yahoo.com", "ManHuman", "Useruserman");
		
		
		console.setHandler(new MockIOHandler(findAllUsers));
		console.userMenu();
		
//		System.out.println("Full Name: " + man.getFullName());
	}
	
	@Test
	public void userListIsEmpty () {
		UserDao dao = new UserDao(dbpath);
		int[] findAllUsers = {1};
		dao.dropUserTable();
		
		console.setHandler(new MockIOHandler(findAllUsers));
		console.userMenu();
	}
	
}
