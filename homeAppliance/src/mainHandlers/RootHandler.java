package mainHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * A handler for the root path of the Home Appliance Store website serving the login functionality. 
 * It generates and returns an HTML page as the response for HTTP requests.
 * @author 24862664
 */
public class RootHandler implements HttpHandler {

    /**
     * Handles the HTTP request for the root homepage by sending a response with the Home Appliance Store's homepage HTML.
     *
     * @param he The HTTP exchange that represents the request-response interaction.
     * @throws IOException If an I/O error occurs while writing the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Send HTTP response headers indicating a successful request (200 OK)
        he.sendResponseHeaders(200, 0);

        // Create a BufferedWriter to write the HTML response body
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        // Write the HTML content to the response body
        out.write(
            "<html>" +
            "<head>" +
                "<title>Home Appliance Store</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "<style>" +
                    ".content-wrapper { min-height: calc(100vh - 60px); }" +
                    "footer { height: 60px; margin-top: auto; }" +
                "</style>" +
            "</head>" +
            "<body>" +
                "<div class=\"content-wrapper\">" +
                    // Navigation Bar
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                        "<a class=\"navbar-brand\" href=\"#\">Home Appliance Store</a>" +
                        "<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" " +
                                "aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                            "<span class=\"navbar-toggler-icon\"></span>" +
                        "</button>" +
                        "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                            "<ul class=\"navbar-nav\">" +
                                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"\">Home</a></li>" +
                                "<li class=\"nav-item\"><a class=\"nav-link\" href=\"#about\">About Us</a></li>" +
                                "<li class=\"nav-item\"><a class=\"nav-link\" href=\"#contact\">Contact</a></li>" +
                            "</ul>" +
                        "</div>" +
                    "</nav>" +

                    // Hero Section
                    "<div class=\"jumbotron text-center bg-light mt-4\">" +
                        "<h1 class=\"display-4\">Welcome to the Home Appliance Store!</h1>" +
                        "<p class=\"lead\">Your one-stop shop for high-quality appliances for every need.</p>" +
                    "</div>" +

                    // Account Creation Buttons
                    "<div class=\"container text-center mb-4\">" +
                        "<a href=\"/users/add?type=customer\" class=\"btn btn-success btn-lg m-2\">Create New Customer Account</a>" +
                        "<a href=\"users/add?type=business\" class=\"btn btn-warning btn-lg m-2\">Create New Business Account</a>" +
                    "</div>" +

                    // Login Section
                    "<div class=\"container mt-4\">" +
                        "<div class=\"row justify-content-center\">" +
                            "<div class=\"col-md-6\">" +
                                "<h3>Login</h3>" +
                                "<form action=\"/login\" method=\"POST\">" +
                                    "<div class=\"form-group\">" +
                                        "<label for=\"customerUsername\">Username</label>" +
                                        "<input type=\"text\" class=\"form-control\" id=\"customerUsername\" name=\"username\" " +
                                               "placeholder=\"Enter username\" required>" +
                                    "</div>" +
                                    "<div class=\"form-group\">" +
                                        "<label for=\"customerPassword\">Password</label>" +
                                        "<input type=\"password\" class=\"form-control\" id=\"customerPassword\" name=\"password\" " +
                                               "placeholder=\"Enter password\" required>" +
                                    "</div>" +
                                    "<button type=\"submit\" class=\"btn btn-primary\">Login</button>" +
                                "</form>" +
                            "</div>" +
                        "</div>" +
                    "</div>" +
                "</div>" +

                // Footer
                "<footer class=\"bg-light py-3\">" +
                    "<div class=\"container\">" +
                        "<p class=\"text-center mb-0\">&copy; 2024 Home Appliance Store. All rights reserved.</p>" +
                    "</div>" +
                "</footer>" +

                // Bootstrap JS requirements
                "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\"></script>" +
                "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js\"></script>" +
            "</body>" +
            "</html>"
        );

        // Close the output stream after writing the response
        out.close();
    }
}
