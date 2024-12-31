package appliances;

public class EntertainmentFactory extends ApplianceDepartments {
    public EntertainmentFactory() {
        initializeApplianceTypes();
        applianceDepartmentMap.put("Entertainment", () -> new EntertainmentFactory());
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic television", () -> new BasicTVAppliance());
        addType("lcd television", () -> new LCDTVAppliance());
    }
}
