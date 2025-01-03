package mainHandlers;

import com.sun.net.httpserver.HttpServer;

import DAO.ApplianceDao;
import DAO.UserDao;
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
import customerHandlers.EditUserHandler;
import loginHandlers.LoginHandler;
import sessionManagement.SessionManager;
import sessionManagement.SessionValidationHandler;

import java.net.InetSocketAddress;
import java.io.IOException;

public class Main {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        String dbpath = "HomeAppliances";
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // Create DAO objects
        ApplianceDao applianceDao = new ApplianceDao(dbpath);
        UserDao userDao = new UserDao(dbpath);

        // Create session manager
        SessionManager sessionManager = new SessionManager();

        // Register contexts
        registerContexts(server, applianceDao, userDao, sessionManager);

        // Start server
        server.setExecutor(null);
        server.start();
        System.out.println("The server is listening on port " + PORT);
    }

    private static void registerContexts(HttpServer server, ApplianceDao applianceDao, UserDao userDao, SessionManager sessionManager) {
        // Public routes
        server.createContext("/", new RootHandler());
        server.createContext("/login", new LoginHandler(userDao, sessionManager));
        server.createContext("/logout", new LoginHandler(userDao, sessionManager));
        server.createContext("/success", new SuccessPageHandler(sessionManager));
        server.createContext("/update", new UpdateUserHandler(userDao, sessionManager));

        // Admin routes with session validation
        server.createContext("/admin", new SessionValidationHandler(new AdminHandler(), sessionManager));
        server.createContext("/admin/appliances", new SessionValidationHandler(new ApplianceList(applianceDao), sessionManager));


        // Department selection and type selection
        server.createContext("/admin/appliances/add", new SessionValidationHandler(new AddApplianceDeptHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/add/type", new SessionValidationHandler(new AddApplianceTypeHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/add/confirm", new SessionValidationHandler(new AddApplianceConfirmHandler(applianceDao), sessionManager));

        // Other appliance routes
        server.createContext("/admin/appliances/edit", new SessionValidationHandler(new EditApplianceForm(applianceDao), sessionManager));
        server.createContext("/admin/appliances/update", new SessionValidationHandler(new UpdateApplianceHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/delete", new SessionValidationHandler(new DeleteApplianceHandler(applianceDao), sessionManager));
        server.createContext("/admin/appliances/delete-confirm", new SessionValidationHandler(new DeleteApplianceConfirmationHandler(applianceDao), sessionManager));

        // User management
        server.createContext("/admin/users", new SessionValidationHandler(new UsersHandler(), sessionManager));
        server.createContext("/admin/users/view", new SessionValidationHandler(new ViewCustomerUsersHandler(userDao), sessionManager));
        server.createContext("/admin/users/view-admin", new SessionValidationHandler(new ViewAdminUsersHandler(userDao), sessionManager));
        server.createContext("/admin/users/view-address", new SessionValidationHandler(new ViewAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/add", new SessionValidationHandler(new CreateUserHandler(userDao), sessionManager));
        server.createContext("/admin/users/add-select", new SessionValidationHandler(new SelectUserHandler(), sessionManager));
        server.createContext("/admin/users/add-confirm", new SessionValidationHandler(new ConfirmCreateUserHandler(userDao), sessionManager));
        server.createContext("/admin/users/edit", new SessionValidationHandler(new EditUserAdminHandler(userDao), sessionManager));
        server.createContext("/admin/users/update-address", new SessionValidationHandler(new UpdateAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/edit-address", new SessionValidationHandler(new EditAddressHandler(userDao), sessionManager));
        server.createContext("/admin/users/delete", new SessionValidationHandler(new DeleteUserHandler(userDao), sessionManager));
        server.createContext("/admin/users/delete-confirm", new SessionValidationHandler(new DeleteUserConfirmationHandler(userDao), sessionManager));
        server.createContext("/admin/users/promote", new SessionValidationHandler(new PromoteUserHandler(userDao), sessionManager));

        // Customer routes
        server.createContext("/home", new CustomerHomeHandler());
        server.createContext("/users/add", new CreateUserHandler(userDao));
        server.createContext("/users/edit", new EditUserHandler(userDao, sessionManager));
    }
}
