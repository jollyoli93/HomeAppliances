package homeApplianceStore;

import java.util.ArrayList;

public class HomeApplianceStore {
	public static void main(String[] args ) {
	ApplianceFactory entertainment = new EntertainmentFactory();
		
		Appliance tv = entertainment.selectAppliance("Basic Television");
		
		ArrayList<String> list = entertainment.listAllApplianceTypes();
		
		System.out.println(list.get(0));

	}
}
