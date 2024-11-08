package homeApplianceStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ApplianceFactory {
    
    protected Map<String, Function<Integer, Appliance>> applianceMap = new HashMap<>();
    
    public Appliance selectAppliance(String type, int id) {
        Function<Integer, Appliance> applianceCreator = applianceMap.get(type.toLowerCase());
        
        if (applianceCreator == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return applianceCreator.apply(id);
    }
    
    // Methods to add or remove appliance types from the map dynamically
    protected void addType(String type, Function<Integer, Appliance> creatorFunction) {
        applianceMap.put(type.toLowerCase(), creatorFunction);
    }
    
    protected void removeType(String type) {
        applianceMap.remove(type.toLowerCase());
    }
    
    public ArrayList<String> listAllApplianceTypes () {
    	ArrayList<String> types = new ArrayList<>();
    	applianceMap.forEach( (key, value) -> {
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
        addType("basic television", id -> new BasicTVAppliance(id));
        addType("lcd television", id -> new LCDTVAppliance(id));
    }
}

class HomeCleaningFactory extends ApplianceFactory {

    public HomeCleaningFactory() {
        initializeApplianceTypes();
    }

    @Override
    void initializeApplianceTypes() {
        addType("basic washing machine", id -> new BasicWashingMachineAppliance(id));
        addType("super fast washing machine", id -> new SuperFastWashingMachineAppliance(id));
    }
}
