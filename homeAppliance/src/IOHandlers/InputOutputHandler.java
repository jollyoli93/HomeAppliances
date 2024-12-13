package IOHandlers;

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
