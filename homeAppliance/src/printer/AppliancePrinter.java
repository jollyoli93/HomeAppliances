package printer;

import homeApplianceStore.Appliance;
import homeApplianceStore.HomeApplianceSS;

public class AppliancePrinter extends Printer {
	
	public AppliancePrinter(Appliance obj) {
		printBehaviour = new ApplianceBehaviour(obj);
	}
	
}
