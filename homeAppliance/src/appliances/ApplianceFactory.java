package appliances;

public abstract class ApplianceFactory {
	
	public Appliance selectAppliance(String type) {
		Appliance appliance;
		
		appliance = createAppliance(type);
		
        if (appliance == null) {
            throw new IllegalArgumentException("Unknown appliance type: " + type);
        }
        
        return appliance;
    }
	
	abstract Appliance createAppliance(String type);
}

class EntertainmentFactory extends ApplianceFactory{
	Appliance createAppliance(String type) {
		if (type.equals("basic television")) {
			return new BasicTVAppliance();
		}
		return null;
	}
}
