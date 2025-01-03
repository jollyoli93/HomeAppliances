package sessionManagement;

import java.util.HashMap;

public class Session {
    private final String id;
    private long lastAccessTime;
    private HashMap<String, Object> storedAttributes;

    public Session(String id, long lastAccessTime) {
        this.id = id;
        this.lastAccessTime = lastAccessTime;
        this.storedAttributes = new HashMap<String, Object>();
    }

    public String getId() {
        return id;
    }
    
    public void setAttribute(String key, Object value) {
    	this.storedAttributes.put(key, value);
    }
    
    public HashMap<String, Object> getAttributes() {
    	return this.storedAttributes;
    }
    
    public Object getAttribute(Object key) {
    	return this.storedAttributes.get(key);
    }
    	
    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public boolean isExpired(long expirationTime) {
        return System.currentTimeMillis() - lastAccessTime > expirationTime;
    }
}
