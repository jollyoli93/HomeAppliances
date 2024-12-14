package appliances;

public class EntertainmentFactory extends ApplianceFactory {
    public EntertainmentFactory() {
        initializeApplianceTypes();
        applianceFactoryMap.put("Entertainment", () -> new EntertainmentFactory());
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic television", () -> new BasicTVAppliance());
        addType("lcd television", () -> new LCDTVAppliance());
    }
}
