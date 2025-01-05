package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import DAO.UserDao;
import users.User;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles HTTP requests for displaying and managing customer and business users.
 * It fetches all users from the database, sorts them based on the username,
 * and displays their details categorized as either customer or business.
 * The page also allows actions such as editing, deleting, promoting, and viewing addresses.
 * 
 * @author 24862664
 */
public class ViewCustomerUsersHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructs a ViewCustomerUsersHandler with a specified UserDao.
     * 
     * @param userDao the DAO object for interacting with user data
     */
    public ViewCustomerUsersHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Handles HTTP requests to display a list of customer and business users.
     * It sorts the users based on the provided query parameter (ascending or descending)
     * and generates an HTML response showing users categorized as customers or businesses.
     * Each user has associated actions such as edit, delete, and promote.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data
     * @throws IOException if an I/O error occurs during the response writing
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        try {
            // Get the query parameter for sorting order (ascending or descending)
            String query = he.getRequestURI().getQuery();
            String sortOrder = "asc"; // Default to ascending
            if (query != null && query.contains("sort=desc")) {
                sortOrder = "desc"; // Sort by descending if query contains sort=desc
            }

            // Send a successful response and start writing the HTML content
            he.sendResponseHeaders(200, 0);

            List<User> allUsers;
            // Get users based on the sorting order
            if ("desc".equals(sortOrder)) {
                allUsers = userDao.getUsersSortedByUsernameDesc();
            } else {
                allUsers = userDao.getUsersSortedByUsernameAsc();
            }

            // Start the HTML response with Bootstrap styling
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
                "<a href=\"javascript:window.history.back();\" class=\"btn btn-danger ml-2\">Back</a>" +
                "<a href=\"/admin/users/add-select\" class=\"btn btn-success ml-2\">Create New User</a>" +
                "</div>"
            );

            // Sorting options for username
            out.write(
                "<div class=\"mb-3\">" +
                "<a href=\"?sort=asc\" class=\"btn btn-primary\">Sort by Username (Ascending)</a>" +
                "<a href=\"?sort=desc\" class=\"btn btn-primary ml-2\">Sort by Username (Descending)</a>" +
                "</div>"
            );

            // Display Customers Table
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

            // Iterate over users and display customer users
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
                                adminOption(user) +
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

            // Display Business Table
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

            // Iterate over users and display business users
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
                                adminOption(user) +
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
            // In case of an error, send a 500 Internal Server Error response
            he.sendResponseHeaders(500, 0);
            out.write("<html><body><h1>Error fetching user data</h1></body></html>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    /**
     * Generates the HTML for the "Promote" or "Remove" button depending on the user's admin status.
     * 
     * @param user the {@link User} object representing the user
     * @return a string containing the HTML for the promote/remove button
     */
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
