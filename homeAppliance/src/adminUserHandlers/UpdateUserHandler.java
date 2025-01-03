package userHandlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import util.WebUtil;

public class UpdateUserHandler implements HttpHandler {
    private UserDao userDao;

    public UpdateUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (!"POST".equalsIgnoreCase(he.getRequestMethod())) {
            he.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }

        // Parse form data
        Map<String, String> formData = WebUtil.getResponseMap(he);

        try {
            // Extract user data from form
            int customerId = Integer.parseInt(formData.get("customerId"));
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("first_name", formData.get("firstName"));
            updateFields.put("last_name", formData.get("lastName"));
            updateFields.put("username", formData.get("username"));
            updateFields.put("email_address", formData.get("emailAddress"));
            updateFields.put("telephone_num", formData.get("telephoneNum"));

            String businessName = formData.get("businessName");
            if (businessName != null && !businessName.isEmpty()) {
                updateFields.put("business_name", businessName);
            }

            // Update the user in the database using the userDAO method
            int rowsUpdated = userDao.updateById(customerId, "users", updateFields);

            // Prepare response
            String response;
            if (rowsUpdated > 0) {
                response = "User updated successfully.";
                he.getResponseHeaders().add("Location", "/admin/success");
                he.sendResponseHeaders(302, response.length());
            } else {
                response = "Failed to update user.";
                he.sendResponseHeaders(500, response.length());
            }
            he.getResponseBody().write(response.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
            String response = "Error processing the update.";
            he.sendResponseHeaders(500, response.length());
            he.getResponseBody().write(response.getBytes());
        } finally {
            he.getResponseBody().close();
        }
    }
}
