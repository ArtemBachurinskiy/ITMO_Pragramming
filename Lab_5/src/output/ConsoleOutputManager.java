package output;

/**
 * The class which is intended to output data on the console.
 */
public class ConsoleOutputManager implements OutputManager{
    /**
     * Prints a massage to the console
     * @param message String message
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints an error message to the console
     * @param errorMessage String message
     */
    @Override
    public void printErrorMessage(String errorMessage) {
        System.out.println("ERROR: " + errorMessage);
    }
}
