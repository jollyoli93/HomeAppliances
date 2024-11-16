package homeApplianceStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class ApplianceFactory {
    
    protected Map<String, Supplier<Appliance>> applianceMap = new HashMap<>();
    
    public Appliance selectAppliance(String type) {
        Supplier<Appliance> applianceCreator = applianceMap.get(type.toLowerCase());
        
        if (applianceCreator == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return applianceCreator.get();
    }
    
    protected void addType(String type, Supplier<Appliance> creatorFunction) {
        applianceMap.put(type.toLowerCase(), creatorFunction);
    }
    
    protected void removeType(String type) {
        applianceMap.remove(type.toLowerCase());
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

class EntertainmentFactory extends ApplianceFactory {
    public EntertainmentFactory() {
        initializeApplianceTypes();
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic television", () -> new BasicTVAppliance());
        addType("lcd television", () -> new LCDTVAppliance());
    }
}

class HomeCleaningFactory extends ApplianceFactory {
    public HomeCleaningFactory() {
        initializeApplianceTypes();
    }
    
    @Override
    void initializeApplianceTypes() {
        addType("basic washing machine", () -> new BasicWashingMachineAppliance());
        addType("super fast washing machine", () -> new SuperFastWashingMachineAppliance());
    }
}