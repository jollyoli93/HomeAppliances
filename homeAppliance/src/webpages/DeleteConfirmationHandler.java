// DeleteConfirmationHandler.java
package webpages;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import DAO.ApplianceDao;
import appliances.Appliance;

public class DeleteConfirmationHandler implements HttpHandler {
    private ApplianceDao applianceDao;
    
    public DeleteConfirmationHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        int id = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);
        Appliance appliance = applianceDao.getAppliance(id);
        
        String html = "<html>" +
            "<head>" +
                "<title>Confirm Delete</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
            "</head>" +
            "<body>" +
                "<div class=\"container mt-5\">" +
                    "<div class=\"card\">" +
                        "<div class=\"card-body\">" +
                            "<h3 class=\"card-title text-danger\">Confirm Delete</h3>" +
                            "<p>Are you sure you want to delete this appliance?</p>" +
                            "<p><strong>Description:</strong> " + appliance.getDescription() + "</p>" +
                            "<p><strong>SKU:</strong> " + appliance.getSku() + "</p>" +
                            "<p><strong>Price:</strong> $" + appliance.getPrice() + "</p>" +
                            "<form method=\"post\" action=\"/admin/appliances/delete\" style=\"display: inline;\">" +
                                "<input type=\"hidden\" name=\"id\" value=\"" + appliance.getId() + "\">" +
                                "<button type=\"submit\" class=\"btn btn-danger\">Delete</button>" +
                                "<a href=\"/admin/appliances\" class=\"btn btn-secondary ml-2\">Cancel</a>" +
                            "</form>" +
                        "</div>" +
                    "</div>" +
                "</div>" +
            "</body>" +
        "</html>";
        
        he.sendResponseHeaders(200, html.length());
        OutputStream os = he.getResponseBody();
        os.write(html.getBytes());
        os.close();
    }
}