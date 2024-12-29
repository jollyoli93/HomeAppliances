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


public class RootHandler implements HttpHandler {
	
  public void handle(HttpExchange he) throws IOException {
    String response = "Welcome to the Home Appliance Store";
    
    he.sendResponseHeaders(200, 0);
    BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(he.getResponseBody()));

    out.write(
        "<html>" +
            "<head> <title>Home Appliance Store</title> " +
            "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"+
            "</head>" +
            "<body>" +
            "<h1>Welcome to the Home Appliance Store</h1>"+
            "<tbody>"
            );

    out.close();

  }

}