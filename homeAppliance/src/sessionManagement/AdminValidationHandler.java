package sessionManagement;

import com.sun.net.httpserver.HttpHandler;

/**
 * A handler that validates if the user has the 'admin' role before allowing access to the next handler.
 * Extends {@link RoleValidationHandler} to ensure that only users with the admin role can proceed.
 * 
 * @author 24862664
 */
public class AdminValidationHandler extends RoleValidationHandler {

    /**
     * Constructs an AdminValidationHandler with the specified next handler and session manager.
     * 
     * @param next The next handler to be called if the role validation is successful.
     * @param sessionManager The session manager used to manage user sessions.
     */
    public AdminValidationHandler(HttpHandler next, SessionManager sessionManager) {
        super(next, sessionManager);
    }

    /**
     * Validates that the session corresponds to a user with the 'admin' role.
     * 
     * @param session The session object containing the user's information.
     * @return true if the user's role is 'admin', false otherwise.
     */
    @Override
    protected boolean isValidRole(Session session) {
        String role = (String) session.getAttribute("role");
        return "admin".equalsIgnoreCase(role);
    }
}
