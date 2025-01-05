//STUDENT NO. 24862664
package appliances;

/**
 * Factory class for creating entertainment appliances.
 * This class extends the ApplianceDepartments class and initialises 
 * specific appliance types related to entertainment.
 * 
 * @author 24862664
 */
public class EntertainmentFactory extends ApplianceDepartments {
    /**
     * Constructor that initialises the appliance types.
     */
    public EntertainmentFactory() {
        initializeApplianceTypes();
        applianceDepartmentMap.put("entertainment", () -> new EntertainmentFactory());
    }
    
    /**
     * Initialises appliance types related to entertainment.
     * Adds basic television and LCD television to the appliance department.
     */
    @Override
    void initializeApplianceTypes() {
        addType("basic television", () -> new BasicTVAppliance());
        addType("lcd television", () -> new LCDTVAppliance());
    }
}
