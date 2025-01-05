package adminUserHandlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import users.AdminUser;
import users.BusinessUser;
import users.CustomerUser;
import users.User;
import util.WebUtil;

/**
 * Handles HTTP requests for creating a new user.
 * 
 * <p>This handler processes form data, validates the user type, and creates a new user in the system 
 * based on the provided information. Supports admin, customer, and business users.</p>
 * 
 * @author 24862664
 */
public class ConfirmCreateUserHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructs a new handler with the specified {@link UserDao}.
     * 
     * @param userDao the DAO used to manage user data
     */
    public ConfirmCreateUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }
    
    /**
     * Handles incoming HTTP requests and creates a new user based on the form data.
     * 
     * @param he the {@link HttpExchange} object containing the HTTP request and response
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        
        try {
            Map<String, String> params = WebUtil.getResponseMap(he);

            String type = params.get("type");
            String firstName = params.get("firstName");
            String lastName = params.get("lastName");
            String username = params.get("username");
            String emailAddress = params.get("emailAddress");
            String telephoneNum = params.get("telephoneNum");
            String businessName = params.get("businessName");
            String password = params.get("password");

            if (type == null || (!type.equals("admin") && !type.equals("customer") && !type.equals("business"))) {
                he.sendResponseHeaders(400, 0);
                out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
                out.write("<html><body><h1>Invalid User Type</h1></body></html>");
                return;
            }

            User user;
            switch (type) {
                case "admin":
                    user = new AdminUser(firstName, lastName, emailAddress, username, password);
                    userDao.addNewAdmin(user);
                    break;
                case "customer":
                    user = new CustomerUser(firstName, lastName, emailAddress, username, password, telephoneNum);
                    userDao.addNewCustomer(user, null); 
                    break;
                case "business":
                    user = new BusinessUser(firstName, lastName, emailAddress, username, password, telephoneNum, businessName);
                    userDao.addNewBusiness(user, null);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid user type");
            }

            he.getResponseHeaders().set("Location", "/success");
            he.sendResponseHeaders(302, -1);

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
            out.write("<html><body><h1>Internal Server Error</h1></body></html>");
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
