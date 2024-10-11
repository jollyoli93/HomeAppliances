package homeApplianceStore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HomeApplianceTests {
	HomeAppliance home;
	
	@BeforeEach
	public void IsInitialized() {
		this.home = new HomeAppliance(1, "TV001", "TV", "VCR Television", 200);
	}

	@Test
	public void CorrectApplianceDescription() {
		assertEquals("TV", this.home.getDescription());
	}

	@Test
	public void IdNotNull() {
		assertTrue(this.home.getId() != 0);
	}
	
	@Test
	public void CorrectApplianceSku() {
		assertEquals("TV001", home.getSku());
	}
}
