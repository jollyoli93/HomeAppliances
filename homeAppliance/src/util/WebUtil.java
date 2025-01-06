package util;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

/**
 * Utility class for handling web-related operations such as parsing request strings and extracting session IDs.
 * 
 * @author 24862664
 */
public class WebUtil {

    /**
     * Converts a URL-encoded query string into a map of key-value pairs.
     * 
     * @param request The query string to be converted.
     * @return A map containing the key-value pairs from the query string.
     */
    public static HashMap<String, String> requestStringToMap(String request) {
        HashMap<String, String> map = new HashMap<>();
        String[] pairs = request.split("&");
        for (String pair : pairs) {
            try {
                String[] keyValue = pair.split("=");
                map.put(URLDecoder.decode(keyValue[0], "UTF-8"), URLDecoder.decode(keyValue[1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.err.println(e.getMessage());
            }
        }
        return map;
    }
  
    /**
     * Extracts and converts the body of an HTTP request into a map.
     * 
     * @param he The HttpExchange object containing the request.
     * @return A map containing the request parameters.
     * @throws IOException If an error occurs while reading the request body.
     */
    public static Map getResponseMap(HttpExchange he) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
        StringBuilder request = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            request.append(line);
        }
        return requestStringToMap(request.toString());
    }

    /**
     * Extracts the session ID from the request's cookies.
     * 
     * @param he The HttpExchange object containing the request headers.
     * @return The session ID, or null if not found.
     */
    public static String extractSessionId(HttpExchange he) {
        String cookieHeader = he.getRequestHeaders().getFirst("Cookie");
        if (cookieHeader != null && cookieHeader.startsWith("SESSIONID=")) {
            return cookieHeader.substring("SESSIONID=".length());
        }
        return null;
    }
}
