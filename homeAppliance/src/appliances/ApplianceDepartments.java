package appliances;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ApplianceDepartments {
	public static Map<String, Supplier<ApplianceDepartments>> applianceDepartmentMap = new HashMap<>();
	protected Map<String, Supplier<Appliance>> applianceMap = new HashMap<>();
    
    public Appliance selectAppliance(String type) {
        Supplier<Appliance> applianceCreator = applianceMap.get(type.toLowerCase());
        
        if (applianceCreator == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return applianceCreator.get();
    }
    
    public static ApplianceDepartments selectApplianceDepartment(String type) {
        Supplier<ApplianceDepartments> factoryCreator = applianceFactoryMap.get(type);
        
        if (factoryCreator == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return factoryCreator.get();
    }
    
    protected void addType(String type, Supplier<Appliance> creatorFunction) {
        applianceMap.put(type.toLowerCase(), creatorFunction);
    }
    
    protected void removeType(String type) {
        applianceMap.remove(type.toLowerCase());
    }
    
    public ArrayList<String> listFactoryTypes () {
        ArrayList<String> types = new ArrayList<>();
        applianceFactoryMap.forEach((key, value) -> {
            types.add(key);
        });
        
        return types;
    }
    
    public ArrayList<String> listAllApplianceTypes() {
        ArrayList<String> types = new ArrayList<>();
        applianceMap.forEach((key, value) -> {
            types.add(key);
        });
        
        return types;
    }
    
    abstract void initializeApplianceTypes();
}
