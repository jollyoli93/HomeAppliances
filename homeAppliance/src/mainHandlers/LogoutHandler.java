package mainHandlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sessionManagement.Session;
import sessionManagement.SessionManager;
import util.WebUtil;

public class LogoutHandler implements HttpHandler {
    private final SessionManager sessionManager;

    public LogoutHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Retrieve session ID from cookies or request headers
        String sessionId = getSessionIdFromRequest(he);
        System.out.println(sessionId);
        
        if (sessionId != null) {
            // Remove the session from the session manager
            sessionManager.removeSession(sessionId);
            
            // Send a successful logout response
            String response = "Logged out successfully.";
            he.getResponseHeaders().set("Content-Type", "text/plain");
            he.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            // If no session ID is found, send a response indicating failure
            String response = "No session found to log out.";
            he.getResponseHeaders().set("Content-Type", "text/plain");
            he.sendResponseHeaders(400, response.getBytes().length);
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    // Method to retrieve session ID from request (e.g., from cookies or headers)
    private String getSessionIdFromRequest(HttpExchange he) {
        String sessionId = WebUtil.extractSessionId(he);

        return sessionId;
    }
}
