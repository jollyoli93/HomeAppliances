package webpages;

import com.sun.net.httpserver.HttpHandler;
import DAO.ApplianceDao;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import appliances.ApplianceFactory;

public class AddApplianceHandler implements HttpHandler {
    private ApplianceDao applianceDao;

    public AddApplianceHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Get the list of departments from the appliance factories
        ArrayList<String> departments = ApplianceFactory.selectApplianceFactory("Entertainment").listFactoryTypes();

        // Handle the case where no departments are found
        if (departments.isEmpty()) {
            String errorMessage = "<html><body><h3>No departments available!</h3></body></html>";
            he.sendResponseHeaders(404, errorMessage.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(errorMessage.getBytes());
            }
            return;
        }

        // Generate department options dynamically
        StringBuilder departmentOptions = new StringBuilder();
        for (String department : departments) {
            departmentOptions.append("<option value=\"")
                             .append(department.toLowerCase())
                             .append("\">")
                             .append(department)
                             .append("</option>");
        }

        // HTML for the department selection form
        String html = "<html>" +
            "<head>" +
                "<title>Select Department</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
            "</head>" +
            "<body>" +
                "<div class=\"container mt-4\">" +
                    "<h2>Select Department</h2>" +
                    "<form method=\"get\" action=\"/admin/appliances/add/type\">" +
                        "<div class=\"form-group\">" +
                            "<label>Department</label>" +
                            "<select name=\"department\" class=\"form-control\" required>" +
                            departmentOptions.toString() +
                            "</select>" +
                        "</div>" +
                        "<button type=\"submit\" class=\"btn btn-primary\">Next</button>" +
                    "</form>" +
                "</div>" +
            "</body>" +
        "</html>";

        // Send response
        he.sendResponseHeaders(200, html.length());
        try (OutputStream os = he.getResponseBody()) {
            os.write(html.getBytes());
        }
    }
}
