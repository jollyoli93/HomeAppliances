package applianceHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import DAO.ApplianceDao;
import appliances.Appliance;
import util.WebUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Handles HTTP requests for displaying the edit appliance form.
 * This handler retrieves an appliance's details from the database and renders a form for editing the appliance.
 * 
 * @author 24862664
 */
public class EditApplianceForm implements HttpHandler {
    private ApplianceDao applianceDao;

    /**
     * Constructor to initialize the handler with the appliance DAO.
     *
     * @param applianceDao The DAO responsible for appliance-related operations.
     */
    public EditApplianceForm(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    /**
     * Handles the incoming HTTP request to display the edit appliance form.
     * The appliance's details are fetched from the database and presented in a form for editing.
     * 
     * @param he The HTTP exchange object containing the request and response data.
     * @throws IOException If an I/O error occurs while handling the request or sending the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            // Retrieve the query string and parse it into a map
            String query = he.getRequestURI().getQuery();
            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
            String id = queryParams.get("id");

            // Send response headers with status code 200 (OK)
            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            // Fetch appliance details from the database
            Appliance appliance = applianceDao.getAppliance(Integer.parseInt(id));

            // If appliance is found, display the edit form
            if (appliance != null) {
                out.write(
                    "<html>" +
                    "<head> <title>Edit Appliance</title> " +
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "  <h1 class=\"mt-4\">Edit Appliance</h1>" +
                    "  <form method=\"post\" action=\"/admin/appliances/update\">" +
                    "    <div class=\"form-group\">" +
                    "      <label for=\"id\">ID</label>" +
                    "      <input type=\"text\" class=\"form-control\" id=\"id\" name=\"id\" value=\"" + appliance.getId() + "\" readonly>" +
                    "    </div>" +
                    "    <div class=\"form-group\">" +
                    "      <label for=\"description\">Description</label>" +
                    "      <input type=\"text\" class=\"form-control\" id=\"description\" name=\"description\" value=\"" + appliance.getDescription() + "\" readonly>" +
                    "    </div>" +
                    "    <div class=\"form-group\">" +
                    "      <label for=\"category\">Category</label>" +
                    "      <input type=\"text\" class=\"form-control\" id=\"category\" name=\"category\" value=\"" + appliance.getCategory() + "\" readonly>" +
                    "    </div>" +
                    "    <div class=\"form-group\">" +
                    "      <label for=\"sku\">SKU</label>" +
                    "      <input type=\"text\" class=\"form-control\" id=\"sku\" name=\"sku\" value=\"" + appliance.getSku() + "\" readonly>" +
                    "    </div>" +
                    "    <div class=\"form-group\">" +
                    "      <label for=\"price\">Price</label>" +
                    "      <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"price\" name=\"price\" value=\"" + appliance.getPrice() + "\">" +
                    "    </div>" +
                    "    <button type=\"submit\" class=\"btn btn-success\">Save Changes</button>" +
                    "  </form>" +
                    "</div>" +
                    "</body>" +
                    "</html>");
            } else {
                // If appliance not found, show error message
                out.write("<html><body><h1>Appliance Not Found</h1></body></html>");
            }
        } catch (Exception e) {
            // Handle exceptions, respond with 500 Internal Server Error
            he.sendResponseHeaders(500, 0);
            if (out != null) {
                out.write("<html><body><h1>Internal Server Error</h1></body></html>");
            }
            e.printStackTrace();
        } finally {
            // Close the output stream
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
