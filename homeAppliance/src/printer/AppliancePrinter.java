package printer;

import homeApplianceStore.HomeAppliance;

public class AppliancePrinter extends Printer {
	
	public AppliancePrinter(HomeAppliance obj) {
		printBehaviour = new ApplianceBehaviour(obj);
	}
	
}
