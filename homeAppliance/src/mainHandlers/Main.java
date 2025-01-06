package mainHandlers;

import com.sun.net.httpserver.HttpServer;

import DAO.ApplianceDao;
import DAO.ShoppingDao;
import DAO.UserDao;
import adminUserHandlers.AdminHandler;
import adminUserHandlers.ConfirmCreateUserHandler;
import adminUserHandlers.CreateUserHandler;
import adminUserHandlers.DeleteUserConfirmationHandler;
import adminUserHandlers.DeleteUserHandler;
import adminUserHandlers.EditAddressHandler;
import adminUserHandlers.EditUserAdminHandler;
import adminUserHandlers.PromoteUserHandler;
import adminUserHandlers.SelectUserHandler;
import adminUserHandlers.UpdateAddressHandler;
import adminUserHandlers.UpdateUserHandler;
import adminUserHandlers.UsersHandler;
import adminUserHandlers.ViewAddressHandler;
import adminUserHandlers.ViewAdminUsersHandler;
import adminUserHandlers.ViewCustomerUsersHandler;
import applianceHandlers.AddApplianceConfirmHandler;
import applianceHandlers.AddApplianceDeptHandler;
import applianceHandlers.AddApplianceTypeHandler;
import applianceHandlers.ApplianceList;
import applianceHandlers.DeleteApplianceHandler;
import applianceHandlers.DeleteApplianceConfirmationHandler;
import applianceHandlers.EditApplianceForm;
import applianceHandlers.UpdateApplianceHandler;
import customerHandlers.AddAppliancetoBasketHandler;
import customerHandlers.CustomerHomepageHandler;
import customerHandlers.EditUserHandler;
import loginHandlers.LoginHandler;
import loginHandlers.LogoutHandler;
import sessionManagement.AdminValidationHandler;
import sessionManagement.SessionManager;
import sessionManagement.UserValidationHandler;
import shoppingCartHandler.ShoppingCartHandler;

import java.net.InetSocketAddress;
import java.io.IOException;

/**
 * Main class responsible for setting up the server, registering routes, and starting the HTTP server.
 * @author 24862664
 */
public class Main {
    // Port on which the server will listen
    private static final int PORT = 8080;

    /**
     * The entry point of the application for the admin and customer web interface. Initialises the server and sets up routes.
     *
     * @param args Command line arguments
     * @throws IOException If an error occurs while setting up the server
     */
    public static void main(String[] args) throws IOException {
        String dbpath = "HomeAppliances";  // Path to the database
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0); // Create the server on specified port

        // Create DAO objects for appliance, user, and shopping data
        ApplianceDao applianceDao = new ApplianceDao(dbpath);
        UserDao userDao = new UserDao(dbpath);
        ShoppingDao shoppingDao = new ShoppingDao(dbpath);

        // Create session manager for session handling
        SessionManager sessionManager = new SessionManager();

        // Register contexts (routes) for handling HTTP requests
        registerContexts(server, applianceDao, userDao, shoppingDao, sessionManager);

        // Start the server
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("The server is listening on port " + PORT);  // Log the port
    }

    /**
     * Registers the various HTTP contexts (routes) for different handlers.
     *
     * @param server The HTTP server to which the contexts will be registered
     * @param applianceDao The Appliance DAO used to interact with appliance data
     * @param userDao The User DAO used to interact with user data
     * @param shoppingDao The Shopping DAO used to interact with shopping data
     * @param sessionManager The session manager used to manage user sessions
     */
    private static void registerContexts(HttpServer server, ApplianceDao applianceDao, UserDao userDao, ShoppingDao shoppingDao, SessionManager sessionManager) {
        // Public routes (no session validation required)
        server.createContext("/", new RootHandler());
        server.createContext("/login", new LoginHandler(userDao, sessionManager));
        server.createContext("/logout", new LogoutHandler(sessionManager));
        server.createContext("/success", new SuccessPageHandler(sessionManager));
        server.createContext("/update", new UpdateUserHandler(userDao, sessionManager));
        server.createContext("/users/add-confirm", new ConfirmCreateUserHandler(userDao));

        // Admin routes (session validation required)
        server.createContext("/admin", new AdminValidationHandler(new AdminHandler(), sessionManager));
        server.createContext("/admin/appliances", new AdminValidationHandler(new ApplianceList(applianceDao), sessionManager));

        // Appliance-related routes with session validation
        server.createContext("/appliances/add", new UserValidationHandler(new AddApplianceDeptHandler(applianceDao), sessionManager));
        server.createContext("/appliances/add/type", new UserValidationHandler(new AddApplianceTypeHandler(applianceDao, sessionManager), sessionManager));
        server.createContext("/appliances/admin/confirm", new AdminValidationHandler(new AddApplianceConfirmHandler(applianceDao), sessionManager));

        // Admin appliance CRUD funtionality with session validation
        server.createContext("/admin/appliances/edit", new AdminValidationHandler(new EditApplianceForm(applianceDao), sessionManager));
        server.createContext("/admin/appliances/update", new AdminValidationHandler(new UpdateApplianceHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/delete", new AdminValidationHandler(new DeleteApplianceHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/delete-confirm", new AdminValidationHandler(new DeleteApplianceConfirmationHandler(applianceDao), sessionManager));

        // Admin user CRUD functionality with session validation
        server.createContext("/admin/users", new AdminValidationHandler(new UsersHandler(), sessionManager));
        server.createContext("/admin/users/view", new AdminValidationHandler(new ViewCustomerUsersHandler(userDao), sessionManager));
        server.createContext("/admin/users/view-admin", new AdminValidationHandler(new ViewAdminUsersHandler(userDao), sessionManager));
        server.createContext("/admin/users/view-address", new AdminValidationHandler(new ViewAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/add", new AdminValidationHandler(new CreateUserHandler(userDao), sessionManager));
        server.createContext("/admin/users/add-select", new AdminValidationHandler(new SelectUserHandler(), sessionManager));
        server.createContext("/admin/users/edit", new AdminValidationHandler(new EditUserAdminHandler(userDao), sessionManager));
        server.createContext("/admin/users/update-address", new AdminValidationHandler(new UpdateAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/edit-address", new AdminValidationHandler(new EditAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/delete", new AdminValidationHandler(new DeleteUserHandler(userDao), sessionManager));
        server.createContext("/admin/users/delete-confirm", new AdminValidationHandler(new DeleteUserConfirmationHandler(userDao), sessionManager));
        server.createContext("/admin/users/promote", new AdminValidationHandler(new PromoteUserHandler(userDao), sessionManager));

        // Customer routes with some session validation
        server.createContext("/home", new UserValidationHandler(new CustomerHomepageHandler(), sessionManager));
        server.createContext("/users/add", new CreateUserHandler(userDao));
        server.createContext("/users/edit", new UserValidationHandler(new EditUserHandler(userDao, sessionManager), sessionManager));
        server.createContext("/appliances/users/confirm", new UserValidationHandler(new AddAppliancetoBasketHandler(applianceDao, userDao, sessionManager), sessionManager));
        server.createContext("/users/cart", new ShoppingCartHandler(userDao, shoppingDao, sessionManager));
    }
}
