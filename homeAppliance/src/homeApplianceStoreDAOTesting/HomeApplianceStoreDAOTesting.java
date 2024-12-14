package homeApplianceStoreDAOTesting;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import appliances.Appliance;
import database.DbConnection;

public class HomeApplianceStoreDAOTesting {
	String dbPath;
	String driver;
	Appliance appliance;
	
	@BeforeEach
	public void initializeConnection() {
		String dbPath = "HomeAppliances.db";
		String driver = "org.sqlite.JDBC";

		DbConnection con = new DbConnection(dbPath, driver);
		
		con.initializeDBConnection();
	}

//	@Test 
//	public void QueryReturnsCorrectAppliance (){
//		appliance = new HomeAppliance(1, "TV001", "TV", dbPath, 0)
//	}

}
