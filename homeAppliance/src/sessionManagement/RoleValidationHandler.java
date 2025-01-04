package sessionManagement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import util.WebUtil;

import java.io.IOException;

public abstract class RoleValidationHandler implements HttpHandler {
    protected final HttpHandler next;
    protected final SessionManager sessionManager;

    public RoleValidationHandler(HttpHandler next, SessionManager sessionManager) {
        this.next = next;
        this.sessionManager = sessionManager;
    }

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

    protected abstract boolean isValidRole(Session session);
}

