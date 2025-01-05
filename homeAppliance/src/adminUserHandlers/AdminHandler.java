package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles HTTP requests for the admin dashboard page.
 * 
 * <p>This handler serves the HTML content for the Admin Dashboard, which includes navigation links 
 * to add new appliances, view all appliances, and manage users. It uses Bootstrap for styling.</p>
 * 
 * @author 24862664
 */
public class AdminHandler implements HttpHandler {

    /**
     * Handles incoming HTTP requests and sends the Admin Dashboard page as the response.
     * 
     * <p>The response is an HTML page styled with Bootstrap that includes links for admin actions:
     * <ul>
     *     <li>Add New Appliance</li>
     *     <li>View All Appliances</li>
     *     <li>Manage Users</li>
     * </ul>
     * </p>
     * 
     * @param he the {@link HttpExchange} object containing the HTTP request and response data
     * @throws IOException if an I/O error occurs during the response handling
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Sending HTTP response headers
        he.sendResponseHeaders(200, 0);

        // Writing the HTML content to the response body
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        out.write(
            "<html>" +
                "<head>" +
                    "<title>Admin Dashboard</title>" +
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
                "</head>" +
                "<body>" +
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                        "<a class=\"navbar-brand\" href=\"/\">Home Appliance Store</a>" +
                        "<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\">" +
                            "<span class=\"navbar-toggler-icon\"></span>" +
                        "</button>" +
                        "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                            "<ul class=\"navbar-nav\">" +
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/logout\">Logout</a></li>" +
                            "</ul>" +
                        "</div>" +
                    "</nav>" +
                    "<div class=\"container mt-4\">" +
                        "<h1 class=\"mb-4\">Admin Dashboard</h1>" +
                        "<div class=\"list-group\">" +
                            "<a href=\"/appliances/add\" class=\"list-group-item list-group-item-action\">" +
                                "<h5 class=\"mb-1\">Add New Appliance</h5>" +
                                "<small>Create a new appliance listing</small>" +
                            "</a>" +
                            "<a href=\"/admin/appliances\" class=\"list-group-item list-group-item-action\">" +
                                "<h5 class=\"mb-1\">View All Appliances</h5>" +
                                "<small>Manage existing appliances</small>" +
                            "</a>" +
                            "<a href=\"/admin/users\" class=\"list-group-item list-group-item-action\">" +
                            "<h5 class=\"mb-1\">Manage Users</h5>" +
                            "<small>Manage customer accounts</small>" +
                        "</a>" +
                        "</div>" +
                    "</div>" +
                    "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"></script>" +
                    "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\"></script>" +
                "</body>" +
            "</html>"
        );

        // Closing the writer to finish the response
        out.close();
    }
}
