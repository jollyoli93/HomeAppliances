package webHandlers;

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
	        Map<String, String> formData = WebUtil.getResponseMap(he);
	        
	        // Extract form data
	        int id = Integer.parseInt(formData.get("id"));
	        String price = formData.get("price");
	        
	        // Update price in database
	        String result = applianceDao.updateFieldById(id, "price", Double.parseDouble(price));
	        
	        // Prepare response
	        String response = "Update complete: " + result;
	        he.getResponseHeaders().add("Location", "/admin/appliances");
	        he.sendResponseHeaders(302, response.length());
	        he.getResponseBody().write(response.getBytes());
	        he.getResponseBody().close();
	    }
}
