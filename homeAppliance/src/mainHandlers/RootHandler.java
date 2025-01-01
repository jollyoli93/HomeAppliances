package mainHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedWriter;
import java.io.IOException;

public class RootHandler implements HttpHandler {

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
                "  <a class=\"navbar-brand\" href=\"#\">Home Appliance Store</a>" +
                "  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" " +
                "    aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                "    <span class=\"navbar-toggler-icon\"></span>" +
                "  </button>" +
                "  <div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                "    <ul class=\"navbar-nav\">" +
                "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"\">Home</a></li>" +
                "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"#about\">About Us</a></li>" +
                "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"#contact\">Contact</a></li>" +
                "    </ul>" +
                "  </div>" +
                "</nav>" +
                "<div class=\"jumbotron text-center bg-light mt-4\">" +
                "  <h1 class=\"display-4\">Welcome to the Home Appliance Store!</h1>" +
                "  <p class=\"lead\">Your one-stop shop for high-quality appliances for every need.</p>" +
                "</div>" +
                "<div class=\"container mt-4\">" +
                "  <section id=\"login\">" +
                "    <h2>Login</h2>" +
                "    <div class=\"row\">" +
                "      <div class=\"col-md-6\">" +
                
                //customer login
                "        <h3>Customer Login</h3>" +
                "        <form action=\"/customerLogin\" method=\"POST\">" +
                "          <div class=\"form-group\">" +
                "            <label for=\"customerUsername\">Username</label>" +
                "            <input type=\"text\" class=\"form-control\" id=\"customerUsername\" name=\"username\" placeholder=\"Enter username\">" +
                "          </div>" +
                "          <div class=\"form-group\">" +
                "            <label for=\"customerPassword\">Password</label>" +
                "            <input type=\"password\" class=\"form-control\" id=\"customerPassword\" name=\"password\" placeholder=\"Enter password\">" +
                "          </div>" +
                "          <button type=\"submit\" class=\"btn btn-primary\">Login</button>" +
                "        </form>" +
                "      </div>" +
                "      <div class=\"col-md-6\">" +
                
                //admin login
                "        <h3>Administrator Login</h3>" +
                "        <form action=\"/adminLogin\" method=\"POST\">" +
                "          <div class=\"form-group\">" +
                "            <label for=\"adminUsername\">Username</label>" +
                "            <input type=\"text\" class=\"form-control\" id=\"adminUsername\" name=\"username\" placeholder=\"Enter username\">" +
                "          </div>" +
                "          <div class=\"form-group\">" +
                "            <label for=\"adminPassword\">Password</label>" +
                "            <input type=\"password\" class=\"form-control\" id=\"adminPassword\" name=\"password\" placeholder=\"Enter password\">" +
                "          </div>" +
                "          <button type=\"submit\" class=\"btn btn-danger\">Login</button>" +
                "        </form>" +
                "      </div>" +
                "    </div>" +
                "  </section>" +
                "</div>" +
                "<footer class=\"text-center mt-4\">" +
                "  <p>&copy; 2024 Home Appliance Store. All rights reserved.</p>" +
                "</footer>" +
                "</body>" +
            "</html>");

        out.close();
    }
}
