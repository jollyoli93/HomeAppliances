package util;

import appliances.ApplianceFactory;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private Map<String, ApplianceFactory> applianceFactories;

    public FactoryRegistry() {
        applianceFactories = new HashMap<>();
        initApplianceFactories();
    }

    public void initApplianceFactories() {
        applianceFactories.put("Entertainment", new EntertainmentFactory());
        applianceFactories.put("Home Cleaning", new HomeCleaningFactory());
    }

    public Map<String, ApplianceFactory> getFactories() {
        return applianceFactories;
    }
}
