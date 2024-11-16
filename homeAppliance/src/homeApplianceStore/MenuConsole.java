//STUDENT NO. 24862664

package homeApplianceStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.ApplianceDao;
import printer.AppliancePrinter;
import printer.Printer;

public class MenuConsole {
	private String output = null;
    private String driver;
    private String dbPath;
    private ApplianceDao applianceDAO;
    private InputOutputHandler handleInput;

	Map<String, ApplianceFactory> factories = new HashMap<String, ApplianceFactory>();

	public MenuConsole() {
		String dbPath = "HomeAppliances.db";
		initFactoriesMap();
		this.handleInput = new ConsoleIOHandler(); 
		
		this.applianceDAO = new ApplianceDao(dbPath, factories);
	}
	
	public void setHandler(InputOutputHandler handleInput) {
		this.handleInput = handleInput;
	}
	
	private void initFactoriesMap () {
		ApplianceFactory entertainment = new EntertainmentFactory();
		ApplianceFactory homeCleaning = new HomeCleaningFactory();
		
		factories.put("Entertainment", entertainment);
		factories.put("Home Cleaning", homeCleaning);
	}
	
	public String displayMenu() {
		int input = 0;
		
		while (input != 6) {
		
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
			System.out.println();
			
			input = handleInput.getInputInt();
			
				switch (input) {
					case 0:
						System.out.println("Try again");
						System.out.println();

						break;
					case 1:
						getAllProducts();
						break;
						
					case 2:
						getProductById();								
						break;
						
					case 3:
						addProduct();
						break;
					case 4:
						output = handleInput.output("Updating");
						System.out.println(output);
						System.out.println();
						break;
						
					case 5:
						deleteProduct();
//						Boolean delete = deleteProduct();

						break;
						
					case 6:
						break;
						
					default:
						System.out.println("Not valid, please select again...");
						break;
						}
				
				if (input != 6) {
					System.out.println("Press 6 to exit or any key to continue");
					input = handleInput.getInputInt();
				}
		}
		
		output = handleInput.output("Exiting");
		System.out.println(output);
		return output;
		
	}

	private void getAllProducts() {
		try {
			ArrayList<Appliance> list = applianceDAO.findAll();
			
			for (Appliance obj : list) {
				AppliancePrinter print = new AppliancePrinter(obj);
				print.print();
			}
		} catch (NullPointerException e) {
			System.out.println("No products in the database");
			System.out.println();
		}
	}
	
	private void getProductById() {
		try {
			Appliance appliance = null;
			AppliancePrinter printer = null;
			
			System.out.println("Enter Product ID");
			
			int id = handleInput.getInputInt();

			System.out.println("Searching for product " + id);
			System.out.println();
			
			appliance = applianceDAO.getById(id);
			
			printer = new AppliancePrinter(appliance);
			printer.print();
			
		} catch (NullPointerException e) {
			output = handleInput.output("No item found in the database");
			System.out.println(output);
			System.out.println();
		}

	}
	
	private void addProduct() {
		ApplianceFactory applianceFactory = null;
		Appliance appliance = null;
		
		int newId = applianceDAO.getUniqueId("appliances");
		int input;
		
		do {
			System.out.println("Please select department from the list below");
			System.out.println("[1] Entertainment");
			System.out.println("[2] Home Cleaning");
			System.out.println("[3] Go back");
			
			input = handleInput.getInputInt();
			
			switch (input) {
				case 1:
					applianceFactory = new EntertainmentFactory();
					break;
				case 2:
					applianceFactory = new HomeCleaningFactory();
					break;
				case 3:
					break;
				default:
					System.out.println("Please try again");
					System.out.println();

			}
		} while (input == 3);
		
		// list out concrete classes that are available for that particular factory. Must be called on the applianceFactory
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		int countOfTypes = applianceTypes.size();
		
		do {
			System.out.println("Please select an appliace to add");
			System.out.println();

			
			for (int i =  0; i < countOfTypes; i++) {
				String type =  applianceTypes.get(i);
				int userInput = i + 1;
				
				System.out.println("[" + userInput + "] " + type);
			}
			
			input = handleInput.getInputInt();
			
			if (input == countOfTypes + 1) {
				System.out.println("Exiting");
			}
			else if (input >= 0 & input <= countOfTypes + 1) {
				appliance = applianceFactory.selectAppliance(applianceTypes.get(input -1), newId);

			}
			else {
				System.out.println("Input invalid"); 
				System.out.println();

			}
			
		} while (input == countOfTypes + 1);
		
		System.out.println("You have added - " + appliance.getDetails());
		System.out.println();

		applianceDAO.addNew(appliance);
	}
	
	private void updateProduct() {
		
	}
	
	private void deleteProduct() {
		System.out.println("Please enter ID");
		System.out.println();
		
		int input = handleInput.getInputInt();
		
		Boolean deleted = applianceDAO.deleteById(input);
		
		if (deleted) {
			System.out.println("ID: " + input + " Deleted");
			System.out.println();

		} else {
			System.out.println("Failed");
			System.out.println();

		}

		
	}
//
//	class CheckPattern {
//		Pattern pattern;
//		Matcher matcher;
//		
//		public CheckPattern(String input, String pattern) {
//			this.pattern  = Pattern.compile(pattern);
//			this.matcher = this.pattern.matcher(input);
//		
//		}
//		
//		public boolean matches() {
//			return matcher.matches();
//		}
//	}
}


