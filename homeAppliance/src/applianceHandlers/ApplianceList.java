package applianceHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Handles HTTP requests for displaying a list of appliances.
 * This handler generates an HTML page showing a list of appliances, with the ability to filter and sort them.
 * 
 * @author 24862664
 */
public class ApplianceList implements HttpHandler {
    private ApplianceDao applianceDao;

    /**
     * Constructor to initialize the handler with the appliance DAO.
     *
     * @param applianceDao The DAO responsible for appliance-related operations.
     */
    public ApplianceList(ApplianceDao applianceDao) {
        this.applianceDao = applianceDao;
    }

    /**
     * Handles the incoming HTTP request and generates an HTML response displaying a list of appliances.
     * The appliances are sorted and filtered based on the query parameters (e.g., sorting by price, filtering by category).
     * 
     * @param he The HTTP exchange object containing the request and response data.
     * @throws IOException If an I/O error occurs while handling the request or sending the response.
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            // Read the query parameters
            String query = he.getRequestURI().getQuery();
            String sortOrder = "asc"; // Default to ascending
            if (query != null && query.contains("sort=desc")) {
                sortOrder = "desc"; // Sort by descending if the query contains sort=desc
            }

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            List<Appliance> allAppliances;
            if ("desc".equals(sortOrder)) {
                allAppliances = applianceDao.getAppliancesByPriceDesc();
            } else {
                allAppliances = applianceDao.getAppliancesByPriceAsc();
            }

            // Write the HTML response with the appliance list and filtering options
            out.write(
                "<html>" +
                "<head> <title>Appliance Stock</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +

                // JavaScript function to filter products
                "<script>" +
                "function filterAppliances() {" +
                "    const category = document.getElementById('filter-category').value.toLowerCase();" +
                "    const minPrice = parseFloat(document.getElementById('filter-min-price').value) || 0;" +
                "    const maxPrice = parseFloat(document.getElementById('filter-max-price').value) || Number.MAX_VALUE;" +
                "    const rows = document.querySelectorAll('#appliance-table tbody tr');" +
                "    rows.forEach(row => {" +
                "        const rowCategory = row.dataset.category.toLowerCase();" +
                "        const rowPrice = parseFloat(row.dataset.price);" +
                "        if (rowCategory.includes(category) && rowPrice >= minPrice && rowPrice <= maxPrice) {" +
                "            row.style.display = '';" +
                "        } else {" +
                "            row.style.display = 'none';" +
                "        }" +
                "    });" +
                "}" +
                "</script>" +

                "<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">" +
                "  <a class=\"navbar-brand\" href=\"/\">Home Appliance Store</a>" +
                "  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNav\" " +
                "    aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">" +
                "    <span class=\"navbar-toggler-icon\"></span>" +
                "  </button>" +
                "  <div class=\"collapse navbar-collapse\" id=\"navbarNav\">" +
                "    <ul class=\"navbar-nav\">" +
                "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"/\">Home</a></li>" +
                "      <li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
                "    </ul>" +
                "  </div>" +
                "</nav>" +
                "<div class=\"container\">" +
                "  <h1 class=\"mt-4\">Welcome to the Home Appliance Store</h1>" +
                "  <a href=\"/admin\" class=\"btn btn-primary mb-4\">Back to Admin Dashboard</a>" +
                "  <h2>Appliances in Stock</h2>" +
                "  <div class=\"mb-3\">" +
                "    <a href=\"?sort=asc\" class=\"btn btn-primary\">Sort by Price (Ascending)</a>" +
                "    <a href=\"?sort=desc\" class=\"btn btn-primary\">Sort by Price (Descending)</a>" +
                "  </div>" +
                "  <div class=\"mb-3\">" +
                "    <label>Filter by Category:</label>" +
                "    <input type=\"text\" id=\"filter-category\" onkeyup=\"filterAppliances()\" class=\"form-control mb-2\" placeholder=\"e.g., Kitchen\">" +
                "    <label>Filter by Price:</label>" +
                "    <input type=\"number\" id=\"filter-min-price\" onkeyup=\"filterAppliances()\" class=\"form-control mb-2\" placeholder=\"Min Price\">" +
                "    <input type=\"number\" id=\"filter-max-price\" onkeyup=\"filterAppliances()\" class=\"form-control mb-2\" placeholder=\"Max Price\">" +
                "  </div>" +
                "  <table id=\"appliance-table\" class=\"table table-striped\">" +
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

            // Loop through all appliances and create a table row for each one
            for (Appliance a : allAppliances) {
                out.write(
                    "<tr data-category=\"" + a.getCategory() + "\" data-price=\"" + a.getPrice() + "\">" +
                    "<td>" + a.getId() + "</td>" +
                    "<td>" + a.getDescription() + "</td>" +
                    "<td>" + a.getCategory() + "</td>" +
                    "<td>" + a.getPrice() + "</td>" +
                    "<td>" + a.getSku() + "</td>" +
                    "<td>" +
                    "<a href=\"/admin/appliances/edit?id=" + a.getId() + "\" class=\"btn btn-warning btn-sm\">Edit</a> " +
                    "<a href=\"/admin/appliances/delete-confirm?id=" + a.getId() + "\" class=\"btn btn-danger btn-sm\">Delete</a>" +
                    "</td>" +
                    "</tr>");
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
