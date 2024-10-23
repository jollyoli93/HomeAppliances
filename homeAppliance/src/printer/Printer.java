package printer;

public abstract class Printer {
	PrintBehaviour printBehaviour;
	
	public Printer() {
	
	}
	
	public void setPrinter(PrintBehaviour printType) {
		this.printBehaviour = printType;
	}

	public void print() {
		printBehaviour.print();
	}
}

