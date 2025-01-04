package sessionManagement;

import com.sun.net.httpserver.HttpHandler;

public class AdminValidationHandler extends RoleValidationHandler {
    public AdminValidationHandler(HttpHandler next, SessionManager sessionManager) {
        super(next, sessionManager);
    }

    @Override
    protected boolean isValidRole(Session session) {
        String role = (String) session.getAttribute("role");
        return "admin".equalsIgnoreCase(role);
    }
}
