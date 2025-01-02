//STUDENT NO. 24862664

package console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DAO.ApplianceDao;
import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;
import appliances.Appliance;
import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;
import printer.AppliancePrinter;
import printer.Printer;

public class ApplianceConsole {
    private ApplianceDao applianceDAO;
    private InputOutputHandler handleInput;
    private String dbPath;
	private String consoleOutput = null;

	public ApplianceConsole(String dbPath) {
		this.dbPath = dbPath;
		this.handleInput = new ConsoleIOHandler();
		
		this.applianceDAO = new ApplianceDao(dbPath);
	}
	
	public void setHandler(InputOutputHandler handleInput) {
		this.handleInput = handleInput;
	}
	
	public String displayMenu() {
		int input = 0;
		
		while (input != 6) {
			System.out.println("------------------------");
			System.out.println("Choose from these options");
			System.out.println("------------------------");
			
			
			System.out.println("[1] List all products");
			System.out.println("[2] Search by the product ID");
			System.out.println("[3] Add a new product");
			System.out.println("[4] Update a product");
			System.out.println("[5] Delete a product by ID");
			System.out.println("[6] Back");
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
						updatePrice();
						System.out.println(consoleOutput);
						System.out.println();
						break;
						
					case 5:
						deleteProduct();
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
		
		consoleOutput = handleInput.output("Exiting");
		System.out.println(consoleOutput);
		return consoleOutput;
		
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
			
			appliance = applianceDAO.getAppliance(id);
			
			printer = new AppliancePrinter(appliance);
			printer.print();
			
		} catch (NullPointerException e) {
			consoleOutput = handleInput.output("No item found in the database");
			System.out.println(consoleOutput);
			System.out.println();
		}

	}
	
	private void addProduct() {
		ApplianceDepartments applianceFactory = selectDepartment();
		Appliance appliance = selectAppliance(applianceFactory);
		int userInput;
		boolean added = false;
		
		System.out.println("How many would you like to add to your stock?");
		
		userInput = handleInput.getInputInt();

		
		for (int i = 0; i < userInput; i++) {
			added = applianceDAO.addNewAppliance(appliance, null);
			
			if (added == false) {
				System.out.println("Failed to add");
				break;
			}
		} 
		
		if (added == true) 
				System.out.println("You have added " + userInput + " x " + appliance.getDescription());
				System.out.println();

	}
	
	private ApplianceDepartments selectDepartment () {
		ApplianceDepartments applianceFactory = null;
		
		int userInput = 0;
		do {
			System.out.println("Please select department from the list below");
			System.out.println("[1] Entertainment");
			System.out.println("[2] Home Cleaning");
			System.out.println("[3] Go back - fix bug");
			
			userInput = handleInput.getInputInt();
			
			switch (userInput) {
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
		} while (userInput == 3);
		
		return applianceFactory;
	}
	
	private Appliance selectAppliance (ApplianceDepartments factory) {
		ApplianceDepartments applianceFactory = factory;
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		Appliance appliance = null;
		
		int userInput;
		int sizeOfTypesList = applianceTypes.size();
		int endOfList = sizeOfTypesList + 1;
		
		do {
			System.out.println("Please select an appliance type");
			System.out.println();

			
			for (int i =  0; i < sizeOfTypesList; i++) {
				String applianceType =  applianceTypes.get(i);
				int printUserSelectionIndex = i + 1;
				
				System.out.println("[" + printUserSelectionIndex + "] " + applianceType);
			}
			
			userInput = handleInput.getInputInt();
			
			if (userInput == endOfList) {
				System.out.println("Exiting");
			}
			else if (userInput >= 0 & userInput <= sizeOfTypesList + 1) {
				appliance = applianceFactory.selectAppliance(applianceTypes.get(userInput -1));
				return appliance;

			}
			else {
				System.out.println("Input invalid"); 
				System.out.println();

			}
			
		} while (userInput == sizeOfTypesList + 1);
		
		return appliance;
	}
	
	private void updatePrice() {
		System.out.println("Please select an option");
		System.out.println("[1] Update a product price by ID");
		int userInputID = handleInput.getInputInt();
		
		switch (userInputID) {
			case 1: 
				updatePriceByID();
				break;
		}
	}
	
	private String updatePriceByID() {
		System.out.println("Enter ID to update price");
		int userInputID = handleInput.getInputInt();
		
		System.out.println("Enter new price");
		double userInputPrice = handleInput.getInputDouble();
		
		try {
			consoleOutput = applianceDAO.updateFieldById(userInputID, "price", userInputPrice);
			
		} catch (NullPointerException ex) {
			System.out.println("Product not in database");
		}
		return consoleOutput;
	}

	private void deleteProduct() {
		System.out.println("Please enter ID");
		System.out.println();
		
		int input = handleInput.getInputInt();
		
		int deleted = applianceDAO.deleteApplianceById(input);
		
		if (deleted > 0) {
			consoleOutput = "ID: " + input + " Deleted";
			System.out.println("ID: " + input + " Deleted");
			System.out.println();

		} else {
			consoleOutput = "Failed to delete item.";
			System.out.println("Failed");
			System.out.println();

		}
	}

}


