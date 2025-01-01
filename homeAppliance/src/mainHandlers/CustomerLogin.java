package mainHandlers;

import com.sun.net.httpserver.HttpHandler;

import util.WebUtil;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomerLogin implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        if ("GET".equalsIgnoreCase(he.getRequestMethod())) {
            // Display the login form
            String response = "<html>" +
                "<head><title>Customer Login</title></head>" +
                "<body>" +
                "<h1>Customer Login</h1>" +
                "<form action=\"/customerLogin\" method=\"POST\">" +
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
            String response = "Processing customer login...";
            
            he.sendResponseHeaders(200, response.length());
    		
    		Map<String, String> map = null;
			try {
				map = WebUtil.getResponseMap(he);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    		String username = map.get("username");
    		String password = map.get("password");
    		
    		System.out.println(username + " " + password);
    		
    		
        } else {
            he.sendResponseHeaders(405, -1); // Method not allowed
        }
    }
}
