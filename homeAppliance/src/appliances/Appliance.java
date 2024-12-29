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
	
	public void setId (int id) {
		this.id = id;
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
	
	public void setPrice(double updatedPrice) {
		this.price = updatedPrice;
	}
	
	public String getDetails() {
		return "Description: " + description + ", Category: " + category + ", Price: " + price + " ID:" + id + ", SKU: " + sku;
	}
	
    public String toHTMLString() {
	    return "<tr><td>" + id + "</td><td>" + description + "</td><td>" + category + "</td><td>" + price + "</td><td>" + sku
	        + "</td></tr>";
	  }
}

class BasicTVAppliance extends Appliance {
	public BasicTVAppliance() {
		this.sku = "TV001";
		this.description = "Basic Television";
		this.category = "Entertainment";
		this.price = 100;
	}
}

class LCDTVAppliance extends Appliance {
	public LCDTVAppliance() {
		this.sku = "TV002";
		this.description = "LCD Television";
		this.category = "Entertainment";
		this.price = 300;
	}
}

class BasicWashingMachineAppliance extends Appliance {
	public BasicWashingMachineAppliance() {
		this.sku = "WM001";
		this.description = "Basic Washing Machine";
		this.category = "Home Cleaning";
		this.price = 300;
	}
}

class SuperFastWashingMachineAppliance extends Appliance {
	public SuperFastWashingMachineAppliance() {
		this.sku = "WM002";
		this.description = "Super Fast Washing Machine";
		this.category = "Home Cleaning";
		this.price = 1000;
	}
}

