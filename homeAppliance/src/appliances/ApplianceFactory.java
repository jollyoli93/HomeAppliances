package appliances;

public abstract class ApplianceFactory {
	
	public Appliance selectAppliance(String type, int id) {
		Appliance appliance;
		
		appliance = createAppliance(type, id);
		
        if (appliance == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return appliance;
    }
	
	abstract Appliance createAppliance(String type, int id);
}

class EntertainmentFactory extends ApplianceFactory{
	Appliance createAppliance(String type, int id) {
		if (type.equalsIgnoreCase("basic television")) {
			return new BasicTVAppliance(id);
		} else if (type.equalsIgnoreCase("LCD television")){
			return new LCDTVAppliance(id);
		} else {
			return null;
		}
	}
}

class HomeCleaningFactory extends ApplianceFactory{
	Appliance createAppliance(String type, int id) {
		if (type.equalsIgnoreCase("basic washing machine")) {
			return new BasicWashingMachineAppliance(id);
		} else if (type.equalsIgnoreCase("super fast washing machine")){
			return new SuperFastWashingMachineAppliance(id);
		} else {
			return null;
		}
	}
}