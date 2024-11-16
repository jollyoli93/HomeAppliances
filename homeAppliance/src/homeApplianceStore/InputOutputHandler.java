package homeApplianceStore;

import java.util.InputMismatchException;
import java.util.Scanner;

public interface InputOutputHandler {
    int getInputInt();
    String getInputString();
    double getInputDouble();
    public void clearInput();
    
    void output(String message);
}

class ConsoleIOHandler implements InputOutputHandler {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public int getInputInt() {
    	while (true) {
			try {
				int next = scanner.nextInt();
				scanner.nextLine();

				return next;
			} catch (InputMismatchException e) {
				System.out.println("Invalid character please try again");
				scanner.nextLine();
				continue;
			}
		}
    }

    @Override
    public String getInputString() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty. Please try again.");
                    continue;
                }
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    @Override
    public double getInputDouble() {
        while (true) {
            try {
                double next = scanner.nextDouble();
                scanner.nextLine(); // Clear the buffer
                return next;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
                scanner.nextLine(); // Clear the invalid input
                continue;
            }
        }
    }
	
    @Override
    public void output(String message) {
        System.out.println(message);
    }
    
    public void clearInput() {
    	scanner.next();
    };
}

class MockIOHandler implements InputOutputHandler {
    private int mockIntValue = 0;
    private String mockStringValue = "";
    private double mockDoubleValue = 0.0;
    
    // Constructor for setting mock values
    public MockIOHandler(int mockInt, String mockString, double mockDouble) {
        this.mockIntValue = mockInt;
        this.mockStringValue = mockString;
        this.mockDoubleValue = mockDouble;
    }
    
    @Override
    public int getInputInt() {
        return mockIntValue;
    }

    @Override
    public String getInputString() {
        return mockStringValue;
    }

    @Override
    public double getInputDouble() {
        return mockDoubleValue;
    }

    @Override
    public void clearInput() {
        // No action needed for mock
    }

    @Override
    public void output(String message) {
        // Optional: Store output messages for testing
        System.out.println("Mock output: " + message);
    }
    
    // Setters for testing different scenarios
    public void setMockIntValue(int value) {
        this.mockIntValue = value;
    }
    
    public void setMockStringValue(String value) {
        this.mockStringValue = value;
    }
    
    public void setMockDoubleValue(double value) {
        this.mockDoubleValue = value;
    }
}