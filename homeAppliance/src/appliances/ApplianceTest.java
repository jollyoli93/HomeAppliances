package appliances;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplianceTest {
	ApplianceFactory factory;
	Appliance basicTVAppliance;
	
	@BeforeEach
	public void instantiateEntertainmentFactory() {
		this.factory = new EntertainmentFactory();
	}
	
	@BeforeEach
	public void instantiateBasicTelevision () {
		basicTVAppliance = factory.createAppliance("basic television");

	}

	@Test
	void nullReturnAppliance () {		
		Appliance nullAppliance = factory.createAppliance("Not correct");
		
		assertEquals(null, nullAppliance);
	}
	
	@Test
	void correctDescBasicTVAppliance () {				
		assertEquals("Basic Television", basicTVAppliance.getDescription());
	}
	
	@Test
	void correctCatBasicTVAppliance () {				
		assertEquals("Entertainment", basicTVAppliance.getCategory());
	}
	
	@Test
	void correctPriceBasicTVAppliance () {
		assertEquals(100, basicTVAppliance.getPrice());
	}
}
