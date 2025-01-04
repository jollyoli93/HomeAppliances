package sessionManagement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import util.WebUtil;

import java.io.IOException;

public class SessionValidationHandler implements HttpHandler {
    private final HttpHandler next;
    private final SessionManager sessionManager;

    public SessionValidationHandler(HttpHandler next, SessionManager sessionManager) {
        this.next = next;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String sessionId = WebUtil.extractSessionId(exchange);
        Session session =  sessionManager.getSession(sessionId);
        
        //get user role
        String role = (String) session.getAttribute("role");

        if (sessionId != null && session != null && role.equals("admin")) {
            next.handle(exchange);
        } else {
            exchange.sendResponseHeaders(401, -1);
        }
    }
}
