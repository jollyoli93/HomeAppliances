

import java.util.ArrayList;

import DAO.ApplianceDao;
import DAO.ShoppingDao;
import appliances.Appliance;
import appliances.EntertainmentFactory;
import mainHandlers.ArraryList;
import printer.AppliancePrinter;
import printer.Printer;
import users.BusinessUser;
import users.User;

public class main {

	public static void main(String[] args) {
		ApplianceDao appDao = new ApplianceDao("HomeAppliances");
		ArrayList<Appliance> appliances = new ArrayList<Appliance>();
	
		
		appliances = appDao.getAppliancesByPriceAsc();
		
		
		for (Appliance app : appliances) {
			System.out.println(app.getDetails());

		}
		
	}

}
