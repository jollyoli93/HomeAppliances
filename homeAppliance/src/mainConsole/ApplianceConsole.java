//STUDENT NO. 24862664

package mainConsole;

import java.util.ArrayList;
import java.util.Map;

import DAO.ApplianceDao;
import IOHandlers.ConsoleIOHandler;
import IOHandlers.InputOutputHandler;
import appliances.Appliance;
import appliances.ApplianceDepartments;
import appliances.EntertainmentFactory;
import appliances.HomeCleaningFactory;
import printer.AppliancePrinter;
import util.FactoryRegistry;
import util.Util;

/**
 * Main console interface for managing appliances, providing options to
 * list, search, add, update, and delete appliances in the database.
 * 
 * Handles user input and delegates database operations to the DAO layer.
 * 
 * @author 24862664
 */
public class ApplianceConsole {
    private final ApplianceDao applianceDAO;
    private InputOutputHandler handleInput;
    private final String dbPath;
    private String consoleOutput = "";
    private FactoryRegistry factoryRegistery;

    public ApplianceConsole(String dbPath) {
        this.dbPath = dbPath;
        this.handleInput = new ConsoleIOHandler();
        this.applianceDAO = new ApplianceDao(dbPath);
        this.factoryRegistery = new FactoryRegistry();
    }

    public void setHandler(InputOutputHandler handleInput) {
        this.handleInput = handleInput;
    }

    /**
     * Displays the main menu and handles user interaction.
     * 
     * @return The final console output message.
     */
    public String displayMenu() {
        String input;
        boolean flag = true;
        int invalidCount = 0;

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
                    break;

                case "2":
                    consoleOutput = getProductById();
                    break;

                case "3":
                    consoleOutput = addProduct();
                    break;

                case "4":
                    updatePrice();
                    break;

                case "5":
                    consoleOutput = deleteProduct();
                    break;

                case "q":
                    System.out.println("Returning");
                    flag = false;
                    continue;

                default:
                    invalidCount++;
                    if (invalidCount >= 3) {
                        System.out.println("Too many invalid attempts. Exiting...");
                        flag = false;
                    } else {
                        System.out.println("Invalid option. Try again. " + (3 - invalidCount) + " attempts left.");
                    }
                    continue;
            }

            if (flag) {
                System.out.println("Type q to exit or any key to continue:");
                input = handleInput.getInputString();
                if ("q".equalsIgnoreCase(input)) {
                    flag = false;
                }
            }
        }
        return consoleOutput;
    }

    /**
     * Retrieves and displays all products from the database.
     * 
     * @return The console output message.
     */
    private String getAllProducts() {
        try {
            ArrayList<Appliance> list = applianceDAO.findAll(0, null);

            for (Appliance obj : list) {
                new AppliancePrinter(obj).print();
            }

            return list.isEmpty() ? "No products in the database" : "Product list returned";
        } catch (NullPointerException e) {
            return "No products in the database";
        }
    }

    /**
     * Retrieves and displays a product by its ID.
     * 
     * @return The console output message.
     */
    private String getProductById() {
        try {
            System.out.println("Enter Product ID:");
            int id = Integer.parseInt(handleInput.getInputString());

            Appliance appliance = applianceDAO.getAppliance(id);

            if (appliance != null) {
                new AppliancePrinter(appliance).print();
                return appliance.getDescription() + " found.";
            }
            return "No item found in the database.";
        } catch (NumberFormatException e) {
            return "Invalid product ID. Please enter a valid number.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while searching for the product.";
        }
    }

    /**
     * Adds a new product to the database.
     * 
     * @return The console output message.
     */
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
	
    /**
     * Allows the user to dynamically select an appliance department from the factory method.
     * 
     * @return The selected ApplianceDepartments department type e.g. entertainment, home cleaning etc.
     */
    private ApplianceDepartments selectDepartment() {
        Map<String, ApplianceDepartments> factoryDeptMap = factoryRegistery.getDepartments();
        ArrayList<String> applianceDeptList = new ArrayList<>();
        ApplianceDepartments department = null;

        // Add department names to the list
        factoryDeptMap.forEach((key, value) -> {
            applianceDeptList.add(key);
        });

        int userInput;
        int sizeOfTypesList = applianceDeptList.size();
        int endOfList = sizeOfTypesList + 1;

        do {
            System.out.println("Please select a department:");
            System.out.println();

            // print departments to the user
            for (int i = 0; i < sizeOfTypesList; i++) {
                String deptType = applianceDeptList.get(i);  // Get department name from the list
                int printUserSelectionIndex = i + 1;
                System.out.println("[" + printUserSelectionIndex + "] " + deptType);
            }

            System.out.println("[" + endOfList + "] Exit");

            // Get user input
            String stringInput = handleInput.getInputString();
            userInput = Integer.parseInt(stringInput);

            if (userInput == endOfList) {
                System.out.println("Exiting");
                return null;  
            } else if (userInput > 0 && userInput <= sizeOfTypesList) {
                // Select the appliance department based on the user's input
                String selectedKey = (String) applianceDeptList.get(userInput - 1);  // Get the department key
                department = factoryDeptMap.get(selectedKey);  // Fetch department object by key
                return department;  // Return selected department
            } else {
                System.out.println("Input invalid. Please select a valid department number.");
                System.out.println();
            }

        } while (true); 
    }
	
    /**
     * Allows the user to dynamically select an appliance type from a department.
     * 
     * @return The selected Appliance.
     */
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
	
	/**
	 * 
	 * @return Returns message indicating whether the update was successful or if the database is empty.
	 */
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

	/**
	 *  @return A message indicating whether the deletion was successful or not.
	 */
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


