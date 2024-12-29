package webpages;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.UserDao;
import users.User;
import DAO.DAO;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.IOException;


public class UsersHandler implements HttpHandler {
  public void handle(HttpExchange he) throws IOException {
    BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(he.getResponseBody()));
    try {
      DAO users = new UserDao("HomeAppliances");
      List<User> allUsers = users.findAll();

      he.sendResponseHeaders(200, 0);

      out.write(
          "<html>" +
          "<head> <title>Customer List</title> " +
          "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
          "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
          "</head>" +
          "<body>" +
          "<div class=\"container\">" +
          "  <h1 class=\"mt-4\">Customer List</h1>" +
          "  <table class=\"table table-striped\">" +
          "    <thead class=\"thead-dark\">" +
          "      <tr>" +
          "        <th>Customer ID</th>" +
          "        <th>First Name</th>" +
          "        <th>Last Name</th>" +
          "        <th>Username</th>" +
          "        <th>Email Address</th>" +
          "        <th>Telephone Number</th>" +
          "        <th>Business Name</th>" +
          "      </tr>" +
          "    </thead>" +
          "    <tbody>");

      if (allUsers != null) {
        for (User user : allUsers) {
          out.write(user.toHTMLString());
        }
      } else {
        out.write("<tr><td colspan=\"7\">No users found.</td></tr>");
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
