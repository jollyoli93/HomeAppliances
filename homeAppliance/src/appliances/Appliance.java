package appliances;

public abstract class Appliance {
	protected static int id = 0;
	protected String sku;
	protected String description;
	protected String category;
	protected double price;
	
	public int getId () {
		return Appliance.id;
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
	public BasicTVAppliance() {
		Appliance.id += 1;
		this.sku = "TV001";
		this.description = "Basic Television";
		this.category = "Entertainment";
		this.price = 100;
	}
}