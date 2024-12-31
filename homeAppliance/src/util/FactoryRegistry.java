package util;

import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;

import java.util.HashMap;
import java.util.Map;

public class FactoryRegistry {
    private Map<String, ApplianceDepartments> applianceDepartments;

    public FactoryRegistry() {
    	applianceDepartments = new HashMap<>();
        initApplianceFactories();
    }

    public void initApplianceFactories() {
    	applianceDepartments.put("entertainment", new EntertainmentFactory());
    	applianceDepartments.put("home cleaning", new HomeCleaningFactory());
    }

    public Map<String, ApplianceDepartments> getDepartments() {
        return applianceDepartments;
    }
}
