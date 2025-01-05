package customerHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import DAO.UserDao;
import sessionManagement.Session;
import sessionManagement.SessionManager;
import users.User;
import util.WebUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.ArrayList;

public class EditUserHandler implements HttpHandler {
    private UserDao userDao;
    private SessionManager sessionManager;
    
    public EditUserHandler(UserDao userDao, SessionManager sessionManager) {
        this.userDao = userDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
        	String sessionId = WebUtil.extractSessionId(he);
        	Session session = sessionManager.getSession(sessionId);
        	
        	int userId = (int) session.getAttribute("userId");

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            User user = userDao.getUser(userId);

            if (user != null) {
                ArrayList<String> userRoles = userDao.getUserRoles(user.getCustomerId());
                StringBuilder html = new StringBuilder();
                html.append("<html>" +
                    "<head><title>Edit User</title>" +
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                    "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                    "</head>" +
                    "<body><div class=\"container\">" +
                    "<h1 class=\"mt-4\">Edit " + user.getUsername() + "</h1>" +
                    "<form method=\"post\" action=\"/update\">" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"customerId\">Customer ID</label>" +
		                    "<input type=\"text\" class=\"form-control\" id=\"customerId\" name=\"customerId\" value=\"" + user.getCustomerId() + "\" readonly>" +
	                    "</div>" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"firstName\">First Name</label>" +
		                    "<input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"firstName\" value=\"" + user.getFirstName() + "\">" +
	                    "</div>" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"lastName\">Last Name</label>" +
		                    "<input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"lastName\" value=\"" + user.getLastName() + "\">" +
	                    "</div>" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"username\">Username</label>" +
		                    "<input type=\"text\" class=\"form-control\" id=\"username\" name=\"username\" value=\"" + user.getUsername() + "\">" +
	                    "</div>" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"emailAddress\">Email Address</label>" +
		                    "<input type=\"email\" class=\"form-control\" id=\"emailAddress\" name=\"emailAddress\" value=\"" + user.getEmailAddress() + "\">" +
	                    "</div>" +
	                    "<div class=\"form-group\">" +
		                    "<label for=\"telephoneNum\">Telephone Number</label>" +
		                    "<input type=\"tel\" class=\"form-control\" id=\"telephoneNum\" name=\"telephoneNum\" value=\"" + user.getTelephoneNum() + "\" pattern=\"\\d{11}\" title=\"Please enter 11 digits\">" +
	                    "</div>");

                if (userRoles.contains("business")) {
                    String businessName = user.getBusinessName();
                    if (businessName != null) {
                        html.append("<div class=\"form-group\">" +
                            "<label for=\"businessName\">Business Name</label>" +
                            "<input type=\"text\" class=\"form-control\" id=\"businessName\" name=\"businessName\" value=\"" + businessName + "\">" +
                            "</div>");
                    }
                }

                html.append(
                    "<button type=\"submit\" class=\"btn btn-success\">Save Changes</button>" +
            		"<a href=\"javascript:window.history.back();\" class=\"btn btn-primary ml-2\">Back</a>" +
                    "</form></div></body></html>");

                out.write(html.toString());
            } else {
                out.write("<html><body><h1>User Not Found</h1></body></html>");
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