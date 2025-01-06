package sessionManagement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.WebUtil;

import java.io.IOException;

/**
 * Abstract handler that validates user roles before passing the request to the next handler in the chain.
 * It checks if the user has the appropriate role for accessing the resource.
 * 
 * @author 24862664
 */
public abstract class RoleValidationHandler implements HttpHandler {
    protected final HttpHandler next;
    protected final SessionManager sessionManager;

    /**
     * Constructs a RoleValidationHandler with the specified next handler and session manager.
     * 
     * @param next The next handler to call if the user has the valid role.
     * @param sessionManager The session manager used to manage user sessions.
     */
    public RoleValidationHandler(HttpHandler next, SessionManager sessionManager) {
        this.next = next;
        this.sessionManager = sessionManager;
    }

    /**
     * Handles the HTTP request by checking the session's role and passing it to the next handler
     * if the role is valid. Otherwise, it returns a 401 Unauthorized response.
     * 
     * @param exchange The HTTP exchange object representing the request-response interaction.
     * @throws IOException If an I/O error occurs during the handling of the request.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String sessionId = WebUtil.extractSessionId(exchange);
        Session session = sessionManager.getSession(sessionId);

        if (sessionId != null && session != null && isValidRole(session)) {
            next.handle(exchange);
        } else {
            exchange.sendResponseHeaders(401, -1); // Unauthorized
        }
    }

    /**
     * Abstract method to be implemented by subclasses to validate the user's role.
     * 
     * @param session The session object representing the user's session.
     * @return true if the user's role is valid, false otherwise.
     */
    protected abstract boolean isValidRole(Session session);
}
