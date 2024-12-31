package appliances;

public class HomeCleaningFactory extends ApplianceDepartments {
    public HomeCleaningFactory() {
        initializeApplianceTypes();
        applianceFactoryMap.put("Home Cleaning", () -> new HomeCleaningFactory());
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic washing machine", () -> new BasicWashingMachineAppliance());
        addType("super fast washing machine", () -> new SuperFastWashingMachineAppliance());
    }
}