package printer;

import homeApplianceStore.HomeAppliance;

public interface PrintBehaviour {
	void print();
}



class ApplianceBehaviour implements PrintBehaviour {
	HomeAppliance obj;
	
	ApplianceBehaviour (HomeAppliance obj){
		this.obj  = obj;
	}
	
	public void print() {
		System.out.println("ID: " + obj.getId());
		System.out.println("SKU: " + obj.getSku());
		System.out.println("Description: " + obj.getDescription());
		System.out.println("Category: " + obj.getCategory());
		System.out.println("Price: £" + obj.getPrice());
		System.out.println("-----------------------");
		System.out.print("");
	}
}

//class UserBehvaiour 