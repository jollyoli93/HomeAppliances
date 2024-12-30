package webpages;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;

import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.HashMap;

import appliances.ApplianceFactory;
import util.WebUtil;

public class AddTypeHandler implements HttpHandler {
	private ApplianceDao applianceDao;
	
	public AddTypeHandler(ApplianceDao applianceDao) {
		this.applianceDao = applianceDao;
	}
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Get the query parameters
        String query = he.getRequestURI().getQuery();
        HashMap<String, String> requestString = WebUtil.requestStringToMap(query);
        String selectedDepartment = requestString.get("department");
        System.out.println(selectedDepartment);
        
        
        // Get the appliance types for the selected department
        ApplianceFactory factory = ApplianceFactory.selectApplianceFactory(selectedDepartment);
        System.out.println("Print factory: " + factory);
        
        ArrayList<String> types = factory.listAllApplianceTypes();
        System.out.println("Print factory: " + types);
        
        // Generate appliance type options
        StringBuilder typeOptions = new StringBuilder();
        for (String type : types) {
            typeOptions.append("<option value=\"")
                       .append(type.toLowerCase())
                       .append("\">")
                       .append(type)
                       .append("</option>");
        }

        // Generate HTML for the appliance type selection form
        String html = "<html>" +
            "<head>" +
                "<title>Add New Appliance</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
            "</head>" +
            "<body>" +
                "<div class=\"container mt-4\">" +
                    "<h2>Add New Appliance</h2>" +
                    "<form method=\"post\" action=\"/admin/appliances/add/submit\">" + // Submit the form to another endpoint
                        "<div class=\"form-group\">" +
                            "<label>Type</label>" +
                            "<select name=\"type\" class=\"form-control\" required>" +
                            typeOptions +
                            "</select>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                            "<label>Quantity</label>" +
                            "<input type=\"number\" name=\"quantity\" class=\"form-control\" required min=\"1\">" +
                        "</div>" +
                        "<button type=\"submit\" class=\"btn btn-primary\">Add Appliance</button>" +
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
