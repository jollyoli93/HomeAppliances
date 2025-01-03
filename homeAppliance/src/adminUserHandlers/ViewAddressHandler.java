package adminUserHandlers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import DAO.UserDao;
import users.Address;
import users.User;
import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class ViewAddressHandler implements HttpHandler {
    private UserDao userDao;
    
    public ViewAddressHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        try {
            // Extract user ID from the query parameter
            String query = he.getRequestURI().getQuery();
            String userIdParam = null;
            if (query != null) {
                String[] queryParams = query.split("&");
                for (String param : queryParams) {
                    if (param.startsWith("id=")) {
                        userIdParam = param.split("=")[1];
                        break;
                    }
                }
            }

            if (userIdParam == null) {
                he.sendResponseHeaders(400, 0); // Bad request if no user ID is provided
                out.write("<html><body><h1>Missing User ID</h1></body></html>");
                return;
            }

            // Retrieve the user and their addresses
            User user = userDao.getUser(Integer.parseInt(userIdParam));
            if (user == null) {
                he.sendResponseHeaders(404, 0); // Not found if the user does not exist
                out.write("<html><body><h1>User Not Found</h1></body></html>");
                return;
            }

            List<Address> userAddresses = userDao.getAddresses(user.getCustomerId());
        	System.out.println("DEBUG: " + userAddresses);

            he.sendResponseHeaders(200, 0);
            out.write(
                "<html>" +
                "<head><title>User Address Details</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1 class=\"mt-4\">Address Details for: " + user.getUsername() + "</h1>" +
                "<div class=\"mb-3\">" +
                "<a href=\"/admin/users/view\" class=\"btn btn-primary\">Go back</a>" +
                "</div>"
            );

            // Billing Addresses Table
            out.write(
                "<h2>Billing Address</h2>" +
                "<table class=\"table table-striped\">" +
                "<thead class=\"thead-dark\">" +
                "<tr>" +
                "<th>Number</th>" +
                "<th>Street</th>" +
                "<th>City</th>" +
                "<th>Postcode</th>" +
                "<th>Country</th>" +
                "<th>Is Primary</th>" +
                "<th>Actions</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>"
            );

            boolean billingFound = false;
            for (Address address : userAddresses) {
            	System.out.println("DEBUG: " + address);
                if (address.getAddressType().equals("billing")) {
                    billingFound = true;
                    out.write(
                        "<tr>" +
	                        "<td>" + address.getNumber() + "</td>" +
	                        "<td>" + address.getStreet() + "</td>" +
	                        "<td>" + address.getCity() + "</td>" +
	                        "<td>" + address.getPostCode() + "</td>" +
	                        "<td>" + address.getCountry() + "</td>" +
	                        "<td>" + address.isPrimary() + "</td>" +
	                        "<td class=\"d-flex\">" +
	                        "<a href=\"/admin/users/edit-address?id=" + user.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-warning btn-sm mr-1\">Edit</a>" +
	                        "<a href=\"/admin/users/delete-address?id=" + user.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-danger btn-sm mr-1\">Delete</a>" +
	                        "</td>" +
                        "</tr>"
                    );
                }
            }

            if (!billingFound) {
                out.write("<tr><td colspan=\"2\">No billing address found.</td></tr>");
            }

            out.write(
                "</tbody>" +
                "</table>"
            );

            // Shipping Addresses Table
            out.write(
                "<h2>Shipping Address</h2>" +
                "<table class=\"table table-striped\">" +
                "<thead class=\"thead-dark\">" +
                "<tr>" +
	                "<th>Number</th>" +
	                "<th>Street</th>" +
	                "<th>City</th>" +
	                "<th>Postcode</th>" +
	                "<th>Country</th>" +
	                "<th>Is Primary</th>" +
	                "<th>Actions</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>"
            );

            boolean shippingFound = false;
            for (Address address : userAddresses) {
            	System.out.println("DEBUG: " + address);

                if (address.getAddressType().equals("shipping")) {
                    shippingFound = true;
                    out.write(
                        "<tr>" +
	                        "<td>" + address.getNumber() + "</td>" +
	                        "<td>" + address.getStreet() + "</td>" +
	                        "<td>" + address.getCity() + "</td>" +
	                        "<td>" + address.getPostCode() + "</td>" +
	                        "<td>" + address.getCountry() + "</td>" +
	                        "<td>" + address.isPrimary() + "</td>" +
	                        "<td class=\"d-flex\">" +
	                        "<a href=\"/admin/users/edit-address?id=" + user.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-warning btn-sm mr-1\">Edit</a>" +
	                        "<a href=\"/admin/users/delete-address?id=" + user.getCustomerId() + "&address_id=" + address.getAddress_id() + "\" class=\"btn btn-danger btn-sm mr-1\">Delete</a>" +
	                        "</td>" +
                        "</tr>"
                    );
                }
            }

            if (!shippingFound) {
                out.write("<tr><td colspan=\"2\">No shipping address found.</td></tr>");
            }

            out.write(
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0);
            out.write("<html><body><h1>Error fetching address data</h1></body></html>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
