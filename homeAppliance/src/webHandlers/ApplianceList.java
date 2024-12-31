package webHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;



public class ApplianceList implements HttpHandler {
	private ApplianceDao applianceDao;
	
	public ApplianceList(ApplianceDao applianceDao) {
		this.applianceDao = applianceDao;
	}
	
	@Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            List<Appliance> allAppliances = applianceDao.findAll();

            out.write(
                "<html>" +
                "<head> <title>Appliance Library</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "  <a class=\"navbar-brand\" href=\"/\">Home Appliance Store</a>" +
                "  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" " +
                "    aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                "    <span class=\"navbar-toggler-icon\"></span>" +
                "  </button>" +
                "  <div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                "    <ul class=\"navbar-nav\">" +
                "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"/\">Home</a></li>" +
                "    </ul>" +
                "  </div>" +
                "</nav>" +
                "<div class=\"container\">" +
                "  <h1 class=\"mt-4\">Welcome to the Home Appliance Store</h1>" + 
                "  <a href=\"/admin\" class=\"btn btn-primary mb-4\">Back to Admin Dashboard</a>" +
                "  <h2>Appliances in Stock</h2>" +
                "  <table class=\"table table-striped\">" +
                "    <thead class=\"thead-dark\">" +
                "      <tr>" +
                "        <th>ID</th>" +
                "        <th>Description</th>" +
                "        <th>Category</th>" +
                "        <th>Price</th>" +
                "        <th>SKU</th>" +
                "        <th>Actions</th>" +
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
                    "<a href=\"/admin/appliances/edit?id=" + a.getId() + "\" class=\"btn btn-warning btn-sm\">Edit</a> " +
                    "<a href=\"/admin/appliances/delete-confirm?id=" + a.getId() + "\" class=\"btn btn-danger btn-sm\">Delete</a>" +
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
            he.sendResponseHeaders(500, 0); 
            if (out != null) {
                out.write("<html><body><h1>Internal Server Error</h1><p>Could not load appliances.</p></body></html>");
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
