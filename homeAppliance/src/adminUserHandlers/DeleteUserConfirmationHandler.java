package adminUserHandlers;

import DAO.UserDao;
import users.User;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DeleteUserConfirmationHandler implements HttpHandler {
    private UserDao userDao;
    
    public DeleteUserConfirmationHandler(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        int id = Integer.parseInt(he.getRequestURI().getQuery().split("=")[1]);
        User user = userDao.getUser(id);
        
        String html = "<html>" +
        	    "<head>" +
        	        "<title>Confirm Delete</title>" +
        	        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\">" +
        	    "</head>" +
        	    "<body>" +
        	        "<div class=\"container mt-5\">" +
        	            "<div class=\"card\">" +
        	                "<div class=\"card-body\">" +
        	                    "<h3 class=\"card-title text-danger\">Confirm Delete</h3>" +
        	                    "<p>Are you sure you want to delete this user?</p>" +
        	                    "<p><strong>Full Name:</strong> " + user.getFullName() + "</p>" +
        	                    "<p><strong>Username:</strong> " + user.getUsername() + "</p>" +
        	                    "<p><strong>Email:</strong> " + user.getEmailAddress() + "</p>" +
        	                    "<p><strong>Customer ID:</strong> " + user.getCustomerId() + "</p>" +
        	                    "<p><strong>Telephone Number:</strong> " + (user.getTelephoneNum() != null ? user.getTelephoneNum() : "N/A") + "</p>" +
        	                    "<p><strong>Business Name:</strong> " + (user.getBusinessName() != null ? user.getBusinessName() : "N/A") + "</p>" +

        	                    "<form method=\"post\" action=\"/admin/users/delete\" style=\"display: inline;\">" +
        	                        "<input type=\"hidden\" name=\"id\" value=\"" + user.getCustomerId() + "\">" +
        	                        "<button type=\"submit\" class=\"btn btn-danger\">Delete</button>" +
        	                        "<a href=\"/admin/users\" class=\"btn btn-secondary ml-2\">Cancel</a>" +
        	                    "</form>" +
        	                "</div>" +
        	            "</div>" +
        	        "</div>" +
        	    "</body>" +
        	"</html>";

        
        he.sendResponseHeaders(200, html.length());
        OutputStream os = he.getResponseBody();
        os.write(html.getBytes());
        os.close();
    }
}