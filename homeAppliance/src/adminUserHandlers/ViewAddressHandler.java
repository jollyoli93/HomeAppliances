package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import DAO.UserDao;
import users.Address;
import users.User;
import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the HTTP requests for viewing the address details of a user.
 * It retrieves the addresses associated with a user (both billing and shipping),
 * and displays them in a structured HTML page.
 * 
 * The page includes options to edit or delete each address.
 * 
 * @author 24862664
 */
public class ViewAddressHandler implements HttpHandler {
    private UserDao userDao;
    
    /**
     * Constructs a ViewAddressHandler with a specified UserDao.
     * 
     * @param userDao the DAO object for interacting with the user data
     */
    public ViewAddressHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Handles the HTTP request for fetching and displaying the address details of a user.
     * The request should contain the user ID as a query parameter. The handler retrieves
     * the user's data, then displays their billing and shipping addresses in an HTML page.
     * 
     * If the user ID is missing or invalid, or if an error occurs while fetching the data,
     * an appropriate error message will be displayed.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data
     * @throws IOException if an I/O error occurs during the response writing
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        
        try {
            // Extract the user ID from the query parameters
            String query = he.getRequestURI().getQuery();
            String userIdParam = extractUserId(query);

            if (userIdParam == null) {
                // Respond with an error if the user ID is missing
                he.sendResponseHeaders(400, 0); // Bad request
                out.write("<html><body><h1>Missing User ID</h1></body></html>");
                return;
            }

            // Retrieve the user from the database
            User user = userDao.getUser(Integer.parseInt(userIdParam));
            if (user == null) {
                // Respond with an error if the user is not found
                he.sendResponseHeaders(404, 0); // Not found
                out.write("<html><body><h1>User Not Found</h1></body></html>");
                return;
            }

            // Retrieve the user's addresses
            List<Address> userAddresses = userDao.getAddresses(user.getCustomerId());

            // Send a successful response and begin writing the HTML content
            he.sendResponseHeaders(200, 0);
            out.write(buildAddressPage(user, userAddresses));

        } catch (Exception e) {
            // If an error occurs, return an internal server error response
            he.sendResponseHeaders(500, 0);
            out.write("<html><body><h1>Error fetching address data</h1></body></html>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * Extracts the user ID from the query string.
     * 
     * @param query the query string from the URL
     * @return the user ID as a string, or null if not found
     */
    private String extractUserId(String query) {
        if (query != null) {
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                if (param.startsWith("id=")) {
                    return param.split("=")[1];
                }
            }
        }
        return null;
    }

    /**
     * Builds the HTML page displaying the user's address information.
     * The page includes separate tables for billing and shipping addresses,
     * with options to edit or delete each address.
     * 
     * @param user the user whose address details are being viewed
     * @param userAddresses the list of addresses associated with the user
     * @return the HTML content for the address page
     */
    private String buildAddressPage(User user, List<Address> userAddresses) {
        StringBuilder html = new StringBuilder();
        
        html.append(
            "<html>" +
                "<head><title>User Address Details</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\"></head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1 class=\"mt-4\">Address Details for: " + user.getUsername() + "</h1>" +
                "<div class=\"mb-3\">" +
                "<a href=\"/admin/users/view\" class=\"btn btn-primary\">Go back</a>" +
                "</div>"
        );

        // Generate Billing Addresses Table
        html.append(generateAddressTable("Billing Address", userAddresses, "billing"));

        // Generate Shipping Addresses Table
        html.append(generateAddressTable("Shipping Address", userAddresses, "shipping"));

        html.append("</div></body></html>");
        
        return html.toString();
    }

    /**
     * Generates an HTML table for a given address type (either billing or shipping).
     * The table displays address details, including options to edit or delete each address.
     * 
     * @param title the title of the address type (e.g., "Billing Address")
     * @param userAddresses the list of addresses to display
     * @param addressType the type of address to filter by (e.g., "billing" or "shipping")
     * @return the HTML table for the specified address type
     */
    private String generateAddressTable(String title, List<Address> userAddresses, String addressType) {
        StringBuilder tableHtml = new StringBuilder();
        
        tableHtml.append("<h2>" + title + "</h2>" +
            "<table class=\"table table-striped\">" +
            "<thead class=\"thead-dark\"><tr>" +
            "<th>Number</th>" +
            "<th>Street</th>" +
            "<th>City</th>" +
            "<th>Postcode</th>" +
            "<th>Country</th>" +
            "<th>Is Primary</th>" +
            "<th>Actions</th>" +
            "</tr></thead><tbody>");
        
        List<Address> filteredAddresses = userAddresses.stream()
            .filter(address -> address.getAddressType().equals(addressType))
            .collect(Collectors.toList());
        
        if (filteredAddresses.isEmpty()) {
            tableHtml.append("<tr><td colspan=\"7\">No " + addressType + " address found.</td></tr>");
        } else {
            for (Address address : filteredAddresses) {
                tableHtml.append("<tr>" +
                    "<td>" + address.getNumber() + "</td>" +
                    "<td>" + address.getStreet() + "</td>" +
                    "<td>" + address.getCity() + "</td>" +
                    "<td>" + address.getPostCode() + "</td>" +
                    "<td>" + address.getCountry() + "</td>" +
                    "<td>" + address.isPrimary() + "</td>" +
                    "<td class=\"d-flex\">" +
                    "<a href=\"/admin/users/edit-address?id=" + address.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-warning btn-sm mr-1\">Edit</a>" +
                    "<a href=\"/admin/users/delete-address?id=" + address.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-danger btn-sm mr-1\">Delete</a>" +
                    "</td>" +
                    "</tr>");
            }
        }
        
        tableHtml.append("</tbody></table>");
        
        return tableHtml.toString();
    }
}
