package webHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;

import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.Map;

import appliances.Appliance;
import appliances.ApplianceDepartments;
import util.WebUtil;

public class AddApplianceConfirmHandler implements HttpHandler {
	private ApplianceDao applianceDao;
	private ApplianceDepartments department;
	private Appliance appliance;
	
	public AddApplianceConfirmHandler(ApplianceDao applianceDao) {
		this.applianceDao = applianceDao;
	}

    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
               
        try {
            String query = he.getRequestURI().getQuery();
            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
            String selectedDepartment = queryParams.get("department");
            String applianceType = queryParams.get("appliance");
            
            System.out.println("Debug: " + selectedDepartment + " " + applianceType);
            
            //select department
            department = ApplianceDepartments.selectApplianceDepartment(selectedDepartment);
            System.out.println(department);
            
            //select appliance
            appliance = department.selectAppliance(applianceType);
            System.out.println(appliance);
            
            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
            
            if ( appliance != null) {
                out.write(
                    "<html>" +
                    "<head> <title>Edit Appliance</title> " +
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "  <h1 class=\"mt-4\">Add Appliance</h1>" +
                    "  <form method=\"post\" action=\"/admin/appliances/add-confirm\">" +
                    "    <div class=\"form-group\">" +
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
                    "    <button type=\"submit\" class=\"btn btn-success\">Save Changes</button>" +
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
}
