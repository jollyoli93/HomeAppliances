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

/**
 * Handles HTTP requests for user login, including GET and POST methods.
 * 
 * @author 24862664
 */
public class LoginHandler implements HttpHandler {
    private final UserDao userDao;
    private final SessionManager sessionManager;

    /**
     * Constructs a LoginHandler with the specified UserDao and SessionManager.
     * 
     * @param userDao        the data access object for user-related operations
     * @param sessionManager the session manager for handling user sessions
     */
    public LoginHandler(UserDao userDao, SessionManager sessionManager) {
        this.userDao = userDao;
        this.sessionManager = sessionManager;
    }

    /**
     * Handles incoming HTTP requests by delegating to the appropriate method based on
     * the HTTP method.
     * 
     * @param he the HttpExchange object representing the HTTP request and response
     * @throws IOException if an I/O error occurs while handling the request
     */
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

    /**
     * Handles GET requests by serving the login form HTML page.
     * 
     * @param he the HttpExchange object representing the HTTP request and response
     * @throws IOException if an I/O error occurs while sending the response
     */
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

    /**
     * Handles POST requests for user login.
     * Validates the user's credentials and redirects based on their role.
     * 
     * @param he the HttpExchange object representing the HTTP request and response
     * @throws IOException if an I/O error occurs while sending the response
     */
    private void handlePost(HttpExchange he) throws IOException {
        Map<String, String> formData = WebUtil.getResponseMap(he);
        String username = formData.get("username");
        String password = formData.get("password");

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            sendResponse(he, 400, "Username and password are required");
            return;
        }

        User user = validateUser(username, password);
        if (user == null) {
            sendResponse(he, 401, "Invalid login credentials");
            return;
        }

        String sessionId = sessionManager.createSession();
        Session session = sessionManager.getSession(sessionId);

        session.setAttribute("userId", user.getCustomerId());
        session.setAttribute("role", user.getRole());

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

    /**
     * Validates the user's credentials against the stored user data.
     * 
     * @param username the username provided by the user
     * @param password the password provided by the user
     * @return the User object if validation is successful, or null otherwise
     */
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

    /**
     * Redirects the user to the customer home page.
     * 
     * @param he   the HttpExchange object
     * @param user the authenticated user
     * @throws IOException if an I/O error occurs while sending the response
     */
    private void handleCustomer(HttpExchange he, User user) throws IOException {
        he.getResponseHeaders().set("Location", "/home");
        he.sendResponseHeaders(302, -1);
    }

    /**
     * Redirects the user to the admin dashboard.
     * 
     * @param he   the HttpExchange object
     * @param user the authenticated user
     * @throws IOException if an I/O error occurs while sending the response
     */
    private void handleAdmin(HttpExchange he, User user) throws IOException {
        he.getResponseHeaders().set("Location", "/admin");
        he.sendResponseHeaders(302, -1);
    }

    /**
     * Sends an HTTP response with the specified status code and message.
     * 
     * @param he         the HttpExchange object
     * @param statusCode the HTTP status code
     * @param response   the response message
     * @throws IOException if an I/O error occurs while sending the response
     */
    private void sendResponse(HttpExchange he, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes();
        he.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = he.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
