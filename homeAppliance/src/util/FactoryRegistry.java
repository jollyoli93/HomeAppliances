package util;

import appliances.ApplianceFactory;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private static FactoryRegistry instance;
    private Map<String, ApplianceFactory> applianceFactories;

    private FactoryRegistry() {
        applianceFactories = new HashMap<>();
        initApplianceFactories();
    }

    private void initApplianceFactories() {
        applianceFactories.put("Entertainment", new EntertainmentFactory());
        applianceFactories.put("Home Cleaning", new HomeCleaningFactory());
    }

    public static synchronized FactoryRegistry getInstance() {
        if (instance == null) {
            instance = new FactoryRegistry();
        }
        return instance;
    }

    public ApplianceFactory getFactory(String category) {
        return applianceFactories.get(category);
    }

    public Map<String, ApplianceFactory> getFactories() {
        return applianceFactories;
    }
}
