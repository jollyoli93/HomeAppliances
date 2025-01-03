package sessionManagement;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
        String sessionId = extractSessionId(exchange);

        if (sessionId != null && sessionManager.getSession(sessionId) != null) {
            next.handle(exchange);
        } else {
            exchange.sendResponseHeaders(401, -1);
        }
    }

    private String extractSessionId(HttpExchange exchange) {
        String cookieHeader = exchange.getRequestHeaders().getFirst("Cookie");
        if (cookieHeader != null && cookieHeader.startsWith("SESSIONID=")) {
            return cookieHeader.substring("SESSIONID=".length());
        }
        return null;
    }
}
