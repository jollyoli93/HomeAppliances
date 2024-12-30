package webpages;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import util.WebUtil;

public class UpdateApplianceHandler implements HttpHandler{
	private ApplianceDao applianceDao;
	
	public UpdateApplianceHandler(ApplianceDao applianceDao) {
		this.applianceDao = applianceDao;
	}
	
	@Override
    public void handle(HttpExchange he) throws IOException {
	    String response = "Processing update...";
	    he.sendResponseHeaders(200, response.length());
		
		Map<String, String> map = null;
		try {
			map = WebUtil.getResponseMap(he);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(map);
	
		String price = map.get("price");
		
		//System.out.println(username + " " + password);
    }
}
