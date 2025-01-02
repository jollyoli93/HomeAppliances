package userHandlers;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import DAO.UserDao;
import users.Address;
import util.WebUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class EditAddressHandler implements HttpHandler {
    private UserDao userDao;

    public EditAddressHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
        BufferedWriter out = null;
        try {
            String query = he.getRequestURI().getQuery();
            Map<String, String> queryParams = WebUtil.requestStringToMap(query);
            String customerIdParam = queryParams.get("id");
            String addressIdParam = queryParams.get("address_id");

            if (customerIdParam == null || addressIdParam == null) {
                he.sendResponseHeaders(400, 0); // Bad request
                return;
            }

            int customerId = Integer.parseInt(customerIdParam);
            int addressId = Integer.parseInt(addressIdParam);

            he.sendResponseHeaders(200, 0);
            out = new BufferedWriter(new OutputStreamWriter(he.getResponseBody()));

            Address address = userDao.getAddress(customerId, addressId);
            
            System.out.println(address);

            if (address != null) {
                StringBuilder html = new StringBuilder();
                html.append("<html>" +
                        "<head><title>Edit Address</title>" +
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" " +
                        "integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">" +
                        "</head>" +
                        "<body><div class=\"container\">" +
                        "<h1 class=\"mt-4\">Edit Address</h1>" +
                        "<form method=\"post\" action=\"/admin/users/update-address\">" +
	                        "<input type=\"hidden\" name=\"customerId\" value=\"" + customerId + "\">" +
	                        "<input type=\"hidden\" name=\"addressId\" value=\"" + addressId + "\">" +
	                        "<div class=\"form-group\">" +
		                        "<label for=\"number\">Number</label>" +
		                        "<input type=\"text\" class=\"form-control\" id=\"number\" name=\"number\" value=\"" + address.getNumber() + "\">" +
	                        "</div>" +
	                        "<div class=\"form-group\">" +
		                        "<label for=\"street\">Street</label>" +
		                        "<input type=\"text\" class=\"form-control\" id=\"street\" name=\"street\" value=\"" + address.getStreet() + "\">" +
	                        "</div>" +
	                        "<div class=\"form-group\">" +
		                        "<label for=\"city\">City</label>" +
		                        "<input type=\"text\" class=\"form-control\" id=\"city\" name=\"city\" value=\"" + address.getCity() + "\">" +
	                        "</div>" +
	                        "<div class=\"form-group\">" +
		                        "<label for=\"postCode\">Post Code</label>" +
		                        "<input type=\"text\" class=\"form-control\" id=\"postCode\" name=\"postCode\" value=\"" + address.getPostCode() + "\">" +
	                        "</div>" +
	                        "<div class=\"form-group\">" +
		                        "<label for=\"country\">Country</label>" +
		                        "<input type=\"text\" class=\"form-control\" id=\"country\" name=\"country\" value=\"" + address.getCountry() + "\">" +
	                        "</div>" +
	                        "<div class=\"form-check\">" +
		                        "<input class=\"form-check-input\" type=\"checkbox\" id=\"isPrimary\" name=\"isPrimary\" " +
		                        (address.isPrimary() ? "checked" : "") + ">" +
		                        "<label class=\"form-check-label\" for=\"isPrimary\">Set as Primary Address</label>" +
	                        "</div>" +
	                        "<button type=\"submit\" class=\"btn btn-success mt-3\">Save Changes</button>" +
	                        "<a href=\"/admin/users\" class=\"btn btn-secondary ml-2 mt-3\">Cancel</a>" +
                        "</form></div></body></html>");

                out.write(html.toString());
            } else {
                out.write("<html><body><h1>Address Not Found</h1></body></html>");
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
