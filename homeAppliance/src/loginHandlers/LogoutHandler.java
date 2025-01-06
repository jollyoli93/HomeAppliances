package loginHandlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sessionManagement.SessionManager;
import util.WebUtil;

/**
 * Handles HTTP requests for logging out users by terminating their sessions.
 * 
 * @author 24862664
 */
public class LogoutHandler implements HttpHandler {
    private final SessionManager sessionManager;

    /**
     * Constructs a LogoutHandler with the specified SessionManager.
     * 
     * @param sessionManager the session manager responsible for managing user sessions
     */
    public LogoutHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     * Handles the logout request by removing the user's session and redirecting to the homepage.
     * 
     * @param he the HttpExchange object representing the HTTP request and response
     * @throws IOException if an I/O error occurs while handling the request
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Retrieve session ID from cookies or request headers
        String sessionId = getSessionIdFromRequest(he);
        System.out.println(sessionId);

        if (sessionId != null) {
            // Remove the session from the session manager
            sessionManager.removeSession(sessionId);

            // Redirect to homepage
            he.getResponseHeaders().add("Location", "/");
            he.sendResponseHeaders(302, -1); // 302 Found (redirect)
        } else {
            // If no session ID is found, send a response indicating failure
            String response = "No session found to log out.";
            he.getResponseHeaders().set("Content-Type", "text/plain");
            he.sendResponseHeaders(400, response.getBytes().length);
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    /**
     * Retrieves the session ID from the HTTP request, typically from cookies or headers.
     * 
     * @param he the HttpExchange object representing the HTTP request
     * @return the session ID if present, or null otherwise
     */
    private String getSessionIdFromRequest(HttpExchange he) {
        return WebUtil.extractSessionId(he);
    }
}
