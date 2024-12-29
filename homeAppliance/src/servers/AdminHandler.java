package servers;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;
import DAO.DAO;
import appliances.Appliance;

import com.sun.net.httpserver.HttpExchange;
import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;

public class AdminHandler implements HttpHandler {
  public void handle(HttpExchange he) throws IOException {
    String response = "Admin Page";
    he.sendResponseHeaders(200, 0);
    BufferedWriter out = new BufferedWriter(
        new OutputStreamWriter(he.getResponseBody())); 
    
    out.write(
            "<html>" +
                "<head> <title>DVD Library</title> " +
                "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">"
                +
                "</head>" +
                "<body>" +
                
                "<tbody>");
    
    out.close();
  }
}