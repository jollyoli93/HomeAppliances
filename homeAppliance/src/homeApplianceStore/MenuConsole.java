//STUDENT NO. 24862664

package homeApplianceStore;

import java.util.ArrayList;
import java.util.Scanner;

import DAO.ApplianceDao;
import homeApplianceStoreDAO.HomeApplianceDAOImpl;
import printer.AppliancePrinter;

public class MenuConsole {
	String driver;
	String dbPath;
	ApplianceDao applianceDAO;
	
	public MenuConsole() {
		String dbPath = "HomeAppliances.db";

		applianceDAO = new ApplianceDao(dbPath);
	}	
	
	void displayMenu() {
		Scanner scanner = new Scanner(System.in);
		
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
						ArrayList<HomeAppliance> list = applianceDAO.findAll();
						
						for (HomeAppliance obj : list) {
							AppliancePrinter print = new AppliancePrinter(obj);
							print.print();
						}
						
						break;
					case 2:
						int id = scanner.nextInt();
						System.out.println("Searching for product " + id);
						applianceDAO.getById(id);
								
						break;
					case 3:
						//get latest ID
						int newId = 0;
						
						System.out.println("Enter SKU");
						String newSku = scanner.nextLine();
						
						System.out.println("Enter Description");
						String newDesc = scanner.nextLine();
						System.out.println("Enter Category");
						String newCat = scanner.nextLine();
						System.out.println("Enter Price");
						double newPrice = scanner.nextDouble();
						
						
						HomeAppliance addAppliance = new HomeAppliance(newId, newSku, newDesc, newCat, newPrice);
						applianceDAO.addNew(addAppliance);
						
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
				
				System.out.println("Press 0 to continue or 6 to exit");
				input = scanner.nextInt();
				
		} while (input != 6);
		
		scanner.close();
	}
}

