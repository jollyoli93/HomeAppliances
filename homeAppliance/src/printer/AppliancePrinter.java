package printer;

import appliances.Appliance;

/**
 * This class is responsible for printing an appliance type.
 * 
 * @author 24862664
 */
public class AppliancePrinter extends Printer {
	
    /**
     * Constructs an AppliancePrinter object to print out the descriptions.
     * @param appliance object
     */
	public AppliancePrinter(Appliance obj) {
		printBehaviour = new ApplianceBehaviour(obj);
	}
	
}
