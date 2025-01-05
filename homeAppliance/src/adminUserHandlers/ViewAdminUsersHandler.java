package adminUserHandlers;

import java.io.*;
import com.sun.net.httpserver.HttpHandler;
import DAO.UserDao;
import users.User;
import com.sun.net.httpserver.HttpExchange;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the HTTP requests for viewing and managing admin users.
 * It retrieves all users, filters them by role (admin), and displays
 * their information in an HTML page with options to edit or delete the admin.
 * 
 * The page also provides options to create a new admin or navigate back to the user list.
 * 
 * @author 24862664
 */
public class ViewAdminUsersHandler implements HttpHandler {
    private UserDao userDao;

    /**
     * Constructs a ViewAdminUsersHandler with a specified UserDao.
     * 
     * @param userDao the DAO object for interacting with the user data
     */
    public ViewAdminUsersHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Handles the HTTP request to display a list of admin users.
     * It retrieves all users, filters them by the "admin" role, and displays
     * their information in a table format in an HTML page.
     * 
     * The page also includes options to edit or delete each admin user, as well as
     * a link to create a new admin user.
     * 
     * @param he the {@link HttpExchange} object containing the request and response data
     * @throws IOException if an I/O error occurs during the response writing
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

        try {
            // Retrieve all users from the UserDao
            List<User> allUsers = userDao.findAll(0, null);

            // Send a successful response and start writing HTML
            he.sendResponseHeaders(200, 0);
            out.write(
                "<html>" +
                "<head><title>Admin List</title>" +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<h1 class=\"mt-4\">Admin List</h1>" +
                "<a href=\"/admin/users/\" class=\"btn btn-primary mb-3\">Go back</a>" +
                "<a href=\"/admin/users/add?type=admin\" class=\"btn btn-success mb-3\">Create New Admin</a>" + 
                "<h2>Admin</h2>" +
                "<table class=\"table table-striped\">" +
                "<thead class=\"thead-dark\">" +
                "<tr>" +
                "<th>User ID</th>" +
                "<th>Full Name</th>" +
                "<th>Username</th>" +
                "<th>Email Address</th>" +
                "<th>Actions</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>"
            );

            // Display admins from the list of all users
            if (allUsers != null) {
                for (User user : allUsers) {
                    // Fetch roles for each user
                    ArrayList<String> userRoles = userDao.getUserRoles(user.getCustomerId());

                    // Filter only admin users
                    if (userRoles.contains("admin")) {
                        out.write(
                            "<tr>" +
                            "<td>" + user.getCustomerId() + "</td>" +
                            "<td>" + user.getFullName() + "</td>" +
                            "<td>" + user.getUsername() + "</td>" +
                            "<td>" + user.getEmailAddress() + "</td>" +
                            "<td>" +
                            "<a href=\"/admin/users/edit?id=" + user.getCustomerId() + "\" class=\"btn btn-warning btn-sm\">Edit</a> " +
                            "<a href=\"/admin/users/delete-confirm?id=" + user.getCustomerId() + "\" class=\"btn btn-danger btn-sm\">Delete</a>" +
                            "</td>" +
                            "</tr>"
                        );
                    }
                }
            } else {
                out.write("<tr><td colspan=\"6\">No admins found.</td></tr>");
            }

            // End of HTML content
            out.write(
                "</tbody>" +
                "</table>" +
                "</div>" +
                "</body>" +
                "</html>"
            );
        } catch (Exception e) {
            // In case of an error, return an internal server error response
            he.sendResponseHeaders(500, 0);
            out.write("<html><body><h1>Error fetching user data</h1></body></html>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
