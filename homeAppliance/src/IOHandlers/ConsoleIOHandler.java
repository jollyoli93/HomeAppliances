package IOHandlers;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles console-based input and output operations.
 * 
 * @author 24862664
 */
public class ConsoleIOHandler implements InputOutputHandler {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer input from the console.
     * Retries until valid input is provided.
     * 
     * @return the integer input
     */
    @Override
    public int getInputInt() {
        while (true) {
            try {
                int next = scanner.nextInt();
                scanner.nextLine();
                return next;
            } catch (InputMismatchException e) {
                System.out.println("Invalid character, please try again.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Reads a non-empty string input from the console.
     * Retries until valid input is provided.
     * 
     * @return the string input
     */
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

    /**
     * Reads a double input from the console.
     * Retries until valid input is provided.
     * 
     * @return the double input
     */
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
            }
        }
    }

    /**
     * Outputs a message to the console.
     * 
     * @param message the message to be output
     * @return the output message
     */
    @Override
    public String output(String message) {
        return message;
    }

    /**
     * Clears any remaining input from the scanner buffer.
     */
    public void clearInput() {
        scanner.next();
    }
}
