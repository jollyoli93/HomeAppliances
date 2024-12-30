package webpages;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import DAO.ApplianceDao;

public class DeleteApplianceForm implements HttpHandler {
	private ApplianceDao applianceDao;
	
	public DeleteApplianceForm(ApplianceDao applianceDao) {
		this.applianceDao = applianceDao;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}

}
