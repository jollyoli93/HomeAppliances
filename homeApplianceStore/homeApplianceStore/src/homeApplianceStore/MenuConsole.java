//STUDENT NO. 24862664

package homeApplianceStore;

import java.util.Scanner;

import homeApplianceStoreDAO.HomeApplianceDAOImpl;

public class MenuConsole {
	void displayMenu() {
		Scanner scanner = new Scanner(System.in);
//		HomeApplianceDAOImpl dao = new HomeApplianceDAOImpl(null);
		
		int input;
		
		do {
		
			System.out.println("------------------------");
			System.out.println("The Home Appliance Store");
			System.out.println("Choose from these options");
			System.out.println("------------------------");
			
			
			System.out.println("[1] List all products");
			System.out.println("[2] Search by the product ID");
			System.out.println("[3] Add a new product");
			System.out.println("[4] Update a product by ID");
			System.out.println("[5] Delete a product by ID");
			System.out.println("[6] Exit");
			
			input = scanner.nextInt();
			
				switch (input) {
					case 1:
						System.out.println("Listing products");
						break;
					case 2:
						int id = scanner.nextInt();
						System.out.println("Searching for product " + id);
						break;
					case 3:
						System.out.println("Adding new product");
						break;
					case 4:
						System.out.println("Updating");
						break;
					case 5:
//						dao.deleteProduct(input);
						System.out.println("Deleting");
						break;
					case 6:
						System.out.println("Exiting");
						break;
					default:
						System.out.println("Not valid, please select again...");
						break;
						}
		} while (input != 6);
		
		scanner.close();
	}
}
