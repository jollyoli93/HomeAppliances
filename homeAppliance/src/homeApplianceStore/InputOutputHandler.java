package homeApplianceStore;

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
        return scanner.nextInt();
    }

	@Override
	public String getInputString() {
        return scanner.nextLine();
	}

	@Override
	public double getInputDouble() {
        return scanner.nextDouble();
	}
	
    @Override
    public void output(String message) {
        System.out.println(message);
    }
    
    public void clearInput() {
    	scanner.nextInt();
    	scanner.next();
    };
}
