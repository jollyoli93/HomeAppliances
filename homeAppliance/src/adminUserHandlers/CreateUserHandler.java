package adminUserHandlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import util.WebUtil;

/**
 * Handles HTTP requests for rendering the user creation form.
 * 
 * <p>Generates an HTML form for creating users of various types (admin, customer, or business).</p>
 * 
 * @author 24862664
 */
public class CreateUserHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructs a handler for creating users.
     * 
     * @param userDao the DAO used for user operations
     */
    public CreateUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Processes the HTTP request and generates an HTML form for user creation.
     * 
     * @param he the {@link HttpExchange} object containing the request and response
     * @throws IOException if an I/O error occurs during response writing
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            String query = he.getRequestURI().getQuery();
            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
            String type = queryParams.get("type");

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            if (type != null) {
                StringBuilder html = new StringBuilder();

                // HTML form construction
                html.append("<html>" +
                        "<head><title>Create " + type + "</title>" +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                        "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                        "</head>" +
                        "<body><div class=\"container\">" +
                        "<h1 class=\"mt-4\">Create " + type + " account </h1>" +
                        "<form method=\"post\" action=\"/users/add-confirm\">" +
                        "<input type=\"hidden\" name=\"type\" value=\"" + type + "\">" +
                        "<div class=\"form-group\">" +
                        "<label for=\"firstName\">First Name</label>" +
                        "<input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"firstName\" required>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label for=\"lastName\">Last Name</label>" +
                        "<input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"lastName\" required>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label for=\"username\">Username</label>" +
                        "<input type=\"text\" class=\"form-control\" id=\"username\" name=\"username\" required>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label for=\"emailAddress\">Email Address</label>" +
                        "<input type=\"email\" class=\"form-control\" id=\"emailAddress\" name=\"emailAddress\" required>" +
                        "</div>");

                // Additional fields for customer or business users
                if (type.contains("customer") || type.contains("business")) {
                    html.append("<div class=\"form-group\">" +
                            "<label for=\"telephoneNum\">Telephone Number</label>" +
                            "<input type=\"tel\" class=\"form-control\" id=\"telephoneNum\" name=\"telephoneNum\" pattern=\"\\d{11}\" title=\"Please enter 11 digits\">" +
                            "</div>");
                }

                // Additional field for business users
                if (type.contains("business")) {
                    html.append("<div class=\"form-group\">" +
                            "<label for=\"businessName\">Business Name</label>" +
                            "<input type=\"text\" class=\"form-control\" id=\"businessName\" name=\"businessName\">" +
                            "</div>");
                }

                // Password fields
                html.append("<div class=\"form-group\">" +
                        "<label for=\"password\">Password</label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" required>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label for=\"confirmPassword\">Confirm Password</label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"confirmPassword\" name=\"confirmPassword\" required>" +
                        "</div>");

                // Submit and Back buttons
                html.append("<button type=\"submit\" class=\"btn btn-success\">Create User</button>" +
                        "<a href=\"javascript:window.history.back();\" class=\"btn btn-secondary ml-2\">Back</a>" +
                        "</form></div></body></html>");

                out.write(html.toString());
            } else {
                out.write("<html><body><h1>Invalid User Type</h1></body></html>");
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
