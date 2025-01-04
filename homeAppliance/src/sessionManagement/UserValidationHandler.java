package sessionManagement;

import com.sun.net.httpserver.HttpHandler;

public class UserValidationHandler extends RoleValidationHandler {
    public UserValidationHandler(HttpHandler next, SessionManager sessionManager) {
        super(next, sessionManager);
    }

    @Override
    protected boolean isValidRole(Session session) {
        String role = (String) session.getAttribute("role");
        return "admin".equalsIgnoreCase(role) || "business".equalsIgnoreCase(role) || "customer".equalsIgnoreCase(role);
    }
}
