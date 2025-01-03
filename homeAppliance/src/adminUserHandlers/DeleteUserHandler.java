package adminUserHandlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import util.WebUtil;

public class DeleteUserHandler implements HttpHandler {
    private UserDao userDao;
    
    public DeleteUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        try {
            // Get form data from the request
            Map<String, String> formData = WebUtil.getResponseMap(he);
            
            // Parse the user ID from the form data
            int id = Integer.parseInt(formData.get("id"));
            
            // Call DAO method to delete the user
            int result = userDao.deleteUserById(id);
            
            // Prepare success response message
            String response = "Delete complete. Rows affected: " + result;

            // Redirect to success page
            he.getResponseHeaders().add("Location", "/admin/success");
            he.sendResponseHeaders(302, response.length());
            he.getResponseBody().write(response.getBytes());
            he.getResponseBody().close();

        } catch (NumberFormatException e) {
            // Handle the case where the ID is not a valid integer
            String errorResponse = "Invalid user ID provided.";
            he.sendResponseHeaders(400, errorResponse.length()); // 400 Bad Request
            he.getResponseBody().write(errorResponse.getBytes());
            he.getResponseBody().close();
            e.printStackTrace(); // Log the error for debugging purposes
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            String errorResponse = "An error occurred while trying to delete the user.";
            he.sendResponseHeaders(500, errorResponse.length()); // 500 Internal Server Error
            he.getResponseBody().write(errorResponse.getBytes());
            he.getResponseBody().close();
            e.printStackTrace(); // Log the error for debugging purposes
        }
    }
}

