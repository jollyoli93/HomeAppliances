package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import appliances.Appliance;
import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

/**
 * Unit tests for the appliance factory classes. These tests validate the creation 
 * of various appliances using different factories (EntertainmentFactory, HomeCleaningFactory) 
 * and ensure that the correct appliance types are created with the expected properties.
 *
 * The tests also include validation for unknown appliance types to ensure proper exception handling.
 * 
 * @author 24862664
 */
class ApplianceFactoryTest {
    ApplianceDepartments entertainmentFactory;
    ApplianceDepartments cleaningFactory;

    /**
     * Initialises the test environment before each test. This method creates instances
     * of the EntertainmentFactory and HomeCleaningFactory, which are used to create appliances.
     */
    @BeforeEach
    public void setUp() {
        entertainmentFactory = new EntertainmentFactory();
        cleaningFactory = new HomeCleaningFactory();
    }

    /**
     * Test case for creating a basic television using the EntertainmentFactory.
     * This test ensures that the appliance is correctly created with the expected properties.
     */
    @Test
    void testBasicTelevisionCreation() {
        Appliance tv = entertainmentFactory.selectAppliance("basic television");
        tv.setId(1);
        assertEquals("Basic Television", tv.getDescription());
        assertEquals("Entertainment", tv.getCategory());
        assertEquals(100, tv.getPrice());
        assertEquals(1, tv.getId());
    }

    /**
     * Test case for creating an LCD television using the EntertainmentFactory.
     * This test ensures that the appliance is correctly created with the expected properties.
     */
    @Test
    void testLCDTelevisionCreation() {
        Appliance lcdTv = entertainmentFactory.selectAppliance("LCD television");
        lcdTv.setId(2);
        assertEquals("LCD Television", lcdTv.getDescription());
        assertEquals("Entertainment", lcdTv.getCategory());
        assertEquals(300, lcdTv.getPrice());
        assertEquals(2, lcdTv.getId());
    }

    /**
     * Test case for creating a basic washing machine using the HomeCleaningFactory.
     * This test ensures that the appliance is correctly created with the expected properties.
     */
    @Test
    void testBasicWashingMachineCreation() {
        Appliance washer = cleaningFactory.selectAppliance("basic washing machine");
        washer.setId(3);
        assertEquals("Basic Washing Machine", washer.getDescription());
        assertEquals("Home Cleaning", washer.getCategory());
        assertEquals(300, washer.getPrice());
        assertEquals(3, washer.getId());
    }

    /**
     * Test case for creating a super fast washing machine using the HomeCleaningFactory.
     * This test ensures that the appliance is correctly created with the expected properties.
     */
    @Test
    void testSuperFastWashingMachineCreation() {
        Appliance fastWasher = cleaningFactory.selectAppliance("super fast washing machine");
        fastWasher.setId(4);
        assertEquals("Super Fast Washing Machine", fastWasher.getDescription());
        assertEquals("Home Cleaning", fastWasher.getCategory());
        assertEquals(1000, fastWasher.getPrice());
        assertEquals(4, fastWasher.getId());
    }

    /**
     * Test case for attempting to create an appliance with an unknown type.
     * This test ensures that the correct exception is thrown when an invalid appliance type is requested.
     */
    @Test
    void testUnknownApplianceType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            entertainmentFactory.selectAppliance("unknown appliance");
        });
        assertEquals("Unknown appliance type: unknown appliance", exception.getMessage());
    }
}
