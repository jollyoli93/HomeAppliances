package userHandlers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import users.User;
import DAO.ApplianceDao;
import DAO.DAO;

import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class ViewAdminUsersHandler implements HttpHandler {
	private UserDao userDao;
	
  public ViewAdminUsersHandler(UserDao userDao) {
	  this.userDao = userDao;
  	}

public void handle(HttpExchange he) throws IOException {
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));
    try {
      List<User> allUsers = userDao.findAll();

      he.sendResponseHeaders(200, 0);

      // HTML start
      out.write(
          "<html>" +
          "<head> <title>Admin List</title> " +
          "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
          "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
          "</head>" +
          "<body>" +
          "<div class=\"container\">" +
          "  <h1 class=\"mt-4\">Admin List</h1>" +
          "  <a href=\"/admin/users/\" class=\"btn btn-primary mb-3\">Go back</a>" + 
          "  <a href=\"/admin/users/add?type=admin\" class=\"btn btn-success mb-3\">Create New Admin</a>" + // Link to user creation page
          "  <h2>Admin</h2>" +
          "  <table class=\"table table-striped\">" +
          "    <thead class=\"thead-dark\">" +
          "      <tr>" +
          "        <th>User ID</th>" +
          "        <th>Full Name</th>" +
          "        <th>Username</th>" +
          "        <th>Email Address</th>" +
          "        <th>Actions</th>" +
          "      </tr>" +
          "    </thead>" +
          "    <tbody>");
      
      if (allUsers != null) {
        for (User user : allUsers) {
          ArrayList<String> userRoles = userDao.getUserRoles(user.getCustomerId());
          
          // Display only customers
          if (userRoles.contains("admin")) {
            out.write("<tr>" +
                      "<td>" + user.getCustomerId() + "</td>" + 
                      "<td>" + user.getFullName() + "</td>" + 
                      "<td>" + user.getUsername() + "</td>" + 
                      "<td>" + user.getEmailAddress() + "</td>" + 
                      "<td>" + 
                      "<a href=\"/admin/users/edit?id=" + user.getCustomerId() + "\" class=\"btn btn-warning btn-sm\">Edit</a> " +
                      "<a href=\"/admin/users/delete-confirm?id=" + user.getCustomerId() + "\" class=\"btn btn-danger btn-sm\">Delete</a>" + 
                      "</td>" +
                      "</tr>");
          }
        }
      } else {
        out.write("<tr><td colspan=\"6\">No admins found.</td></tr>");
      }

      out.write(
          "</tbody>" +
          "</table>" +
          "</div>" +
          "</body>" +
          "</html>");
    } catch (Exception e) {
      he.sendResponseHeaders(500, 0);
      out.write("<html><body><h1>Error fetching user data</h1></body></html>");
      e.printStackTrace();
    } finally {
      out.close();
    }
  }
}
