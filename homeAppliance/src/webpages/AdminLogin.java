package webpages;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class AdminLogin implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if ("GET".equalsIgnoreCase(he.getRequestMethod())) {
            // Display the login form
            String response = "<html>" +
                "<head><title>Admin Login</title></head>" +
                "<body>" +
                "<h1>Admin Login</h1>" +
                "<form action=\"/adminLogin\" method=\"POST\">" +
                "  <label for=\"username\">Username:</label>" +
                "  <input type=\"text\" id=\"username\" name=\"username\"><br>" +
                "  <label for=\"password\">Password:</label>" +
                "  <input type=\"password\" id=\"password\" name=\"password\"><br>" +
                "  <button type=\"submit\">Login</button>" +
                "</form>" +
                "</body></html>";

            he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else if ("POST".equalsIgnoreCase(he.getRequestMethod())) {
            // Process login
            String response = "Processing admin login...";
            he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            he.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
