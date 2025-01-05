//STUDENT NO. 24862664

package mainConsole;

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
import exceptions.InvalidUserIdException;
import printer.AppliancePrinter;
import printer.Printer;
import util.Util;

public class ApplianceConsole {
    private ApplianceDao applianceDAO;
    private InputOutputHandler handleInput;
    private String dbPath;
	private String consoleOutput = "";

	public ApplianceConsole(String dbPath) {
		this.dbPath = dbPath;
		this.handleInput = new ConsoleIOHandler();
		
		this.applianceDAO = new ApplianceDao(dbPath);
	}
	
	public void setHandler(InputOutputHandler handleInput) {
		this.handleInput = handleInput;
	}
	
	public String displayMenu() {
	    String input = "0";
	    boolean flag = true;
	    int count = 0;

	    while (flag) {
	        System.out.println("------------------------");
	        System.out.println("Choose from these options");
	        System.out.println("------------------------");

	        System.out.println("[1] List all products");
	        System.out.println("[2] Search by the product ID");
	        System.out.println("[3] Add a new product");
	        System.out.println("[4] Update a product");
	        System.out.println("[5] Delete a product by ID");
	        System.out.println("[q] Back");
	        System.out.println();

	        input = handleInput.getInputString();

	        switch (input) {
	            case "1":
	                consoleOutput = getAllProducts();
	                System.out.println(consoleOutput);
	                break;

	            case "2":
	            	consoleOutput = getProductById();
	                System.out.println(consoleOutput);

	                break;

	            case "3":
	                consoleOutput = addProduct();
	                System.out.println(consoleOutput);

	                break;

	            case "4":
	                updatePrice();
	                System.out.println(consoleOutput);

	                break;

	            case "5":
	            	consoleOutput = deleteProduct();
	                System.out.println(consoleOutput);

	                break;

	            case "q":
	                System.out.println("Returning");
	                flag = false; // Set flag to false to exit the loop
	                break;

	            default:
	                count++;
	                if (count >= 3) {
	                    System.out.println("Too many invalid attempts. Exiting...");
	                    flag = false; // Exit after 3 invalid attempts
	                } else {
	                    System.out.println("Invalid option. Try again. " + (3 - count) + " attempts left.");
	                }
	                break;
	        }

	        if (flag) {
	            System.out.println("Type q to exit or any key to continue:");
	            input = handleInput.getInputString();
	            if ("q".equalsIgnoreCase(input)) {
	                flag = false; // Exit if user types q
	            }
	        }
	    }
	    return consoleOutput;
	}


	private String getAllProducts() {
		try {
			ArrayList<Appliance> list = applianceDAO.findAll(0, null);
			
			for (Appliance obj : list) {
				AppliancePrinter print = new AppliancePrinter(obj);
				print.print();
			}
			
			if(list.isEmpty()) {
				return "No products in the database";
			}
			
			return "Product list returned";
			
		} catch (NullPointerException e) {
			return "No products in the database";
		}
	}
	
	private String getProductById() {
	    Appliance appliance = null;
	    int id = 0;

	    try {
	        System.out.println("Enter Product ID");
	        String stringInput = handleInput.getInputString();
	        id = Integer.parseInt(stringInput);

	        System.out.println("Searching for product " + id);

	        appliance = applianceDAO.getAppliance(id);

	        if (appliance == null) {
	            return "No item found in the database";
	        } else {
	            AppliancePrinter printer = new AppliancePrinter(appliance);
	            printer.print(); // Print the appliance details.
	            consoleOutput = appliance.getDescription() + " found.";
	            return consoleOutput;
	        }
	    } catch (NumberFormatException e) {
	        consoleOutput = "Invalid product ID. Please enter a valid number.";
	        return consoleOutput;
	    } catch (Exception e) {
	        consoleOutput = "An error occurred while searching for the product.";
	        e.printStackTrace(); // Log the exception for debugging.
	        return consoleOutput;
	    }

	}

	
	private String addProduct() {
		ApplianceDepartments applianceFactory = selectDepartment();
		Appliance appliance = selectAppliance(applianceFactory);
		int userInput;
		boolean added = false;
		
		System.out.println("How many would you like to add to your stock?");
		
		String stringInput = handleInput.getInputString();
		userInput = Integer.parseInt(stringInput);

		
		for (int i = 0; i < userInput; i++) {
			added = applianceDAO.addNewAppliance(appliance, null);
			
			if (added == false) {
				return "Failed to add";
			}
			
			return "You have added " + userInput + " x " + appliance.getDescription();
			
		}
			
		return "Failed to add product to the database";

	}
	
	private ApplianceDepartments selectDepartment () {
		ApplianceDepartments applianceFactory = null;
		
		String stringInput = null;
		do {
			System.out.println("Please select department from the list below");
			System.out.println("[1] Entertainment");
			System.out.println("[2] Home Cleaning");
			System.out.println("[3] Go back - fix bug");
			
			stringInput = handleInput.getInputString();
			
			switch (stringInput) {
				case "1":
					applianceFactory = new EntertainmentFactory();
					break;
				case "2":
					applianceFactory = new HomeCleaningFactory();
					break;
				case "3":
					break;
				default:
					System.out.println("Please try again");
					System.out.println();

			}
		} while (stringInput == "3");
		
		return applianceFactory;
	}
	
	private Appliance selectAppliance (ApplianceDepartments factory) {
		ApplianceDepartments applianceFactory = factory;
		ArrayList<String> applianceTypes = applianceFactory.listAllApplianceTypes();
		Appliance appliance = null;
		
		String stringInput;
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
			
			stringInput = handleInput.getInputString();
			userInput = Integer.parseInt(stringInput);
			
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
		String stringInput = handleInput.getInputString();
		
		switch (stringInput) {
			case "1": 
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

	private String deleteProduct() {
		System.out.println("Please enter ID");
		System.out.println();
		
		String stringInput = handleInput.getInputString();
		int userInput = Integer.parseInt(stringInput);

		
		int deleted = applianceDAO.deleteApplianceById(userInput);
		
		if (deleted > 0) {
			consoleOutput = "ID: " + userInput + " Deleted";
			return consoleOutput;

		} else {
			return "Failed to delete item.";
		}
	}

}


