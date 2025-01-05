//STUDENT NO. 24862664
package appliances;

/**
 * @author 24862664
 */
public class EntertainmentFactory extends ApplianceDepartments {
    public EntertainmentFactory() {
        initializeApplianceTypes();
        applianceDepartmentMap.put("entertainment", () -> new EntertainmentFactory());
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic television", () -> new BasicTVAppliance());
        addType("lcd television", () -> new LCDTVAppliance());
    }
}
