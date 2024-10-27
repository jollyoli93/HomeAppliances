//STUDENT NO. 24862664

package homeApplianceStore;

public class HomeAppliance {
	private int id;
	private String sku;
	private String description;
	private String category;
	private double price;
	
	public HomeAppliance (int id, String sku, String description, String category, double price) {
		this.id = id;
		this.sku = sku;
		this.description = description;
		this.category = category;
		this.price = price;
		
	}
	
	public int getId() {
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
