package homeApplianceStoreDAO;

import java.util.ArrayList;

import homeApplianceStore.HomeAppliance;

public interface HomeApplianceDAO {
	HomeAppliance get(int id);
	ArrayList<HomeAppliance> findAllProducts();
	boolean addProduct(int id);
	boolean deleteProduct(int id);
	boolean updateProduct(int id);
}
