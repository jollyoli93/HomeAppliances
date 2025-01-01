package applianceHandlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import appliances.Appliance;
import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;
import util.WebUtil;

public class AddApplianceTypeHandler implements HttpHandler {
    private ApplianceDao applianceDao;

    public AddApplianceTypeHandler(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        // Get the query parameters
        String query = he.getRequestURI().getQuery();
        HashMap<String, String> requestString = WebUtil.requestStringToMap(query);
        String selectedDepartment = requestString.get("department"); // Get selected department
        System.out.println("Selected Department: " + selectedDepartment);
        
        // Generate HTML for the department selection form
        String html = "<html>" +
            "<head>" +
                "<title>Select Appliance Type</title>" +
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
                    "<h2>Select Appliance Type</h2>" +
                    "<form method=\"get\" action=\"/admin/appliances/add/confirm\">" +
                        "<div class=\"form-group\">" +
                        	"<label for=\"department\">Department</label>" +
                            "<input type=\"text\" class=\"form-control\" id=\"department\" name=\"department\" value=\"" + selectedDepartment + "\" readonly>" +
                            "<label>Appliance</label>" +
                            "<select name=\"appliance\" class=\"form-control\" required>";

        // add dynamically population for department options

		ApplianceDepartments applianceFactory = null;        
        
        switch (selectedDepartment)  {
        case "entertainment":
			applianceFactory = new EntertainmentFactory();
			break;
        case "home cleaning":
			applianceFactory = new HomeCleaningFactory();
			break;
		default:
			applianceFactory = new EntertainmentFactory();
			break;
        }
        
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		System.out.println(applianceTypes);
        
        for (String appliances : applianceTypes) {
            // Mark the selected appliance if it matches the one passed in the query string
            html += "<option value=\"" + appliances.toLowerCase() + "\""
                    + (appliances.equalsIgnoreCase(selectedDepartment) ? " selected" : "")
                    + ">" + appliances + "</option>";
        }
       

        html += "</select>" +
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
