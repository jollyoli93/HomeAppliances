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
	String driver;
	String dbPath;
	ApplianceDao applianceDAO;
	ConsoleIOHandler handleInput;

	Map<String, ApplianceFactory> factories = new HashMap<String, ApplianceFactory>();

	public MenuConsole() {
		String dbPath = "HomeAppliances.db";
		initFactoriesMap();
		
		this.applianceDAO = new ApplianceDao(dbPath, factories);
		this.handleInput = new ConsoleIOHandler();
	}	
	
	private void initFactoriesMap () {
		ApplianceFactory entertainment = new EntertainmentFactory();
		ApplianceFactory homeCleaning = new HomeCleaningFactory();
		
		factories.put("Entertainment", entertainment);
		factories.put("Home Cleaning", homeCleaning);
	}
	
	public void displayMenu() {
//		Scanner scanner = new Scanner(System.in);
		
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
			
			input = handleInput.getInputInt();
			System.out.println(input);
			
				switch (input) {
					case 0:
						System.out.println("Try again");
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
//				
//				System.out.println("Press 0 to continue or 6 to exit");
//				input = handleInput.getInputInt();
				
		} 
		
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
		int id = 0;
		
		try {
			Appliance appliance = null;
			AppliancePrinter printer = null;
			
			System.out.println("Enter Product ID");
			
			id = handleInput.getInputInt();

			System.out.println("Searching for product " + id);
			appliance = applianceDAO.getById(id);
			
			printer = new AppliancePrinter(appliance);
			printer.print();
			
		} catch (NullPointerException e) {
			System.out.println("ID " + id + " does not exist in the database");
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
			}
		} while (input == 3);
		
		// list out concrete classes that are available for that particular factory. Must be called on the applianceFactory
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		int countOfTypes = applianceTypes.size();
		
		do {
			System.out.println("Please select an appliace to add");
			
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
			}
			
		} while (input == countOfTypes + 1);
		
		System.out.println("You have added - " + appliance.getDetails());
//		applianceDAO.addNew(appliance);
	}
	
	private void updateProduct() {
		
	}
	
	private void deleteProduct() {
		
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


