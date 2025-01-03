package userHandlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import util.WebUtil;

public class CreateUserHandler implements HttpHandler {
    private UserDao userDao;

    public CreateUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

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

                html.append("<html>" +
                        "<head><title>Create " + type + "</title>" +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                        "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                        "</head>" +
                        "<body><div class=\"container\">" +
                        "<h1 class=\"mt-4\">Create " + type + " account </h1>" +
                        "<form method=\"post\" action=\"/admin/users/add-confirm\">" +
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

                if (type.contains("customer") || type.contains("business")) {
                    html.append("<div class=\"form-group\">" +
                            "<label for=\"telephoneNum\">Telephone Number</label>" +
                            "<input type=\"tel\" class=\"form-control\" id=\"telephoneNum\" name=\"telephoneNum\" pattern=\"\\d{11}\" title=\"Please enter 11 digits\">" +
                            "</div>");
                }

                if (type.contains("business")) {
                    html.append("<div class=\"form-group\">" +
                            "<label for=\"businessName\">Business Name</label>" +
                            "<input type=\"text\" class=\"form-control\" id=\"businessName\" name=\"businessName\">" +
                            "</div>");
                }

                html.append("<div class=\"form-group\">" +
                        "<label for=\"password\">Password</label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" required>" +
                        "</div>" +
                        "<div class=\"form-group\">" +
                        "<label for=\"confirmPassword\">Confirm Password</label>" +
                        "<input type=\"password\" class=\"form-control\" id=\"confirmPassword\" name=\"confirmPassword\" required>" +
                        "</div>");

                html.append("<button type=\"submit\" class=\"btn btn-success\">Create User</button>" +
                        "<a href=\"javascript:window.history.back();\" class=\"btn btn-secondary ml-2\">Cancel</a>" +
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
