/**
 * Abstract class representing a printer that uses different print behaviors.
 * It allows setting a specific print behavior and performing the print operation.
 * 
 * @author 24862664
 */
package printer;

public abstract class Printer {
    protected PrintBehaviour printBehaviour;

    /**
     * Default constructor.
     */
    public Printer() {
    }

    /**
     * Sets the print behavior for this printer.
     * 
     * @param printType The print behavior to set.
     */
    public void setPrinter(PrintBehaviour printType) {
        this.printBehaviour = printType;
    }

    /**
     * Prints the details using the current print behavior.
     */
    public void print() {
        printBehaviour.print();
    }
}
