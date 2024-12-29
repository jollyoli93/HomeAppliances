package servers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;
import appliances.ApplianceFactory;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.IOException;


public class ApplianceHandler implements HttpHandler {
	Map<String, ApplianceFactory> applianceFactories = new HashMap<String, ApplianceFactory>();
	private void initFactoriesMap () {
		ApplianceFactory entertainment = new EntertainmentFactory();
		ApplianceFactory homeCleaning = new HomeCleaningFactory();
		
		applianceFactories.put("Entertainment", entertainment);
		applianceFactories.put("Home Cleaning", homeCleaning);
	}
	
	
  public void handle(HttpExchange he) throws IOException {
    String response = "Welcome to the Home Appliance Store";
    
    initFactoriesMap();
    
    he.sendResponseHeaders(200, 0);
    BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(he.getResponseBody()));
   
    DAO appliances = new ApplianceDao("HomeAppliances", null);

    List<Appliance> allAppliances = appliances.findAll();

    out.write(
        "<html>" +
            "<head> <title>DVD Library</title> " +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
            +
            "</head>" +
            "<body>" +
            "<h1> Appliances in Stock</h1>" +
            "<table class=\"table\">" +
            "<thead>" +
            "  <tr>" +
            "    <th>ID</th>" +
            "    <th>Description</th>" +
            "    <th>Category</th>" +
            "    <th>Price</th>" +
            "    <th>SKU</th>" +
            "  </tr>" +
            "</thead>" +
            "<tbody>");

    for (Appliance a : allAppliances) {
      out.write(a.toHTMLString());
    }
    out.write(
        "</tbody>" +
            "</table>" +
            "</body>" +
            "</html>");

    out.close();

  }

}