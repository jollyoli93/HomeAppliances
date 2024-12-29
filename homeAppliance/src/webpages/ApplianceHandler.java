package webpages;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;
import appliances.ApplianceFactory;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.IOException;

public class ApplianceHandler implements HttpHandler {
    Map<String, ApplianceFactory> applianceFactories = new HashMap<String, ApplianceFactory>();

    private void initFactoriesMap() {
        ApplianceFactory entertainment = new EntertainmentFactory();
        ApplianceFactory homeCleaning = new HomeCleaningFactory();
        
        applianceFactories.put("Entertainment", entertainment);
        applianceFactories.put("Home Cleaning", homeCleaning);
    }
    
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            initFactoriesMap();

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            DAO appliances = new ApplianceDao("HomeAppliances", null);
            List<Appliance> allAppliances = appliances.findAll();

            out.write(
                "<html>" +
                "<head> <title>Appliance Library</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "  <h1 class=\"mt-4\">Welcome to the Home Appliance Store</h1>" +  // Homepage Header
                "  <a href=\"/\" class=\"btn btn-primary mb-4\">Back to Homepage</a>" + // Homepage Link
                "  <h2>Appliances in Stock</h2>" +
                "  <table class=\"table table-striped\">" +
                "    <thead class=\"thead-dark\">" +
                "      <tr>" +
                "        <th>ID</th>" +
                "        <th>Description</th>" +
                "        <th>Category</th>" +
                "        <th>Price</th>" +
                "        <th>SKU</th>" +
                "        <th>Actions</th>" +  // New column for buttons
                "      </tr>" +
                "    </thead>" +
                "    <tbody>");

            // Loop through all appliances and display them with Edit/Delete buttons
            for (Appliance a : allAppliances) {
                out.write(
                    "<tr>" +
                    "<td>" + a.getId() + "</td>" +
                    "<td>" + a.getDescription() + "</td>" +
                    "<td>" + a.getCategory() + "</td>" +
                    "<td>" + a.getPrice() + "</td>" +
                    "<td>" + a.getSku() + "</td>" +
                    "<td>" +
                    "<a href=\"/admin/products/edit?id=" + a.getId() + "\" class=\"btn btn-warning btn-sm\">Edit</a> " + // Edit button
                    "<a href=\"/admin/products/delete?id=" + a.getId() + "\" class=\"btn btn-danger btn-sm\">Delete</a>" + // Delete button
                    "</td>" +
                    "</tr>"
                );
            }

            out.write(
                "</tbody>" +
                "  </table>" +
                "</div>" +
                "</body>" +
                "</html>");

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0); // Send an HTTP 500 response code
            if (out != null) {
                out.write("<html><body><h1>Internal Server Error</h1><p>Could not load appliances.</p></body></html>");
            }
            e.printStackTrace(); // Log the error to the console

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace(); // Log any error while closing the writer
                }
            }
        }
    }
}
