package userHandlers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;
import DAO.UserDao;
import users.User;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class ViewCustomerUsersHandler implements HttpHandler {
    private UserDao userDao;
    
    public ViewCustomerUsersHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
        try {
            List<User> allUsers = userDao.findAll();
            he.sendResponseHeaders(200, 0);

            out.write(
                "<html>" +
                "<head><title>Customer and Business List</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1 class=\"mt-4\">Users List</h1>" +
                "<div class=\"mb-3\">" +
                "<a href=\"/admin/users/\" class=\"btn btn-primary\">Go back</a> " +
                "<a href=\"/admin/users/create\" class=\"btn btn-success ml-2\">Create New User</a>" +
                "</div>"
            );

            // Customers Table
            out.write(
                "<h2>Customers</h2>" +
                "<table class=\"table table-striped\">" +
                "<thead class=\"thead-dark\">" +
                "<tr>" +
                "<th>User ID</th>" +
                "<th>Full Name</th>" +
                "<th>Username</th>" +
                "<th>Email Address</th>" +
                "<th>Telephone Number</th>" +
                "<th>Actions</th>" +
                "<th>Admin</th>" + 
                "<th>Addresses</th>" + 
                "</tr>" +
                "</thead>" +
                "<tbody>"
            );

            if (allUsers != null) {
                for (User user : allUsers) {
                    ArrayList<String> userRoles = userDao.getUserRoles(user.getCustomerId());
                    if (userRoles.contains("customer")) {
                        out.write(
                            "<tr>" +
                            "<td>" + user.getCustomerId() + "</td>" +
                            "<td>" + user.getFullName() + "</td>" +
                            "<td>" + user.getUsername() + "</td>" +
                            "<td>" + user.getEmailAddress() + "</td>" +
                            "<td>" + user.getTelephoneNum() + "</td>" +
                            "<td class=\"d-flex\">" +
                            "<a href=\"/admin/users/edit?id=" + user.getCustomerId() + "\" class=\"btn btn-warning btn-sm mr-1\">Edit</a>" +
                            "<a href=\"/admin/users/delete-confirm?id=" + user.getCustomerId() + "\" class=\"btn btn-danger btn-sm mr-1\">Delete</a>" +
                            "</td>" +
	                        "<td>"+
	                        	adminOption(user)+
                            "</td>"+
                            "<td>"+
                        		"<a href=\"/admin/users/view-address?id=" + user.getCustomerId() + "\" class=\"btn btn-info btn-sm mr-1\">View</a>" +
                        	"</td>"+
                            "</tr>"
                        );
                    }
                }
            } else {
                out.write("<tr><td colspan=\"6\">No customers found.</td></tr>");
            }

            // Business Table
            out.write(
                "</tbody>" +
                "</table>" +
                "<h2>Business</h2>" +
                "<table class=\"table table-striped\">" +
                "<thead class=\"thead-dark\">" +
                "<tr>" +
                "<th>User ID</th>" +
                "<th>Full Name</th>" +
                "<th>Username</th>" +
                "<th>Email Address</th>" +
                "<th>Telephone Number</th>" +
                "<th>Business Name</th>" +
                "<th>Actions</th>" +
                "<th>Admin</th>" +
                "<th>Addresses</th>" + 
                "</tr>" +
                "</thead>" +
                "<tbody>"
            );

            if (allUsers != null) {
                for (User user : allUsers) {
                    ArrayList<String> userRoles = userDao.getUserRoles(user.getCustomerId());
                    if (userRoles.contains("business")) {
                        out.write(
                            "<tr>" +
                            "<td>" + user.getCustomerId() + "</td>" +
                            "<td>" + user.getFullName() + "</td>" +
                            "<td>" + user.getUsername() + "</td>" +
                            "<td>" + user.getEmailAddress() + "</td>" +
                            "<td>" + user.getTelephoneNum() + "</td>" +
                            "<td>" + (user.getBusinessName() != null ? user.getBusinessName() : "") + "</td>" +
                            "<td class=\"d-flex\">" +
	                            "<a href=\"/admin/users/edit?id=" + user.getCustomerId() + "\" class=\"btn btn-warning btn-sm mr-1\">Edit</a>" +
	                            "<a href=\"/admin/users/delete?id=" + user.getCustomerId() + "\" class=\"btn btn-danger btn-sm mr-1\">Delete</a>" +
                            "</td>" +
	                        "<td>"+
	                        	adminOption(user)+
                            "</td>"+
                            "<td>"+
                            	"<a href=\"/admin/users/view-address?id=" + user.getCustomerId() + "\" class=\"btn btn-info btn-sm mr-1\">View</a>" +
                            "</td>"+
                            "</tr>"
                        );
                    }
                }
            } else {
                out.write("<tr><td colspan=\"7\">No businesses found.</td></tr>");
            }

            out.write(
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

        } catch (Exception e) {
            he.sendResponseHeaders(500, 0);
            out.write("<html><body><h1>Error fetching user data</h1></body></html>");
            e.printStackTrace();
        } finally {
            out.close();
        }
       
    }
    
    private String adminOption(User user) {
        if (userDao.isUserAdmin(user.getCustomerId())) {
            return "<form action=\"/admin/users/promote\" method=\"post\" class=\"d-inline-block\">" +
                    "<input type=\"hidden\" name=\"id\" value=\"" + user.getCustomerId() + "\">" +
                    "<button type=\"submit\" class=\"btn btn-danger btn-sm\">Remove</button>" +
                    "</form>";
        } else {
            return "<form action=\"/admin/users/promote\" method=\"post\" class=\"d-inline-block\">" +
                   "<input type=\"hidden\" name=\"id\" value=\"" + user.getCustomerId() + "\">" +
                   "<button type=\"submit\" class=\"btn btn-info btn-sm\">Promote</button>" +
                   "</form>";
        }
    }
}