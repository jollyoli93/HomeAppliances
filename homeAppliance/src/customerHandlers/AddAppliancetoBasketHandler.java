package customerHandlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.ShoppingDao;
import DAO.UserDao;
import appliances.Appliance;
import appliances.ApplianceDepartments;
import sessionManagement.Session;
import sessionManagement.SessionManager;
import shoppingCart.ShoppingCartItem;
import users.User;
import util.WebUtil;

public class AddAppliancetoBasketHandler implements HttpHandler {
	    private ApplianceDao applianceDao;
	    private ApplianceDepartments department;
	    private Appliance appliance;
	    private ShoppingDao shoppingDao;
	    private UserDao userDao;
	    private ShoppingCartItem  cart;
	    private SessionManager sessionManager;

	    public AddAppliancetoBasketHandler(ApplianceDao applianceDao, UserDao userDao, SessionManager sessionManager) {
	        this.applianceDao = applianceDao;
	        this.sessionManager = sessionManager;
	        this.shoppingDao = new ShoppingDao("HomeAppliances");
	        this.userDao = userDao;
	    }

	    @Override
	    public void handle(HttpExchange he) throws IOException {
	        String method = he.getRequestMethod();

	        if ("GET".equalsIgnoreCase(method)) {
	            handleGet(he);
	        } else if ("POST".equalsIgnoreCase(method)) {
	            handlePost(he);
	        } else {
	            he.sendResponseHeaders(405, -1); // Method Not Allowed
	        }
	    }

	    private void handleGet(HttpExchange he) throws IOException {
	        BufferedWriter out = null;

	        try {

	            he.sendResponseHeaders(200, 0);
	            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
	            
	            String query = he.getRequestURI().getQuery();
	            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
	            String selectedDepartment = queryParams.get("department");
	            String applianceType = queryParams.get("appliance");
	            // Select department
	            department = ApplianceDepartments.selectApplianceDepartment(selectedDepartment);
	            System.out.println(department);

	            // Select appliance
	            appliance = department.selectAppliance(applianceType);
	            System.out.println(appliance);

	            if (appliance != null) {
	                out.write(
	                    "<html>" +
	                    "<head> <title>View Appliance</title> " +
	                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
	                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
	                    "</head>" +
	                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
	                    "<a class=\"navbar-brand\" href=\"/\">Home Appliance Store</a>" +
	                    "<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\">" +
	                        "<span class=\"navbar-toggler-icon\"></span>" +
	                    "</button>" +
	                    "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
	                        "<ul class=\"navbar-nav\">" +
	                            "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/\">Home</a></li>" +
	                            "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
	                        "</ul>" +
	                    "</div>" +
	                    "</nav>" +
	                    "<body>" +
	                    "<div class=\"container\">" +
	                    "  <h1 class=\"mt-4\">View Appliance</h1>" +
	                    "  <form method=\"post\" action=\"/appliances/users/confirm\">" +
	                    "    <div class=\"form-group\">" +
	                    "    <div class=\"form-group\">" +
	                    "      <label for=\"description\">Description</label>" +
	                    "      <input type=\"text\" class=\"form-control\" id=\"description\" name=\"description\" value=\"" + appliance.getDescription() + "\" readonly>" +
	                    "    </div>" +
	                    "    <div class=\"form-group\">" +
	                    "      <label for=\"category\">Category</label>" +
	                    "      <input type=\"text\" class=\"form-control\" id=\"category\" name=\"category\" value=\"" + appliance.getCategory() + "\" readonly>" +
	                    "    </div>" +
	                    "    <div class=\"form-group\">" +
	                    "      <label for=\"sku\">SKU</label>" +
	                    "      <input type=\"text\" class=\"form-control\" id=\"sku\" name=\"sku\" value=\"" + appliance.getSku() + "\" readonly>" +
	                    "    </div>" +
	                    "    <div class=\"form-group\">" +
	                    "      <label for=\"price\">Price</label>" +
	                    "      <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"price\" name=\"price\" value=\"" + appliance.getPrice() + "\" readonly>" +
	                    "    </div>" +
	                    "    <button type=\"submit\" class=\"btn btn-success\">Add to basket</button>" +
	                    "  </form>" +
	                    "</div>" +
	                    "</body>" +
	                    "</html>");

	            } else {
	                out.write("<html><body><h1>Appliance Not Found</h1></body></html>");
	            }

	        } catch (Exception e) {
	            he.sendResponseHeaders(500, 0);
	            if (out != null) {
	                out.write("<html><body><h1>Internal Server Error</h1></body></html>");
	            }
	            e.printStackTrace();
	        } finally {
	            if (out != null) {
	                try {
	                    out.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    private void handlePost(HttpExchange he) throws IOException {
	        String response;

	        try (InputStream is = he.getRequestBody()) {
	            // Read the body of the request
	            String body = new String(is.readAllBytes());

	            // Select appliance
	            appliance = getApplianceFromStore(body);
	            System.out.println("Ready to add appliance: " + appliance);
	            
	            // Set user ID from session
	        	User user = getUserProfile(he);
	        	System.out.println("User profile is: " + user);

	        	System.out.println("User object: " + user);
	            System.out.println("Ready to add appliance ID no: " + appliance.getId());
	            // Add appliance
	            if (appliance != null && user != null) {
                	cart = new ShoppingCartItem(user, appliance);
                	
	                try {               	
						shoppingDao.addNewLineItem(cart, null);
						
						response = "Appliance added successfully!";
						
		                // Redirect to success page
		                he.getResponseHeaders().add("Location", "/success/");
		                he.sendResponseHeaders(302, -1); // 302 Found (redirect)
		                return;
					} catch (Exception e) {
						response = "Failed to add appliance to basket.";
						e.printStackTrace();
					}
	            } else {
	                response = "Appliance not found.";
	            }
	        } catch (Exception e) {
	            response = "Failed to add appliance: " + e.getMessage();
	            e.printStackTrace();
	        }

	        // Send response
	        he.sendResponseHeaders(200, response.getBytes().length);
	        try (OutputStream os = he.getResponseBody()) {
	            os.write(response.getBytes());
	        }
	 
	    }
	    
	    private Appliance getApplianceFromStore (String body) {
	    	Appliance appliance;
	    	
            // Parse the body into a map
            Map<String, String> formParams = WebUtil.requestStringToMap(body); 
            String selectedDepartment = formParams.get("category");
            String applianceType = formParams.get("description");

            // Select department
            department = ApplianceDepartments.selectApplianceDepartment(selectedDepartment.toLowerCase());
            System.out.println(department);
            
            //select appliance ID from database
            appliance = applianceDao.getApplianceByType(applianceType);
            
            return appliance;
	    }
	    
	    private User getUserProfile(HttpExchange he) {
	    	String sessionId = WebUtil.extractSessionId(he);
			Session session = sessionManager.getSession(sessionId);
        	
        	int userId = (int) session.getAttribute("userId");
        	
        	User user = userDao.getUser(userId);

			return user;
	    }

	}
