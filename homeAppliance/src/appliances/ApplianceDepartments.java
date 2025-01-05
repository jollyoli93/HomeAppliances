package appliances;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Abstract class representing an Appliance Department.
 * It manages a collection of appliance types and allows dynamic selection of appliances or departments.
 */
public abstract class ApplianceDepartments {

    /** 
     * Static map for managing available appliance departments by their names. 
     * Maps department names to factory methods for creating department instances.
     */
    public static Map<String, Supplier<ApplianceDepartments>> applianceDepartmentMap = new HashMap<>();

    /** 
     * Map for managing available appliance types within this department. 
     * Maps appliance types to factory methods for creating appliance instances.
     */
    protected Map<String, Supplier<Appliance>> applianceMap = new HashMap<>();

    /**
     * Selects an appliance from this department based on the type.
     *
     * @param type the type of the appliance (case-insensitive)
     * @return an instance of the selected appliance
     * @throws IllegalArgumentException if the appliance type is not recognized
     */
    public Appliance selectAppliance(String type) {
        Supplier<Appliance> applianceCreator = applianceMap.get(type.toLowerCase());

        if (applianceCreator == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }

        return applianceCreator.get();
    }

    /**
     * Selects an appliance department based on the department type.
     *
     * @param type the name of the department
     * @return an instance of the selected appliance department
     * @throws IllegalArgumentException if the department type is not recognized
     */
    public static ApplianceDepartments selectApplianceDepartment(String type) {
        Supplier<ApplianceDepartments> factoryCreator = applianceDepartmentMap.get(type);

        if (factoryCreator == null) {
            throw new IllegalArgumentException("Unknown department: " + type);
        }

        return factoryCreator.get();
    }

    /**
     * Adds a new appliance type to this department.
     *
     * @param type the name of the appliance type (case-insensitive)
     * @param creatorFunction a factory method to create instances of this appliance type
     */
    protected void addType(String type, Supplier<Appliance> creatorFunction) {
        applianceMap.put(type.toLowerCase(), creatorFunction);
    }

    /**
     * Removes an appliance type from this department.
     *
     * @param type the name of the appliance type (case-insensitive)
     */
    protected void removeType(String type) {
        applianceMap.remove(type.toLowerCase());
    }

    /**
     * Lists all available appliance department types.
     *
     * @return a list of department names
     */
    public ArrayList<String> listFactoryTypes() {
        ArrayList<String> types = new ArrayList<>();
        applianceDepartmentMap.forEach((key, value) -> {
            types.add(key);
        });

        return types;
    }

    /**
     * Lists all available appliance types within this department.
     *
     * @return a list of appliance type names
     */
    public ArrayList<String> listAllApplianceTypes() {
        ArrayList<String> types = new ArrayList<>();
        applianceMap.forEach((key, value) -> {
            types.add(key);
        });

        return types;
    }

    /**
     * Abstract method to initialize the appliance types for the department.
     * Subclasses must implement this to define available appliance types.
     */
    abstract void initializeApplianceTypes();
}
