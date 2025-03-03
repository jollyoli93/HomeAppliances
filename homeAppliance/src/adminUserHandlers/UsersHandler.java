package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * Handles the HTTP requests for displaying the user administration dashboard.
 * This handler generates an HTML page with links to view users, view admin accounts,
 * and create new users.
 * 
 * @author 24862664
 */
public class UsersHandler implements HttpHandler {

    /**
     * Handles the HTTP request to display the user admin dashboard page.
     * It generates an HTML page that provides links to view registered customers,
     * view admin accounts, and create new user accounts.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data.
     * @throws IOException if an I/O error occurs during the response writing.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Send HTTP response headers with a 200 OK status and no content length
        he.sendResponseHeaders(200, 0);

        // Set up output stream to write the response body
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        
        // Write the HTML content for the user admin dashboard
        out.write(
            "<html>" +
                "<head>" +
                    "<title>User Dashboard</title>" +
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
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/logout\">Logout</a></li>" +
                            "</ul>" +
                        "</div>" +
                    "</nav>" +
                    "<div class=\"container mt-4\">" +
                        "<h1 class=\"mb-4\">User Admin Dashboard</h1>" +
                        "<div class=\"list-group\">" +
                            "<a href=\"/admin/users/view\" class=\"list-group-item list-group-item-action\">" +
                                "<h5 class=\"mb-1\">View Customers</h5>" +
                                "<small>View all registered users.</small>" +
                            "</a>" +
                            "<a href=\"/admin/users/view-admin\" class=\"list-group-item list-group-item-action\">" +
	                            "<h5 class=\"mb-1\">View Admin</h5>" +
	                            "<small>View admin accounts</small>" +
	                        "</a>" +
                            "<a href=\"/admin/users/add-select\" class=\"list-group-item list-group-item-action\">" +
                            "<h5 class=\"mb-1\">Create New User</h5>" +
                            "<small>Add a new account</small>" +
                        "</a>" +
                        "</div>" +
                    "</div>" +
                    "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"></script>" +
                    "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\"></script>" +
                "</body>" +
            "</html>"
        );
        
        // Close the BufferedWriter to complete the response
        out.close();
    }
}
