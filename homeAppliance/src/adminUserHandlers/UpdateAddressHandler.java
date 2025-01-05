package adminUserHandlers;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import users.Address;
import users.BillingAddress;
import users.ShippingAddress;
import util.WebUtil;

/**
 * Handles the HTTP request to update the user's address (either shipping or billing).
 * This handler processes the address update based on the provided customer ID and address type.
 * 
 * @author 24862664
 */
public class UpdateAddressHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructor to initialize the handler with the provided UserDao.
     * 
     * @param userDao the UserDao object used to update the user addresses.
     */
    public UpdateAddressHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Handles the HTTP request to update a user's address.
     * It processes the form data, validates the customer ID and address type,
     * creates the appropriate address object, and then updates the address in the database.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data.
     * @throws IOException if an I/O error occurs during the response writing.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        if (!"POST".equalsIgnoreCase(he.getRequestMethod())) {
            he.sendResponseHeaders(405, -1); // Method Not Allowed
            return;
        }
        
        try {
            // Parse form data from the HTTP request
            Map<String, String> formData = WebUtil.getResponseMap(he);

            // Extract and validate customer ID
            int customerId = validateCustomerId(formData.get("customerId"));

            // Extract and validate address type (shipping or billing)
            String addressType = validateAddressType(formData.get("addressType"));

            // Create the address object based on the provided data
            Address address = createAddress(formData, addressType, customerId);

            // Update the user's address in the database using the UserDao
            int rowsUpdated = userDao.updateUserAddress(customerId, address);

            // Send the response based on whether the update was successful or not
            sendResponse(he, rowsUpdated);

        } catch (IllegalArgumentException e) {
            // Handle invalid input (e.g., invalid customer ID or address type)
            sendErrorResponse(he, 400, "Invalid input: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions (e.g., database errors)
            e.printStackTrace();
            sendErrorResponse(he, 500, "Error processing the update.");
        } finally {
            he.getResponseBody().close();
        }
    }

    /**
     * Validates the customer ID by attempting to parse it as an integer.
     * 
     * @param customerIdStr the customer ID as a string.
     * @return the validated customer ID as an integer.
     * @throws IllegalArgumentException if the customer ID is invalid.
     */
    private int validateCustomerId(String customerIdStr) {
        try {
            return Integer.parseInt(customerIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid customer ID format");
        }
    }

    /**
     * Validates the address type to ensure it is either "shipping" or "billing".
     * 
     * @param addressType the address type to validate.
     * @return the validated address type.
     * @throws IllegalArgumentException if the address type is invalid.
     */
    private String validateAddressType(String addressType) {
        if (!"shipping".equals(addressType) && !"billing".equals(addressType)) {
            throw new IllegalArgumentException("Invalid address type. Use 'shipping' or 'billing'.");
        }
        return addressType;
    }

    /**
     * Creates the address object based on the form data and address type.
     * 
     * @param formData the map containing the form data (address fields).
     * @param addressType the type of address (either "shipping" or "billing").
     * @param customerId the customer ID to associate with the address.
     * @return the created Address object (either ShippingAddress or BillingAddress).
     */
    private Address createAddress(Map<String, String> formData, String addressType, int customerId) {
        String number = formData.get("number");
        String street = formData.get("street");
        String city = formData.get("city");
        String country = formData.get("country");
        String postCode = formData.get("postCode");
        boolean isPrimary = "on".equalsIgnoreCase(formData.get("isPrimary"));

        // Validate required fields are not empty or null
        validateRequiredFields(number, street, city, country, postCode);

        // Create and return the appropriate address object based on address type
        return addressType.equalsIgnoreCase("shipping") 
            ? new ShippingAddress(number, street, city, country, postCode, customerId, isPrimary)
            : new BillingAddress(number, street, city, country, postCode, customerId, isPrimary);
    }

    /**
     * Validates that all required address fields are provided and not empty.
     * 
     * @param fields the address fields to validate.
     * @throws IllegalArgumentException if any required field is missing or empty.
     */
    private void validateRequiredFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                throw new IllegalArgumentException("All address fields are required");
            }
        }
    }

    /**
     * Sends the HTTP response after the address update attempt.
     * If the update is successful, it sends a redirect to the success page.
     * If the update fails, it sends an error message.
     * 
     * @param he the {@link HttpExchange} object used to send the response.
     * @param rowsUpdated the number of rows affected by the update (indicating success or failure).
     * @throws IOException if an I/O error occurs during the response writing.
     */
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

    /**
     * Sends an error response with the specified status code and error message.
     * 
     * @param he the {@link HttpExchange} object used to send the response.
     * @param statusCode the HTTP status code to send.
     * @param message the error message to include in the response.
     * @throws IOException if an I/O error occurs during the response writing.
     */
    private void sendErrorResponse(HttpExchange he, int statusCode, String message) throws IOException {
        he.sendResponseHeaders(statusCode, message.length());
        he.getResponseBody().write(message.getBytes());
    }
}
