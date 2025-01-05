package appliances;

/**
 * Factory class for creating home cleaning appliances.
 * This class extends the ApplianceDepartments class and initialises 
 * specific appliance types related to home cleaning.
 * 
 * @author 24862664
 */
public class HomeCleaningFactory extends ApplianceDepartments {

    /**
     * Constructor that initialises the appliance types.
     */
    public HomeCleaningFactory() {
        initializeApplianceTypes();
        applianceDepartmentMap.put("home cleaning", () -> new HomeCleaningFactory());
    }
    
    /**
     * Initialises appliance types related to home cleaning.
     * Adds basic and super fast washing machines to the appliance department.
     */
    @Override
    void initializeApplianceTypes() {
        addType("basic washing machine", () -> new BasicWashingMachineAppliance());
        addType("super fast washing machine", () -> new SuperFastWashingMachineAppliance());
    }
}
