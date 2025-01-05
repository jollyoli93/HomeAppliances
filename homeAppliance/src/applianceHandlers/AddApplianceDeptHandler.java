package applianceHandlers;

import com.sun.net.httpserver.HttpHandler;
import DAO.ApplianceDao;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import appliances.ApplianceDepartments;

public class AddApplianceDeptHandler implements HttpHandler {
    private ApplianceDao applianceDao;

    public AddApplianceDeptHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Get the list of departments from the appliance factories
        ArrayList<String> departments = ApplianceDepartments.selectApplianceDepartment("entertainment").listFactoryTypes();

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
                "<div class=\"container mt-4\">" +
                    "<h2>Select Department</h2>" +
                    "<form method=\"get\" action=\"/appliances/add/type\">" +
                        "<div class=\"form-group\">" +
                            "<label>Department</label>" +
                            "<select name=\"department\" class=\"form-control\" required>" +
                            departmentOptions.toString() +
                            "</select>" +
                        "</div>" +
                        "<button type=\"submit\" class=\"btn btn-primary\">Next</button>" +
        				"<a href=\"javascript:window.history.back();\" class=\"btn btn-secondary ml-2\">Back</a>" +
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
