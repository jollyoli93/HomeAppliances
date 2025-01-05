//STUDENT NO. 24862664
package appliances;

/**
 * @author 24862664
 */
public class HomeCleaningFactory extends ApplianceDepartments {
    public HomeCleaningFactory() {
        initializeApplianceTypes();
        applianceDepartmentMap.put("home cleaning", () -> new HomeCleaningFactory());
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic washing machine", () -> new BasicWashingMachineAppliance());
        addType("super fast washing machine", () -> new SuperFastWashingMachineAppliance());
    }
}