package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import IOHandlers.MockIOHandler;
import homeApplianceStore.UserConsole;
import users.AdminUser;

class UserConsoleTest {
	String dbpath = "HomeApplianceTest";
			
	@Test
	public void addUserToDatabase () {
		int[] addUser = {3};
		UserConsole console = new UserConsole(dbpath);
		AdminUser man = new AdminUser("Man", "Human", "Manhuman@yahoo.com", "ManHuman", "Useruserman");
		
		
		console.setHandler(new MockIOHandler(addUser));
		
//		System.out.println("Full Name: " + man.getFullName());
	}
}
