package adminUserHandlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import sessionManagement.Session;
import sessionManagement.SessionManager;
import util.WebUtil;

/**
 * Handles the HTTP request to update a user's information in the system.
 * This handler processes the update form and updates the user's data in the database.
 * 
 * @author 24862664
 */
public class UpdateUserHandler implements HttpHandler {
    private UserDao userDao;
    private SessionManager sessionManager;

    /**
     * Constructor to initialize the handler with the provided UserDao and SessionManager.
     * 
     * @param userDao the UserDao object used to interact with the user data in the database.
     * @param sessionManager the SessionManager object used to manage user sessions.
     */
    public UpdateUserHandler(UserDao userDao, SessionManager sessionManager) {
        this.userDao = userDao;
        this.sessionManager = sessionManager;
    }

    /**
     * Handles the HTTP request to update the user data.
     * It processes the form data, validates the session, and updates the user information in the database.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data.
     * @throws IOException if an I/O error occurs during the response writing.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract session ID from the request and retrieve the session object
        String sessionId = WebUtil.extractSessionId(he);
        Session session = sessionManager.getSession(sessionId);
        
        // Check if the request method is POST
        if (!"POST".equalsIgnoreCase(he.getRequestMethod())) {
            he.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }
        
        // Check if the user is logged in (session is valid)
        if (session == null) {
            he.sendResponseHeaders(401, -1); // Not logged in
            return;
        }

        // Parse form data from the HTTP request
        Map<String, String> formData = WebUtil.getResponseMap(he);

        try {
            // Extract user data from the form data
            int customerId = Integer.parseInt(formData.get("customerId"));
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("first_name", formData.get("firstName"));
            updateFields.put("last_name", formData.get("lastName"));
            updateFields.put("username", formData.get("username"));
            updateFields.put("email_address", formData.get("emailAddress"));
            updateFields.put("telephone_num", formData.get("telephoneNum"));

            // Check if the business name is provided and add it to the update fields
            String businessName = formData.get("businessName");
            if (businessName != null && !businessName.isEmpty()) {
                updateFields.put("business_name", businessName);
            }

            // Update the user in the database using the userDAO method
            int rowsUpdated = userDao.updateById(customerId, "users", updateFields);

            // Prepare response based on the update result
            String response;
            if (rowsUpdated > 0) {
                response = "User updated successfully.";
                he.getResponseHeaders().add("Location", "/success");
                he.sendResponseHeaders(302, response.length()); // Redirect to success page
            } else {
                response = "Failed to update user.";
                he.sendResponseHeaders(500, response.length()); // Internal server error
            }
            he.getResponseBody().write(response.getBytes());

        } catch (Exception e) {
            // Handle any errors during the process and return an error message
            e.printStackTrace();
            String response = "Error processing the update.";
            he.sendResponseHeaders(500, response.length());
            he.getResponseBody().write(response.getBytes());
        } finally {
            // Close the response body after processing the request
            he.getResponseBody().close();
        }
    }
}
