package sessionManagement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

/**
 * Manages user sessions, providing functionality to create, retrieve, and remove sessions.
 * Sessions are stored with an expiration time of 30 minutes.
 * 
 * @author 24862664
 */
public class SessionManager {
    private final ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<>();
    private final long SESSION_EXPIRATION = 30 * 60 * 1000; // 30 minutes

    /**
     * Creates a new session and returns its unique session ID.
     * 
     * @return The new session ID.
     */
    public String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, new Session(sessionId, System.currentTimeMillis()));
        return sessionId;
    }

    /**
     * Retrieves a session by its ID, or null if the session is expired or doesn't exist.
     * 
     * @param sessionId The session ID.
     * @return The session if valid, null if expired or invalid.
     */
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

    /**
     * Removes a session by its ID.
     * 
     * @param sessionId The session ID.
     */
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
