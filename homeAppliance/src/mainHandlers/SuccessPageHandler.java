package mainHandlers;

import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import sessionManagement.Session;
import sessionManagement.SessionManager;
import util.WebUtil;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedWriter;
import java.io.IOException;

public class SuccessPageHandler implements HttpHandler {
    private final SessionManager sessionManager;

    public SuccessPageHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            String sessionId = WebUtil.extractSessionId(he);
            Session session = sessionManager.getSession(sessionId);

            if (session == null) {
                he.sendResponseHeaders(401, 0);
                return;
            }

            String role = (String) session.getAttribute("role");

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            // Dynamic content based on role
            StringBuilder content = new StringBuilder();
            if ("admin".equalsIgnoreCase(role)) {
                content.append(
                    "<a href=\"/admin\" class=\"btn btn-primary\">Back to Admin Dashboard</a>" +
                    "<a href=\"/admin/appliances\" class=\"btn btn-success ml-2\">View Appliances</a>" +
                    "<a href=\"/admin/users\" class=\"btn btn-success ml-2\">View Users</a>"
                );
            } else if ("customer".equalsIgnoreCase(role) || "business".equalsIgnoreCase(role)) {
                content.append(
                    "<a href=\"/\" class=\"btn btn-primary\">Back to Homepage</a>"

                );
            } else {
                content.append("<p class=\"text-danger\">Unknown role. Please contact support.</p>");
            }

            // Write complete HTML
            out.write(
                "<html>" +
                "<head> <title>Operation Successful</title> " +
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
                "  <h1 class=\"mt-4\">Operation Successful</h1>" +
                "  <p class=\"lead\">Your operation was completed successfully. You can choose what to do next:</p>" +
                "  <div class=\"mb-4\">" +
                content +
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
