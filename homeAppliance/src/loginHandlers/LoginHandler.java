package loginHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import DAO.UserDao;
import sessionManagement.SessionManager;
import sessionManagement.Session;
import users.User;
import util.WebUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    private final UserDao userDao;
    private final SessionManager sessionManager;

    public LoginHandler(UserDao userDao, SessionManager sessionManager) {
        this.userDao = userDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        String response;
        try {
            switch (he.getRequestMethod().toUpperCase()) {
                case "GET":
                    handleGet(he);
                    break;
                case "POST":
                    handlePost(he);
                    break;
                default:
                    he.sendResponseHeaders(405, -1); // Method not allowed
            }
        } catch (IOException e) {
            response = "An error occurred: " + e.getMessage();
            sendResponse(he, 500, response);
            throw e;
        }
    }

    private void handleGet(HttpExchange he) throws IOException {
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

        sendResponse(he, 200, loginForm);
    }

    private void handlePost(HttpExchange he) throws IOException {
        Map<String, String> formData = WebUtil.getResponseMap(he);
        String username = formData.get("username");
        String password = formData.get("password");

        if (username == null || password == null) {
            sendResponse(he, 400, "Username and password are required");
            return;
        }

        User user = validateUser(username, password);
        if (user == null) {
            sendResponse(he, 401, "Invalid login credentials");
            return;
        }
        
        //Clear any previous sessions
        String currentSession = WebUtil.extractSessionId(he);
        sessionManager.removeSession(currentSession);
        
        // Create a new session for the user
        String sessionId = sessionManager.createSession();
        Session session = sessionManager.getSession(sessionId);
        session.setAttribute("userId", user.getCustomerId());
        session.setAttribute("role", user.getRole());

        // Set the session ID in a cookie
        he.getResponseHeaders().add("Set-Cookie", "SESSIONID=" + sessionId + "; Path=/; HttpOnly");

        switch (user.getRole().toLowerCase()) {
            case "admin":
                handleAdmin(he, user);
                break;
            case "customer":
                handleCustomer(he, user);
                break;
            case "business":
                handleCustomer(he, user);
                break;
            default:
                sendResponse(he, 403, "Invalid user role");
        }
    }

    private User validateUser(String username, String password) {
        try {
            int userId = userDao.getUserIdFromUsername(username);
            User user = userDao.getUser(userId);

            if (user != null && user.validatePassword(password)) {
                return user;
            }
        } catch (Exception e) {
            System.err.println("Error validating user: " + e.getMessage());
        }
        return null;
    }

    private void handleCustomer(HttpExchange he, User user) throws IOException {
        he.getResponseHeaders().set("Location", "/home");
        he.sendResponseHeaders(302, -1);
    }

    private void handleAdmin(HttpExchange he, User user) throws IOException {
        he.getResponseHeaders().set("Location", "/admin");
        he.sendResponseHeaders(302, -1);
    }

    private void sendResponse(HttpExchange he, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        he.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = he.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
