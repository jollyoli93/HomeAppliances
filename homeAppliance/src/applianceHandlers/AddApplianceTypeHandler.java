package applianceHandlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;
import sessionManagement.Session;
import sessionManagement.SessionManager;
import util.WebUtil;

/**
 * Handles HTTP requests for selecting the appliance type based on the selected department.
 * This handler generates a dynamic form based on the department selected by the user.
 * 
 * @author 24862664
 */
public class AddApplianceTypeHandler implements HttpHandler {
    private ApplianceDao applianceDao;
    private SessionManager sessionManager;

    /**
     * Constructor to initialize the handler with the appliance DAO and session manager.
     *
     * @param applianceDao The DAO responsible for appliance-related operations.
     * @param sessionManager The session manager to manage user sessions.
     */
    public AddApplianceTypeHandler(ApplianceDao applianceDao, SessionManager sessionManager) {
        this.applianceDao = applianceDao;
        this.sessionManager = sessionManager;
    }

    /**
     * Handles the incoming HTTP request for selecting an appliance type based on the selected department.
     * It validates the department and generates the appropriate form for selecting an appliance.
     * It also checks the role of the user and generates the form action accordingly.
     * 
     * @param he The HTTP exchange object containing the request and response data.
     * @throws IOException If an I/O error occurs while handling the request or sending the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        // Get the query parameters
        String query = he.getRequestURI().getQuery();
        HashMap<String, String> requestString = WebUtil.requestStringToMap(query);
        String selectedDepartment = requestString.get("department"); // Get selected department
        System.out.println("Selected Department: " + selectedDepartment);

        String role = getSessionRole(he);

        // Determine form action based on role
        String formAction;
        if ("admin".equalsIgnoreCase(role)) {
            formAction = "/appliances/admin/confirm";
        } else if ("customer".equalsIgnoreCase(role) || "business".equalsIgnoreCase(role)) {
            formAction = "/appliances/users/confirm";
        } else {
            he.sendResponseHeaders(403, 0); // Forbidden
            try (OutputStream os = he.getResponseBody()) {
                os.write("<html><body><h1>403 Forbidden</h1><p>Unauthorized access.</p></body></html>".getBytes());
            }
            return;
        }

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
                    "<form method=\"get\" action=\"" + formAction + "\">" + // Dynamic form action
                        "<div class=\"form-group\">" +
                            "<label for=\"department\">Department</label>" +
                            "<input type=\"text\" class=\"form-control\" id=\"department\" name=\"department\" value=\"" + selectedDepartment + "\" readonly>" +
                            "<label>Appliance</label>" +
                            "<select name=\"appliance\" class=\"form-control\" required>";

        ApplianceDepartments applianceFactory;

//        switch (selectedDepartment.toLowerCase()) {
//            case "entertainment":
//                applianceFactory = new EntertainmentFactory();
//                break;
//            case "home cleaning":
//                applianceFactory = new HomeCleaningFactory();
//                break;
//            default:
//                he.sendResponseHeaders(400, 0);
//                try (OutputStream os = he.getResponseBody()) {
//                    os.write("<html><body><h1>400 Bad Request</h1><p>Invalid department selected.</p></body></html>".getBytes());
//                }
//                return;
//        }
        
        // Get the list of departments from the appliance factories
        ArrayList<String> departments = ApplianceDepartments.selectApplianceDepartment("entertainment").listFactoryTypes();
        
        applianceFactory = ApplianceDepartments.selectApplianceDepartment(selectedDepartment);

        ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
        System.out.println(applianceTypes);

        for (String appliance : applianceTypes) {
            html += "<option value=\"" + appliance.toLowerCase() + "\">" + appliance + "</option>";
        }

        html += "</select>" +
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

    /**
     * Retrieves the role of the user from the session.
     * 
     * @param he The HTTP exchange object containing the request data.
     * @return The role of the user (e.g., admin, customer, etc.).
     */
    private String getSessionRole(HttpExchange he) {
        String sessionId = WebUtil.extractSessionId(he);
        Session session = sessionManager.getSession(sessionId);
        String role = (String) session.getAttribute("role");
        
        return role;
    }
}
