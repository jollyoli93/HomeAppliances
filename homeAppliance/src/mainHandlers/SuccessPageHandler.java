package mainHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class SuccessPageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            out.write(
                "<html>" +
                "<head> <title>Successful</title> " +
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
                "<li class=\"nav-item active\"><a class=\"nav-link\" href=\"/admin\">Dashboard</a></li>" +
                "    </ul>" +
                "  </div>" +
                "</nav>" +
                "<div class=\"container\">" +
                "  <h1 class=\"mt-4\">Operation Successful</h1>" +
                "  <p class=\"lead\">Your operation was completed successfully. You can choose what to do next:</p>" +
                "  <div class=\"mb-4\">" +
                "    <a href=\"/admin\" class=\"btn btn-primary\">Back to Admin Dashboard</a>" +
                "    <a href=\"/admin/appliances\" class=\"btn btn-success ml-2\">View Appliances</a>" +
                "    <a href=\"/admin/users\" class=\"btn btn-success ml-2\">View Users</a>" +
                "  </div>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0); 
            if (out != null) {
                out.write("<html><body><h1>Internal Server Error</h1><p>Could not load success page.</p></body></html>");
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
