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

	@Override
	public int getInputInt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getInputString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getInputDouble() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clearInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void output(String message) {
		// TODO Auto-generated method stub
		
	}
	
}
