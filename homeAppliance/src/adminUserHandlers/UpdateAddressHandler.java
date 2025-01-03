package userHandlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import users.Address;
import users.BillingAddress;
import users.ShippingAddress;
import util.WebUtil;

public class UpdateAddressHandler implements HttpHandler {
    private UserDao userDao;

    public UpdateAddressHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        if (!"POST".equalsIgnoreCase(he.getRequestMethod())) {
            he.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }
        
        try {
            // Parse form data
            Map<String, String> formData = WebUtil.getResponseMap(he);

            // Extract and validate user ID
            int customerId = validateCustomerId(formData.get("customerId"));

            // Extract and validate address type
            String addressType = validateAddressType(formData.get("addressType"));

            // Create the address object
            Address address = createAddress(formData, addressType, customerId);

            // Update the address using the DAO method
            int rowsUpdated = userDao.updateUserAddress(customerId, address);

            sendResponse(he, rowsUpdated);

        } catch (IllegalArgumentException e) {
            // Handle invalid input
            sendErrorResponse(he, 400, "Invalid input: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            sendErrorResponse(he, 500, "Error processing the update.");
        } finally {
            he.getResponseBody().close();
        }
    }

    private int validateCustomerId(String customerIdStr) {
        try {
            return Integer.parseInt(customerIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID format");
        }
    }

    private String validateAddressType(String addressType) {
        if (!"shipping".equals(addressType) && !"billing".equals(addressType)) {
            throw new IllegalArgumentException("Invalid address type. Use 'shipping' or 'billing'.");
        }
        return addressType;
    }

    private Address createAddress(Map<String, String> formData, String addressType, int customerId) {
        String number = formData.get("number");
        String street = formData.get("street");
        String city = formData.get("city");
        String country = formData.get("country");
        String postCode = formData.get("postCode");
        boolean isPrimary = "on".equalsIgnoreCase(formData.get("isPrimary"));

        // Validate required fields
        validateRequiredFields(number, street, city, country, postCode);

        return addressType.equalsIgnoreCase("shipping") 
            ? new ShippingAddress(number, street, city, country, postCode, customerId, isPrimary)
            : new BillingAddress(number, street, city, country, postCode, customerId, isPrimary);
    }

    private void validateRequiredFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                throw new IllegalArgumentException("All address fields are required");
            }
        }
    }

    private void sendResponse(HttpExchange he, int rowsUpdated) throws IOException {
        if (rowsUpdated > 0) {
            String response = "Address updated successfully.";
            he.getResponseHeaders().add("Location", "/admin/success");
            he.sendResponseHeaders(302, response.length());
            he.getResponseBody().write(response.getBytes());
        } else {
            sendErrorResponse(he, 500, "Failed to update address.");
        }
    }

    private void sendErrorResponse(HttpExchange he, int statusCode, String message) throws IOException {
        he.sendResponseHeaders(statusCode, message.length());
        he.getResponseBody().write(message.getBytes());
    }
}