package webHandlers;

import com.sun.net.httpserver.HttpServer;

import DAO.ApplianceDao;
import DAO.UserDao;

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
    
    // Department selection and type selection
    server.createContext("/admin/appliances/add", new AddApplianceDeptHandler(applianceDao)); // Department selection page 
    server.createContext("/admin/appliances/add/type", new AddApplianceTypeHandler(applianceDao)); //Type selection page
    server.createContext("/admin/appliances/add/confirm", new AddApplianceConfirmHandler(applianceDao)); // confirm appliance and submit
    
    // Other appliance routes
    server.createContext("/admin/appliances/edit", new EditApplianceForm(applianceDao)); 
    server.createContext("/admin/appliances/update", new UpdateApplianceHandler(applianceDao));
    server.createContext("/admin/appliances/delete", new DeleteApplianceHandler(applianceDao));
    server.createContext("/admin/appliances/delete-confirm", new DeleteConfirmationHandler(applianceDao));
    
    // User management
    server.createContext("/admin/users", new UsersHandler());
    server.createContext("/admin/users/create", new CreateUserHandler());  
    
    // Customer routes (if any needed)
  }
}
