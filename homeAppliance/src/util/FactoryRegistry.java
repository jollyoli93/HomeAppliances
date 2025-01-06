package util;

import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * The FactoryRegistry class is responsible for managing the various appliance factories.
 * This class helps in initialising and retrieving the appropriate appliance factory based on the department type.
 * 
 * @author 24862664
 */
public class FactoryRegistry {
    
    // A map that stores appliance department names as keys and their corresponding factory objects as values.
    private Map<String, ApplianceDepartments> applianceDepartments;

    /**
     * Constructor that initialises the appliance department map and sets up the factory for each department.
     */
    public FactoryRegistry() {
        applianceDepartments = new HashMap<>();
        initApplianceFactories();
    }

    /**
     * Initialises the appliance department factories. 
     * This method adds entries to the applianceDepartments map for different departments, such as "entertainment" 
     * and "home cleaning", associating them with their respective factory objects.
     */
    public void initApplianceFactories() {
        applianceDepartments.put("entertainment", new EntertainmentFactory());
        applianceDepartments.put("home cleaning", new HomeCleaningFactory());
    }

    /**
     * Retrieves the map of appliance department factories.
     * 
     * @return A map containing the names of appliance departments as keys, and their corresponding factory instances as values.
     */
    public Map<String, ApplianceDepartments> getDepartments() {
        return applianceDepartments;
    }
}
