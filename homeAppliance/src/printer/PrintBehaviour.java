package printer;

import homeApplianceStore.Appliance;
import users.User;

public interface PrintBehaviour {
	void print();
}

class ApplianceBehaviour implements PrintBehaviour {
	Appliance obj;
	
	ApplianceBehaviour (Appliance obj2){
		this.obj  = obj2;
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

class CustomerBehaviour implements PrintBehaviour {
	User obj;
	
	CustomerBehaviour (User obj2){
		this.obj  = obj2;
	}
	
	public void print() {
		System.out.println("Full Name: " + obj.getFullName());
		System.out.println("Username: " + obj.getUsername());
		System.out.println("Email Address: " + obj.getEmailAddress());
		System.out.println("-----------------------");
		System.out.print("");
	}
}

class AdminBehaviour implements PrintBehaviour {
	User obj;
	
	AdminBehaviour (User obj2){
		this.obj  = obj2;
	}
	
	public void print() {
		System.out.println("Full Name: " + obj.getFullName());
		System.out.println("Username: " + obj.getUsername());
		System.out.println("Email Address: " + obj.getEmailAddress());
		System.out.println("-----------------------");
		System.out.print("");
	}
}

class BusinessBehaviour implements PrintBehaviour {
	User obj;
	
	BusinessBehaviour (User obj2){
		this.obj  = obj2;
	}
	
	public void print() {
		System.out.println("Full Name: " + obj.getFullName());
		System.out.println("Username: " + obj.getUsername());
		System.out.println("Email Address: " + obj.getEmailAddress());
		System.out.println("-----------------------");
		System.out.print("");
	}
}