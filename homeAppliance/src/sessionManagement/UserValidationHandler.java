package sessionManagement;

import com.sun.net.httpserver.HttpHandler;

/**
 * A handler that validates if a user has a valid role (admin, business, or customer).
 * Extends {@link RoleValidationHandler} to provide role based validation.
 * 
 * @author 24862664
 */
public class UserValidationHandler extends RoleValidationHandler {

    /**
     * Constructs a new {@code UserValidationHandler} with the given next handler and session manager.
     * 
     * @param next The next {@code HttpHandler} in the chain of responsibility.
     * @param sessionManager The {@code SessionManager} used for managing sessions.
     */
    public UserValidationHandler(HttpHandler next, SessionManager sessionManager) {
        super(next, sessionManager);
    }

    /**
     * Checks if the session has a valid role (admin, business, or customer).
     * 
     * @param session The session to validate.
     * @return true if the role is valid (admin, business, or customer) and false otherwise.
     */
    @Override
    protected boolean isValidRole(Session session) {
        String role = (String) session.getAttribute("role");
        return "admin".equalsIgnoreCase(role) || "business".equalsIgnoreCase(role) || "customer".equalsIgnoreCase(role);
    }
}
