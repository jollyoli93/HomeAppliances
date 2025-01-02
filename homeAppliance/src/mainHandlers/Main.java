package mainHandlers;

import com.sun.net.httpserver.HttpServer;

import DAO.ApplianceDao;
import DAO.UserDao;
import applianceHandlers.AddApplianceConfirmHandler;
import applianceHandlers.AddApplianceDeptHandler;
import applianceHandlers.AddApplianceTypeHandler;
import applianceHandlers.ApplianceList;
import applianceHandlers.DeleteApplianceHandler;
import applianceHandlers.DeleteApplianceConfirmationHandler;
import applianceHandlers.EditApplianceForm;
import applianceHandlers.UpdateApplianceHandler;
import userHandlers.CreateUserHandler;
import userHandlers.DeleteUserConfirmationHandler;
import userHandlers.DeleteUserHandler;
import userHandlers.EditAddressHandler;
import userHandlers.EditUserHandler;
import userHandlers.PromoteUserHandler;
import userHandlers.UsersHandler;
import userHandlers.ViewAddressHandler;
import userHandlers.ViewAdminUsersHandler;
import userHandlers.ViewCustomerUsersHandler;

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
    
    // Create contexts
    registerContexts(server, applianceDao, userDao);
    
    // Start server
    server.setExecutor(null);
    server.start();
    System.out.println("The server is listening on port " + PORT);
  }

  private static void registerContexts(HttpServer server, ApplianceDao applianceDao, UserDao userDao) {
    // Registering routes for the homepage, admin pages, login pages, etc.
    server.createContext("/", new RootHandler());
    server.createContext("/customerLogin", new CustomerLogin());
    server.createContext("/adminLogin", new AdminLogin());

    // ADMIN
    server.createContext("/admin", new AdminHandler());
    server.createContext("/admin/appliances", new ApplianceList(applianceDao));
    server.createContext("/admin/success", new SuccessPageHandler()); 
    
    // Department selection and type selection
    server.createContext("/admin/appliances/add", new AddApplianceDeptHandler(applianceDao)); // Department selection page 
    server.createContext("/admin/appliances/add/type", new AddApplianceTypeHandler(applianceDao)); //Type selection page
    server.createContext("/admin/appliances/add/confirm", new AddApplianceConfirmHandler(applianceDao)); // confirm appliance and submit
    
    // Other appliance routes
    server.createContext("/admin/appliances/edit", new EditApplianceForm(applianceDao)); 
    server.createContext("/admin/appliances/update", new UpdateApplianceHandler(applianceDao));
    server.createContext("/admin/appliances/delete", new DeleteApplianceHandler(applianceDao));
    server.createContext("/admin/appliances/delete-confirm", new DeleteApplianceConfirmationHandler(applianceDao));
    
    // User management
    server.createContext("/admin/users", new UsersHandler());
    server.createContext("/admin/users/view", new ViewCustomerUsersHandler(userDao));
    server.createContext("/admin/users/view-admin", new ViewAdminUsersHandler(userDao)); 
    server.createContext("/admin/users/view-address", new ViewAddressHandler(userDao)); 
    server.createContext("/admin/users/add", new CreateUserHandler(userDao));
    server.createContext("/admin/users/edit", new EditUserHandler(userDao));
    server.createContext("/admin/users/update", new UpdateUserHandler(userDao));
    server.createContext("/admin/users/update-address", new UpdateAddressHandler());
    server.createContext("/admin/users/edit-address", new EditAddressHandler(userDao));
    server.createContext("/admin/users/delete", new DeleteUserHandler(userDao));
    server.createContext("/admin/users/delete-confirm", new DeleteUserConfirmationHandler(userDao));
    server.createContext("/admin/users/promote", new PromoteUserHandler(userDao));  

    // Customer routes (if any needed)
  }
}
