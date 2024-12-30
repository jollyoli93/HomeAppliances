package webpages;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import appliances.Appliance;
import appliances.ApplianceFactory;
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
            "<body>" +
                "<div class=\"container mt-4\">" +
                    "<h2>Select Appliance Type</h2>" +
                    "<form method=\"get\" action=\"/admin/appliances/add/type\">" +
                        "<div class=\"form-group\">" +
                            "<label>Department</label>" +
                            "<select name=\"department\" class=\"form-control\" required>";

        // Dynamically populate the department options
        // Assuming you have a list of departments (replace this with actual data if necessary)
        //String[] departments = {"Entertainment", "Home Appliances", "Kitchen", "Cleaning"};

		ApplianceFactory applianceFactory = null;        
        
        switch (selectedDepartment)  {
        case "entertainment":
			applianceFactory = new EntertainmentFactory();
			break;
		default:
			applianceFactory = new EntertainmentFactory();
			break;
        }
        
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		System.out.println(applianceTypes);
        
        for (String appliances : applianceTypes) {
            // Mark the selected department if it matches the one passed in the query string
            html += "<option value=\"" + appliances.toLowerCase() + "\""
                    + (appliances.equalsIgnoreCase(selectedDepartment) ? " selected" : "")
                    + ">" + appliances + "</option>";
        }
        
        //to add appliance type
        //appliance = applianceFactory.selectAppliance(applianceTypes.get(userInput -1));

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
