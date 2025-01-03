package sessionManagement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

public class SessionManager {
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private final long SESSION_EXPIRATION = 30 * 60 * 1000; // 30 minutes

    public String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(sessionId, System.currentTimeMillis()));
        return sessionId;
    }

    public Session getSession(String sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null && !session.isExpired(SESSION_EXPIRATION)) {
            session.updateLastAccessTime();
            return session;
        }
        if (session != null) {
            sessions.remove(sessionId); // Remove expired session
        }
        return null;
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
