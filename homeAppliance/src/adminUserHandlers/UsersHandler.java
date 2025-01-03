package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class UsersHandler implements HttpHandler {
    public void handle(HttpExchange he) throws IOException {
        he.sendResponseHeaders(200, 0);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        
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
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/\">Home</a></li>" +
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
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
        
        out.close();
    }
}