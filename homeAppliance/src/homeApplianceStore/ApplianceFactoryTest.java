package homeApplianceStore;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


	
class ApplianceFactoryTest {
    ApplianceFactory entertainmentFactory;
    ApplianceFactory cleaningFactory;

    @BeforeEach
    public void setUp() {
        entertainmentFactory = new EntertainmentFactory();
        cleaningFactory = new HomeCleaningFactory();
    }

    @Test
    void testBasicTelevisionCreation() {
        Appliance tv = entertainmentFactory.selectAppliance("basic television", 1);
        assertEquals("Basic Television", tv.getDescription());
        assertEquals("Entertainment", tv.getCategory());
        assertEquals(100, tv.getPrice());
        assertEquals(1, tv.getId());
    }

    @Test
    void testLCDTelevisionCreation() {
        Appliance lcdTv = entertainmentFactory.selectAppliance("LCD television", 2);
        assertEquals("LCD Television", lcdTv.getDescription());
        assertEquals("Entertainment", lcdTv.getCategory());
        assertEquals(300, lcdTv.getPrice());
        assertEquals(2, lcdTv.getId());
    }

    @Test
    void testBasicWashingMachineCreation() {
        Appliance washer = cleaningFactory.selectAppliance("basic washing machine", 3);
        assertEquals("Basic Washing Machine", washer.getDescription());
        assertEquals("Home Cleaning", washer.getCategory());
        assertEquals(300, washer.getPrice());
        assertEquals(3, washer.getId());
    }

    @Test
    void testSuperFastWashingMachineCreation() {
        Appliance fastWasher = cleaningFactory.selectAppliance("super fast washing machine", 4);
        assertEquals("Super Fast Washing Machine", fastWasher.getDescription());
        assertEquals("Home Cleaning", fastWasher.getCategory());
        assertEquals(1000, fastWasher.getPrice());
        assertEquals(4, fastWasher.getId());
    }

    @Test
    void testUnknownApplianceType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            entertainmentFactory.selectAppliance("unknown appliance", 5);
        });
        assertEquals("Unknown appliance type: unknown appliance", exception.getMessage());
    }
	
}
