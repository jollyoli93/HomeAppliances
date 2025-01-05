package applianceHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

import appliances.Appliance;
import appliances.ApplianceDepartments;
import util.WebUtil;

/**
 * Handles the logic for confirming the addition of a new appliance.
 * This handler processes both GET and POST requests related to appliance addition.
 * @author 24862664
 */
public class AddApplianceConfirmHandler implements HttpHandler {
    private ApplianceDao applianceDao;
    private ApplianceDepartments department;
    private Appliance appliance;

    /**
     * Constructor for initializing the handler with the appliance DAO.
     *
     * @param applianceDao The DAO responsible for interacting with appliance data.
     */
    public AddApplianceConfirmHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    /**
     * Handles incoming HTTP requests. Directs the request to the appropriate method
     * based on the HTTP method type (GET or POST).
     *
     * @param he The HTTP exchange object containing request and response data.
     * @throws IOException If an I/O error occurs while handling the request.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        String method = he.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            handleGet(he);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(he);
        } else {
            // If the method is neither GET nor POST, return a 405 Method Not Allowed.
            he.sendResponseHeaders(405, -1);
        }
    }

    /**
     * Handles GET requests. Displays the appliance details and a form to confirm the addition of an appliance.
     *
     * @param he The HTTP exchange object containing request and response data.
     * @throws IOException If an I/O error occurs while handling the GET request.
     */
    private void handleGet(HttpExchange he) throws IOException {
        BufferedWriter out = null;

        try {
            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            String query = he.getRequestURI().getQuery();
            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
            String selectedDepartment = queryParams.get("department");
            String applianceType = queryParams.get("appliance");

            // Select department based on the query parameter
            department = ApplianceDepartments.selectApplianceDepartment(selectedDepartment);
            System.out.println(department);

            // Select appliance based on the query parameter
            appliance = department.selectAppliance(applianceType);

            System.out.println(appliance.getDescription());
            System.out.println(appliance.getId());

            // Display appliance details if available
            if (appliance != null) {
                out.write(
                    "<html>" +
                    "<head> <title>Edit Appliance</title> " +
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                    "</head>" +
                    "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                    "<a class=\"navbar-brand\" href=\"/\">Home Appliance Store</a>" +
                    "<button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\">" +
                        "<span class=\"navbar-toggler-icon\"></span>" +
                    "</button>" +
                    "<div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                        "<ul class=\"navbar-nav\">" +
                            "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/\">Home</a></li>" +
                            "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
                        "</ul>" +
                    "</div>" +
                    "</nav>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "  <h1 class=\"mt-4\">Add Appliance</h1>" +
                    "  <form method=\"post\" action=\"/appliances/admin/confirm\">" +
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
                    "      <input type=\"number\" step=\"0.01\" class=\"form-control\" id=\"price\" name=\"price\" value=\"" + appliance.getPrice() + "\" readonly>" +
                    "    </div>" +
                    "    <button type=\"submit\" class=\"btn btn-success\">Confirm</button>" +
                    "    <a href=\"javascript:window.history.back();\" class=\"btn btn-primary ml-2\">Back</a>" +
                    "  </form>" +
                    "</div>" +
                    "</body>" +
                    "</html>");
            } else {
                out.write("<html><body><h1>Appliance Not Found</h1></body></html>");
            }

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0);
            if (out != null) {
                out.write("<html><body><h1>Internal Server Error</h1></body></html>");
            }
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles POST requests. Adds a new appliance to the database based on the provided data.
     *
     * @param he The HTTP exchange object containing request and response data.
     * @throws IOException If an I/O error occurs while handling the POST request.
     */
    private void handlePost(HttpExchange he) throws IOException {
        String response;

        try (InputStream is = he.getRequestBody()) {
            System.out.println("Debug: Post request.");

            // Read the body of the request
            String body = new String(is.readAllBytes());

            // Parse the body into a map
            Map<String, String> formParams = WebUtil.requestStringToMap(body);
            String selectedDepartment = formParams.get("category");
            String applianceType = formParams.get("description");

            // Select department based on the form parameter
            department = ApplianceDepartments.selectApplianceDepartment(selectedDepartment.toLowerCase());
            System.out.println(department);

            // Select appliance based on the form parameter
            appliance = department.selectAppliance(applianceType.toLowerCase());
            System.out.println(appliance);
            
            System.out.println(appliance.getDescription());

            // Add appliance if found
            if (appliance != null) {
                try {
                    applianceDao.addNewAppliance(appliance, null);
                    response = "Appliance added successfully!";
                    System.out.println(response);

                    // Redirect to success page
                    he.getResponseHeaders().add("Location", "/success/");
                    he.sendResponseHeaders(302, -1); // 302 Found (redirect)
                    return;
                } catch (Exception e) {
                    response = "Failed to add appliance.";
                    e.printStackTrace();
                }
            } else {
                response = "Appliance not found.";
            }
        } catch (Exception e) {
            response = "Failed to add appliance: " + e.getMessage();
            e.printStackTrace();
        }

        // Send response back to client
        he.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = he.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
