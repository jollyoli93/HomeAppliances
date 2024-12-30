package util;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

public class WebUtil {

  public static HashMap<String, String> requestStringToMap(String request) {
    HashMap<String, String> map = new HashMap<String, String>();
    String[] pairs = request.split("&");
    for (int i = 0; i < pairs.length; i++) {
      String pair = pairs[i];

      try {
        String[] keyValue = pair.split("=");
        String key = keyValue[0];
        key = URLDecoder.decode(key, "UTF-8");

        String value = keyValue[1];
        value = URLDecoder.decode(value, "UTF-8");

        map.put(key, value);
      } catch (UnsupportedEncodingException e) {
        System.err.println(e.getMessage());
      }

    }
    return map;
  }
  
  public static Map getResponseMap(HttpExchange he) throws IOException {
	  BufferedReader in = new BufferedReader(new InputStreamReader(he.getRequestBody()));
	
	  String line;
	  String request = "";
	
	  while( (line = in.readLine()) != null) {
		  request = request + line;
	  }
	
	  HashMap<String,String> map = WebUtil.requestStringToMap(request);
	return map;
  }
  
  
}
