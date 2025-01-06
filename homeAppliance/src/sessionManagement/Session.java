package sessionManagement;

import java.util.HashMap;

/**
 * Represents a user session in the system, storing session data and managing session expiration.
 * 
 * @author 24862664
 */
public class Session {
    private final String id;
    private long lastAccessTime;
    private HashMap<String, Object> storedAttributes;

    /**
     * Constructs a new session with the specified session ID and last access time.
     * 
     * @param id The unique identifier for the session.
     * @param lastAccessTime The timestamp representing the last time the session was accessed.
     */
    public Session(String id, long lastAccessTime) {
        this.id = id;
        this.lastAccessTime = lastAccessTime;
        this.storedAttributes = new HashMap<String, Object>();
    }

    /**
     * Gets the unique ID of this session.
     * 
     * @return The session ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets an attribute in the session with the specified key and value.
     * 
     * @param key The attribute's key.
     * @param value The value associated with the key.
     */
    public void setAttribute(String key, Object value) {
        this.storedAttributes.put(key, value);
    }

    /**
     * Gets all attributes stored in this session as a map.
     * 
     * @return A map containing all session attributes.
     */
    public HashMap<String, Object> getAttributes() {
        return this.storedAttributes;
    }

    /**
     * Retrieves a specific attribute from the session using its key.
     * 
     * @param key The key of the attribute to retrieve.
     * @return The value associated with the specified key, or null if the key doesn't exist.
     */
    public Object getAttribute(Object key) {
        return this.storedAttributes.get(key);
    }

    /**
     * Gets the last access time of the session.
     * 
     * @return The timestamp of the last access.
     */
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    /**
     * Updates the last access time of the session to the current system time.
     */
    public void updateLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    /**
     * Checks whether the session has expired based on the specified expiration time.
     * 
     * @param expirationTime The time in milliseconds after which the session is considered expired.
     * @return true if the session has expired, false otherwise.
     */
    public boolean isExpired(long expirationTime) {
        return System.currentTimeMillis() - lastAccessTime > expirationTime;
    }
}
