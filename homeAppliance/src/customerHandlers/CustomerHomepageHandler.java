package customerHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Handles the customer homepage view.
 * 
 * @author 24862664
 */
public class CustomerHomepageHandler implements HttpHandler {

    /**
     * Handles the HTTP request to display the customer homepage.
     * 
     * @param he the HTTP exchange
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Send HTTP response headers
        he.sendResponseHeaders(200, 0);

        // Write the HTML content to the response
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        out.write(
            "<html>" +
            "<head>" +
            "<title>Home Appliance Store</title>" +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
            "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
            "</head>" +
            "<body>" +
            "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
            "  <a class=\"navbar-brand\" href=\">Home Appliance Store</a>" +
            "  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" " +
            "    aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
            "    <span class=\"navbar-toggler-icon\"></span>" +
            "  </button>" +
            "  <div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
            "    <ul class=\"navbar-nav\">" +
            "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"\">Home</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"/#about\">About Us</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"/#contact\">Contact</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"/logout\">Log Out</a></li>" +  // Log Out button
            "    </ul>" +
            "  </div>" +
            "</nav>" +

            "<div class=\"jumbotron text-center bg-light mt-4\">" +
            "  <h1 class=\"display-4\">Welcome to the Home Appliance Store!</h1>" +
            "  <p class=\"lead\">Your one-stop shop for high-quality appliances for every need.</p>" +
            "</div>" +

            // user options
            "<div class=\"container text-center mt-4\">" +
            "  <a href=\"/appliances/add\" class=\"btn btn-primary btn-lg m-2\">View Appliances</a>" +
            "  <a href=\"/users/cart\" class=\"btn btn-info btn-lg m-2\">Shopping Cart</a>" +
            "  <a href=\"/users/edit\" class=\"btn btn-warning btn-lg m-2\">Edit Account Details</a>" +
            "</div>" +

            "<footer class=\"text-center mt-4\">" +
            "  <p>&copy; 2024 Home Appliance Store. All rights reserved.</p>" +
            "</footer>" +

            "</body>" +
            "</html>"
        );

        out.close();
    }
}
