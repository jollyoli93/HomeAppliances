package homeApplianceStore;

public class HomeAppliance {
	private int id;
	private String sku;
	private String description;
	private String category;
	private int price;
	
//	private static int totalAppliances = 0;  potentaly?

	public HomeAppliance (int id, String sku, String description, String category, int price) {
		this.id = id;
		this.sku = sku;
		this.description = description;
		this.category = category;
		this.price = price;
		
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getSku() {
		return this.sku;
	}
	
}
