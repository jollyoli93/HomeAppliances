package printer;

import homeApplianceStore.Appliance;

public class AppliancePrinter extends Printer {
	
	public AppliancePrinter(Appliance obj) {
		printBehaviour = new ApplianceBehaviour(obj);
	}
	
}
