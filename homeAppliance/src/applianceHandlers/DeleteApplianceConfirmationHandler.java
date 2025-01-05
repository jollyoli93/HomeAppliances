package applianceHandlers;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import DAO.ApplianceDao;
import appliances.Appliance;

/**
 * Handles the HTTP requests for confirming the deletion of an appliance.
 * This handler generates an HTML page asking the user to confirm whether they want to delete the appliance.
 * 
 * @author [Your Name]
 */
public class DeleteApplianceConfirmationHandler implements HttpHandler {
    private ApplianceDao applianceDao;
    
    /**
     * Constructor to initialize the handler with the appliance DAO.
     *
     * @param applianceDao The DAO responsible for appliance-related operations.
     */
    public DeleteApplianceConfirmationHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }
    
    /**
     * Handles the incoming HTTP request for confirming the deletion of an appliance.
     * It retrieves the appliance by its ID and displays a confirmation page.
     * The user can confirm or cancel the deletion.
     * 
     * @param he The HTTP exchange object containing the request and response data.
     * @throws IOException If an I/O error occurs while handling the request or sending the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Retrieve the appliance ID from the query string
        int id = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);
        // Fetch the appliance details
        Appliance appliance = applianceDao.getAppliance(id);
        
        // Generate the HTML response for the confirmation page
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
        
        // Send the response headers and the HTML content
        he.sendResponseHeaders(200, html.length());
        OutputStream os = he.getResponseBody();
        os.write(html.getBytes());
        os.close();
    }
}
