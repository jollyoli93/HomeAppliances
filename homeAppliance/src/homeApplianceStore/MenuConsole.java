//STUDENT NO. 24862664

package homeApplianceStore;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.ApplianceDao;
import printer.AppliancePrinter;

public class MenuConsole {
	String driver;
	String dbPath;
	ApplianceDao applianceDAO;
	ConsoleIOHandler handleInput;
	
	public MenuConsole() {
		String dbPath = "HomeAppliances.db";
		this.applianceDAO = new ApplianceDao(dbPath);
		this.handleInput = new ConsoleIOHandler();
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
						allProducts();
						break;
						
					case 2:
						productById();								
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
				
				System.out.println("Press 0 to continue or 6 to exit");
				input = scanner.nextInt();
				
		} while (input != 6);
		
		scanner.close();
	}

	private void allProducts() {
		ArrayList<HomeAppliance> list = applianceDAO.findAll();
		
		for (HomeAppliance obj : list) {
			AppliancePrinter print = new AppliancePrinter(obj);
			print.print();
		}
	}
	
	private void productById() {
		int id = handleInput.getInputInt();

		System.out.println("Searching for product " + id);
		applianceDAO.getById(id);
	}
	
	private void addProduct() {
		//ID and SKU are unique get db checker 
		int newId = 0;
		
		handleInput.clearInput(); // Clear the newline char
		String newSku;
		
		do {
			System.out.println("Enter SKU (Format as AA000)");

			newSku = handleInput.getInputString();
			
			if (newSku == null || newSku.trim().isEmpty()) {
	            System.out.println("SKU cannot be null or empty. Please try again.");
	            continue; 
		    }
			
			String skuPattern = "^[A-Z]{2}\\d{3}$";
			CheckPattern skuMatch = new CheckPattern(newSku, skuPattern);
			
			if (skuMatch.matches()) {
				System.out.println("SKU Added");
				System.out.println("-----------");
				break;
			} else {
				System.out.println("Incorrect format, please try again");
			}
		} while (true);
		
		System.out.println("Enter Description");
		String newDesc = handleInput.getInputString();
		
		System.out.println("Enter Category");
		String newCat = handleInput.getInputString();
		
		System.out.println("Enter Price");	
		double newPrice = 0.0;
		boolean validInput = false;

		do {
		    System.out.print("Enter the price: ");
		    
		    try {
		        newPrice = handleInput.getInputDouble();;
		        
		        if (newPrice < 1) {
		            System.out.println("Price must be greater than zero");
		        } else {
		            validInput = true; 
		        }
		    } catch (InputMismatchException e) {
		        System.out.println("Please enter a valid number for the price.");
		        handleInput.clearInput(); // Clear the invalid input
		    }
		} while (!validInput);
		
		HomeAppliance addAppliance = new HomeAppliance(newId, newSku, newDesc, newCat, newPrice);
		applianceDAO.addNew(addAppliance);
	}
	
	private void updateProduct() {
		
	}
	
	private void deleteProduct() {
		
	}


	class CheckPattern {
		Pattern pattern;
		Matcher matcher;
		
		public CheckPattern(String input, String pattern) {
			this.pattern  = Pattern.compile(pattern);
			this.matcher = this.pattern.matcher(input);
		
		}
		
		public boolean matches() {
			return matcher.matches();
		}
	}
}


