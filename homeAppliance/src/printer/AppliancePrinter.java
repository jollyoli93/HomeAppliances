package printer;

import appliances.Appliance;

public class AppliancePrinter extends Printer {
	
	public AppliancePrinter(Appliance obj) {
		printBehaviour = new ApplianceBehaviour(obj);
	}
	
}
