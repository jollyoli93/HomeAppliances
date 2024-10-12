package homeApplianceStore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HomeApplianceTests {
	HomeAppliance home;
	
	@BeforeEach
	public void isInitialized() {
		this.home = new HomeAppliance(1, "TV001", "TV", "Entertainment", 200);
	}

	@Test
	public void correctApplianceDescription() {
		assertEquals("TV", this.home.getDescription());
	}

	@Test
	public void idNotNull() {
		assertTrue(this.home.getId() != 0);
	}
	
	@Test
	public void correctApplianceSku() {
		assertEquals("TV001", home.getSku());
	}
	
	@Test
	public void priceNotNull() {
		assertTrue(home.getPrice() != 0.0);
	}
	
	@Test
	public void zeroPriceIsFalse() {
		assertFalse(home.getPrice()== 0.0);
	}
}
