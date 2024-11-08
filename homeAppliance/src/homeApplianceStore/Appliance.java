package appliances;

public abstract class Appliance {
	protected int id = 0;
	protected String sku;
	protected String description;
	protected String category;
	protected double price;
	
	public int getId () {
		return this.id;
	}
	
	public String getSku() {
		return this.sku;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public double getPrice() {
		return this.price;
	}
	
}

class BasicTVAppliance extends Appliance {
	public BasicTVAppliance(int id) {
		this.id = id;
		this.sku = "TV001";
		this.description = "Basic Television";
		this.category = "Entertainment";
		this.price = 100;
	}
}

class LCDTVAppliance extends Appliance {
	public LCDTVAppliance(int id) {
		this.id = id;
		this.sku = "TV002";
		this.description = "LCD Television";
		this.category = "Entertainment";
		this.price = 300;
	}
}

class BasicWashingMachineAppliance extends Appliance {
	public BasicWashingMachineAppliance(int id) {
		this.id = id;
		this.sku = "WM001";
		this.description = "Basic Washing Machine";
		this.category = "Home Cleaning";
		this.price = 300;
	}
}

class SuperFastWashingMachineAppliance extends Appliance {
	public SuperFastWashingMachineAppliance(int id) {
		this.id = id;
		this.sku = "WM002";
		this.description = "Super Fast Washing Machine";
		this.category = "Home Cleaning";
		this.price = 1000;
	}
}

