package webpages;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import util.WebUtil;

public class DeleteApplianceHandler implements HttpHandler {
    private ApplianceDao applianceDao;
    
    public DeleteApplianceHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        Map<String, String> formData = WebUtil.getResponseMap(he);
        
        int id = Integer.parseInt(formData.get("id"));
        int result = applianceDao.deleteApplianceById(id);
        
        String response = "Delete complete. Rows affected: " + result;
        he.getResponseHeaders().add("Location", "/admin/appliances");
        he.sendResponseHeaders(302, response.length());
        he.getResponseBody().write(response.getBytes());
        he.getResponseBody().close();
    }
}