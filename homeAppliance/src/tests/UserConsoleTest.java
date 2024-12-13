package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DAO.DAO;
import DAO.UserDao;
import homeApplianceStore.UserConsole;
import users.AdminUser;

class UserConsoleTest {
	String dbpath = "HomeApplianceTest";
			
	@Test
	public void addUserToDatabase () {
		UserConsole console = new UserConsole(dbpath);
		AdminUser man = new AdminUser("Man", "Human", "Manhuma@yahoo.com", "ManHuman", "Useruserman");
		
		
//		System.out.println("Full Name: " + man.getFullName());
	}
}
