package IOHandlers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Constructs a MockIOHandler with predefined integer and string inputs.
 *
 * @param mockInt    an array of integers to initialize the integer queue; can be null.
 * @param mockString an array of strings to initialize the string queue; can be null.
 */

public class MockIOHandler implements InputOutputHandler {
    private Queue<Integer> mockIntValue = new LinkedList<>();
    private Queue<String> mockStringValue = new LinkedList<>();
    
    String outputLog = "";

    public MockIOHandler(int[] mockInt) {
        for (int input : mockInt) {
        	mockIntValue.offer(input);
        }
    }
    
    public MockIOHandler(String[] mockString) {
        for (String input : mockString) {
        	mockStringValue.offer(input);
        }
    }
    
    public MockIOHandler(int[] mockInt, String[] mockString) {
    	for (int input : mockInt) {
        	mockIntValue.offer(input);
        }
        for (String input : mockString) {
        	mockStringValue.offer(input);
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
        if (mockStringValue.isEmpty()) {
        	return "6";
        }
		return mockStringValue.poll();
    }

    @Override
    public double getInputDouble() {
        return 0;
    }

    @Override
    public void clearInput() {
    	System.out.println("Method not applicable");
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
