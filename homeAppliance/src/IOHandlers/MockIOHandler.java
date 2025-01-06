package IOHandlers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A mock implementation of the InputOutputHandler interface for testing purposes.
 * Allows predefined inputs and captures output logs.
 * 
 * @author 24862664
 */
public class MockIOHandler implements InputOutputHandler {
    private Queue<Integer> mockIntValue = new LinkedList<>();
    private Queue<String> mockStringValue = new LinkedList<>();
    String outputLog = "";

    /**
     * Constructs a MockIOHandler with predefined integer inputs.
     * 
     * @param mockInt an array of integers to initialize the integer queue; can be null
     */
    public MockIOHandler(int[] mockInt) {
        for (int input : mockInt) {
            mockIntValue.offer(input);
        }
    }

    /**
     * Constructs a MockIOHandler with predefined string inputs.
     * 
     * @param mockString an array of strings to initialize the string queue; can be null
     */
    public MockIOHandler(String[] mockString) {
        for (String input : mockString) {
            mockStringValue.offer(input);
        }
    }

    /**
     * Retrieves the next integer input from the mock queue or returns a default value.
     * 
     * @return the next integer input or 8 if the queue is empty
     */
    @Override
    public int getInputInt() {
        if (mockIntValue.isEmpty()) {
            return 8;
        }
        return mockIntValue.poll();
    }

    /**
     * Retrieves the next string input from the mock queue or returns a default value.
     * 
     * @return the next string input or "q" if the queue is empty
     */
    @Override
    public String getInputString() {
        if (mockStringValue.isEmpty()) {
            return "q";
        }
        return mockStringValue.poll();
    }

    /**
     * Retrieves a double input. Always returns 0 in this mock implementation.
     * 
     * @return 0.0 as a placeholder value
     */
    @Override
    public double getInputDouble() {
        return 0;
    }

    /**
     * Clears input buffer. Not applicable in this mock implementation.
     */
    @Override
    public void clearInput() {
        System.out.println("Method not applicable");
    }

    /**
     * Captures the given message as output.
     * 
     * @param message the message to capture
     * @return the captured message
     */
    @Override
    public String output(String message) {
        return outputLog = message;
    }

    /**
     * Retrieves all captured output messages.
     * 
     * @return the output log as a string
     */
    public String getOutputLog() {
        return outputLog.toString();
    }
}
