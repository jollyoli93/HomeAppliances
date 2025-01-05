package applianceHandlers;

import java.io.IOException;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import DAO.ApplianceDao;
import util.WebUtil;

/**
 * Handles HTTP requests for updating an appliance's details.
 * This handler processes the form data received for updating specific fields of an appliance.
 * 
 * @author 24862664
 */
public class UpdateApplianceHandler implements HttpHandler {
    private ApplianceDao applianceDao;

    /**
     * Constructor to initialize the handler with the appliance DAO.
     *
     * @param applianceDao The DAO responsible for appliance-related operations.
     */
    public UpdateApplianceHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    /**
     * Handles the HTTP request to update an appliance's details.
     * This method processes the form data, extracts the appliance ID and updated fields, 
     * and updates the corresponding appliance in the database.
     * 
     * @param he The HTTP exchange object containing the request and response data.
     * @throws IOException If an I/O error occurs while handling the request or sending the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Extract form data from the request
        Map<String, String> formData = WebUtil.getResponseMap(he);

        // Extract appliance ID and updated price from form data
        int id = Integer.parseInt(formData.get("id"));
        String price = formData.get("price");

        // Update the appliance's price in the database
        String result = applianceDao.updateFieldById(id, "price", Double.parseDouble(price));

        // Prepare a response message indicating the update result
        String response = "Update complete: " + result;

        // Redirect to success page after updating
        he.getResponseHeaders().add("Location", "/admin/success");

        // Send a redirect response (302)
        he.sendResponseHeaders(302, response.length());
        he.getResponseBody().write(response.getBytes());
        he.getResponseBody().close();
    }
}
