package mainHandlers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;

import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class CustomerHomepage implements HttpHandler {
  public void handle(HttpExchange he) throws IOException {
    String response = "Admin Page";
    he.sendResponseHeaders(200, 0);
    BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(he.getResponseBody())); 
    
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
            "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"#\">Home</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"#about\">About Us</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"#products\">Products</a></li>" +
            "      <li class=\"nav-item\"><a class=\"nav-link\" href=\"#contact\">Contact</a></li>" +
            "    </ul>" +
            "  </div>" +
            "</nav>" +
            "<div class=\"jumbotron text-center bg-light mt-4\">" +
            "  <h1 class=\"display-4\">Welcome to the Home Appliance Store!</h1>" +
            "  <p class=\"lead\">Your one-stop shop for high-quality appliances for every need.</p>" +
            "  <a class=\"btn btn-primary btn-lg\" href=\"#products\" role=\"button\">View Products</a>" +
            "</div>" +
            "<div class=\"container mt-4\">" +
            "  <section id=\"about\">" +
            "    <h2>About Us</h2>" +
            "    <p>We provide a wide variety of appliances, ranging from entertainment systems to home cleaning tools. " +
            "       Our mission is to make your life easier with innovative and reliable appliances.</p>" +
            "  </section>" +
            "  <hr>" +
            "  <section id=\"products\">" +
            "    <h2>Our Products</h2>" +
            "    <p>Browse our collection of top-notch appliances that cater to every need in your home.</p>" +
            "    <ul>" +
            "      <li>Entertainment Systems</li>" +
            "      <li>Home Cleaning Appliances</li>" +
            "      <li>Kitchen Essentials</li>" +
            "      <li>And many more!</li>" +
            "    </ul>" +
            "  </section>" +
            "  <hr>" +
            "  <section id=\"contact\">" +
            "    <h2>Contact Us</h2>" +
            "    <p>If you have any questions, feel free to reach out to us at:</p>" +
            "    <ul>" +
            "      <li>Email: info@homeappliancestore.com</li>" +
            "      <li>Phone: +1-234-567-890</li>" +
            "    </ul>" +
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