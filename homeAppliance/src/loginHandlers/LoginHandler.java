package loginHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import DAO.UserDao;
import users.User;
import util.WebUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    private final UserDao userDao;
    
    public LoginHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        try {
            switch (exchange.getRequestMethod().toUpperCase()) {
                case "GET":
                    handleGet(exchange);
                    break;
                case "POST":
                    handlePost(exchange);
                    break;
                default:
                    exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        } catch (IOException e) {
            response = "An error occurred: " + e.getMessage();
            sendResponse(exchange, 500, response);
            throw e;
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String loginForm = """
            <html>
            <head>
                <title>Customer Login</title>
                <style>
                    .container { margin: 20px; }
                    .form-group { margin-bottom: 10px; }
                    label { display: inline-block; width: 100px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <h1>Customer Login</h1>
                    <form action="/customerLogin" method="POST">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" required>
                        </div>
                        <button type="submit">Login</button>
                    </form>
                </div>
            </body>
            </html>
            """;
        
        sendResponse(exchange, 200, loginForm);
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Map<String, String> formData = WebUtil.getResponseMap(exchange);
        String username = formData.get("username");
        String password = formData.get("password");

        if (username == null || password == null) {
            sendResponse(exchange, 400, "Username and password are required");
            return;
        }

        User user = validateUser(username, password);
        if (user == null) {
            sendResponse(exchange, 401, "Invalid login credentials");
            return;
        }

        switch (user.getRole().toLowerCase()) {
            case "admin":
                handleAdmin(exchange, user);
                break;
            case "customer":
                handleCustomer(exchange, user);
                break;
            case "business":
                handleCustomer(exchange, user);
                break;
            default:
                sendResponse(exchange, 403, "Invalid user role");
        }
    }

    private User validateUser(String username, String password) {
        try {
            int userId = userDao.getUserIdFromUsername(username);
            User user = userDao.getUser(userId);
            
            System.out.println(username + " " + password);
            System.out.println("Hashed input: " + user.hashPassword(password));
            System.out.println("Password from db: " + user.getPassword());
            
            return (user != null && user.validatePassword(password)) ? user : null;
        } catch (Exception e) {
            System.err.println("Error validating user: " + e.getMessage());
            return null;
        }
    }

    private void handleCustomer(HttpExchange exchange, User user) throws IOException {
        exchange.getResponseHeaders().set("Location", "/home");
        exchange.sendResponseHeaders(302, -1);
    }

    private void handleAdmin(HttpExchange exchange, User user) throws IOException {
        exchange.getResponseHeaders().set("Location", "/admin");
        exchange.sendResponseHeaders(302, -1);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}