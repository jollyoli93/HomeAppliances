package IOHandlers;

import java.util.LinkedList;
import java.util.Queue;

public class MockIOHandler implements InputOutputHandler {
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
