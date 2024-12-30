package webpages;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.IOException;

public class Main {
  private static final int PORT = 8080;

  public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
    
    // Create contexts dynamically
    registerContexts(server);
    
    // Start server
    server.setExecutor(null);
    server.start();
    System.out.println("The server is listening on port " + PORT);
  }

  private static void registerContexts(HttpServer server) {
    // Registering routes for the homepage, admin pages, login pages, etc.
    server.createContext("/", new RootHandler());
    server.createContext("/customerLogin", new CustomerLogin());
    server.createContext("/adminLogin", new AdminLogin());

    // ADMIN
    server.createContext("/admin", new AdminHandler());
    server.createContext("/admin/products", new ApplianceHandler());
    server.createContext("/admin/products/edit", new EditProductHandler()); 
    server.createContext("/admin/products/delete", new DeleteProductHandler()); 
    
    server.createContext("/admin/users", new UsersHandler());
    server.createContext("/admin/users/create", new CreateUserHandler());  
   

    //Customer
  }
}