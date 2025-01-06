package IOHandlers;

/**
 * Interface for handling input and output operations.
 * 
 * @author 24862664
 */
public interface InputOutputHandler {

    /**
     * Reads an integer input.
     * 
     * @return the integer input
     */
    public int getInputInt();

    /**
     * Reads a string input.
     * 
     * @return the string input
     */
    public String getInputString();

    /**
     * Reads a double input.
     * 
     * @return the double input
     */
    public double getInputDouble();

    /**
     * Clears any remaining input from the input buffer.
     */
    public void clearInput();

    /**
     * Outputs a message.
     * 
     * @param message the message to output
     * @return the output message
     */
    public String output(String message);
}
