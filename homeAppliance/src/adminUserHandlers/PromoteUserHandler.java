package adminUserHandlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import util.WebUtil;

/**
 * Handles HTTP requests for promoting or demoting a user to/from an admin.
 * 
 * @author 24862664
 */
public class PromoteUserHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructs a handler for promoting a user to admin or demoting them.
     * 
     * @param userDao the {@link UserDao} instance used to access user data
     */
    public PromoteUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Processes the HTTP request to promote or demote a user to/from admin.
     * 
     * @param he the {@link HttpExchange} object containing the request and response
     * @throws IOException if an I/O error occurs during the response writing
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        if (!"POST".equalsIgnoreCase(he.getRequestMethod())) {
            String response = "Method not allowed.";
            he.sendResponseHeaders(405, response.length());
            he.getResponseBody().write(response.getBytes());
            he.getResponseBody().close();
            return;
        }

        try {
            Map<String, String> formData = WebUtil.getResponseMap(he);
            int id = Integer.parseInt(formData.get("id"));
            int result;
            
            if (userDao.isUserAdmin(id)) {
                result = userDao.removeAdminById(id);
            } else {
                result = userDao.giveAdminStatus(id);
            }

            String response;
            if (result > 0) {
                response = "Promote to admin. Rows affected: " + result;
                he.getResponseHeaders().add("Location", "/admin/success");
                he.sendResponseHeaders(302, response.length());
            } else {
                response = "Failed to promote the user to admin.";
                he.sendResponseHeaders(400, response.length());
            }
            he.getResponseBody().write(response.getBytes());
        } catch (NumberFormatException | NullPointerException e) {
            String response = "Invalid user ID provided.";
            he.sendResponseHeaders(400, response.length());
            he.getResponseBody().write(response.getBytes());
        } catch (Exception e) {
            String response = "An error occurred while promoting the user.";
            he.sendResponseHeaders(500, response.length());
            he.getResponseBody().write(response.getBytes());
        } finally {
            he.getResponseBody().close();
        }
    }
}
