package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DAO.UserDao;
import IOHandlers.MockIOHandler;
import mainConsole.UserConsole;

/**
 * Unit tests for the UserConsole class, covering user creation, deletion, updates, and other actions
 * like handling admin status and updating user addresses.
 * 
 * @author 24862664
 */
class UserConsoleTest {
    UserConsole console;
    UserDao dao;
    String output;
    String dbpath = "HomeApplianceTest";

    /**
     * Sets up test data by initialising UserDao and UserConsole, and clearing the user table.
     */
    @BeforeEach
    public void userConsoleTest () {
        dao = new UserDao(dbpath);
        dao.dropUserTable(dbpath);
        console = new UserConsole(dbpath);
    }
    
    /**
     * Tests adding a customer user.
     */
    @Test
    public void addUserCustomer () {
        String[] customer = {"3","customer", "Billy", "Jean", "billyjean@mjm.com", "BillyJean93", "NotMyLover", "07523435456"};
        console.setHandler(new MockIOHandler(customer));
        output = console.userMenu();
        assertEquals("Succesfully added to the database", output);
    }

    /**
     * Tests adding an admin user.
     */
    @Test
    public void addUserAdmin () {
        String[] admin = {"3","admin", "Man", "Human", "Manhuman@yahoo.com", "ManHuman", "Useruserman"};
        console.setHandler(new MockIOHandler(admin));
        output = console.userMenu();
        assertEquals("Succesfully added to the database", output);
    }

    /**
     * Tests adding a business user.
     */
    @Test
    public void addUserBusiness () {
        String[] business = {"3","business", "Jimmy", "Jean", "jimmyjean@mjm.com", "JimmyJean", "BillyJeansLover", "07565345827", "Music Shop"};
        console.setHandler(new MockIOHandler(business));
        output = console.userMenu();
        assertEquals("Succesfully added to the database", output);
    }

    /**
     * Tests finding all users.
     */
    @Test
    public void findAllUsers () {
        String[] findAllUsers = {"1"};
        addUserCustomer();
        addUserBusiness();
        addUserAdmin();
        console.setHandler(new MockIOHandler(findAllUsers));
        console.userMenu();
    }

    /**
     * Tests the case where the user list is empty.
     */
    @Test
    public void userListIsEmpty () {
        String[] findAllUsers = {"1"};
        console.setHandler(new MockIOHandler(findAllUsers));
        console.userMenu();
        assertEquals(null, output);
    }

    /**
     * Tests deleting a user by ID.
     */
    @Test
    public void deleteUserByID () {
        String[] deleteUserOne = {"5", "1"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(deleteUserOne));
        output = console.userMenu();
        assertEquals("User deleted successfully.", output);
    }

    /**
     * Tests failure to delete a user by ID.
     */
    @Test
    public void FailedTodeleteUserByID () {
        String[] deleteUserOne = {"5", "1"};
        console.setHandler(new MockIOHandler(deleteUserOne));
        output = console.userMenu();
        assertEquals("Failed to delete user.", output);
    }

    /**
     * Tests updating the first name of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersFirstname () {
        String[] UpdateFirstUser = {"4", "1", "1", "Bobby", "9"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests updating the last name of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersSecondName () {
        String[] UpdateFirstUser = {"4", "1", "2", "Bobby", "9"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests failure to update a user's first name.
     */
    @Test
    public void FailedToUpdateUsersFirstname () {
        String[] UpdateFirstUser = {"4", "0", "1", "Bobby", "9"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 0", output);
    }

    /**
     * Tests updating the email of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersEmail () {
        String[] UpdateFirstEmail = {"4", "1", "3", "newemail@email.co.uk", "9"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(UpdateFirstEmail));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests updating the password of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersPassword() {
        String[] UpdateFirstUser = {"4", "1", "4", "new password", "9"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests updating the business name of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersBusinessName() {
        String[] UpdateFirstUser = {"4", "1", "7", "Best Biz"};
        addUserBusiness();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests updating the telephone number of a user.
     */
    @Test
    public void CorrectUpdateFirstUsersTelephoneNum() {
        String[] UpdateFirstUser = {"4", "1", "6", "01514353521"};
        addUserBusiness();
        console.setHandler(new MockIOHandler(UpdateFirstUser));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests updating a user with all required details.
     */
    @Test
    public void updateUser() {
        String[] updateCustomer = {"4", "1", "1", "William", "8"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(updateCustomer));
        console.userMenu();
        assertEquals("Succesfully added to the database", output);
    }

    /**
     * Tests adding an address to a user.
     */
    @Test
    public void addUserAddress() {
        String[] addAddress = {"6", "1", "12", "Maple Street", "Springfield", "USA", "12345", "true", "1"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(addAddress));
        console.userMenu();
        assertEquals("Succesfully added to the database", output);
    }

    /**
     * Tests handling the admin status of a user.
     */
    @Test
    public void handleAdminStatus() {
        String[] giveAdmin = {"7", "1", "1"};
        addUserCustomer();
        console.setHandler(new MockIOHandler(giveAdmin));
        output = console.userMenu();
        assertEquals("Number of rows updated: 1", output);
    }

    /**
     * Tests rejecting a user’s request to remove admin status.
     */
    @Test
    public void rejectRemoveAdminStatus() {
        String[] giveAdmin = {"7", "1", "1"};
        addUserAdmin();
        console.setHandler(new MockIOHandler(giveAdmin));
        output = console.userMenu();
        assertEquals("Number of rows updated: 0", output);
    }
}
