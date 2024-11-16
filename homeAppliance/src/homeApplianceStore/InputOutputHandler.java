package homeApplianceStore;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public interface InputOutputHandler {
	public int getInputInt();
	public String getInputString();
	public double getInputDouble();
    public void clearInput();
    public String output(String message);
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
    public String output(String message) {
		return message;
    }
    
    public void clearInput() {
    	scanner.next();
    };
}



class MockIOHandler implements InputOutputHandler {
    private Queue<Integer> mockIntValue = new LinkedList<>();
    String outputLog = "";

    public MockIOHandler(int[] mockInt) {
        for (int input : mockInt) {
        	mockIntValue.offer(input);
        }
    }

	@Override
    public int getInputInt() {
        if (mockIntValue.isEmpty()) {
        	return 6;
        }
		return mockIntValue.poll();
    }

    @Override
    public String getInputString() {
        return null;
    }

    @Override
    public double getInputDouble() {
        return 0;
    }

    @Override
    public void clearInput() {
        // Not applicable
    }

    @Override
    public String output(String message) {
        return outputLog = message;
    }

    // Method to get all captured output
    public String getOutputLog() {
        return outputLog.toString();
    }
   
}