package output;

/**
 * interface OutputManager
 */
public interface OutputManager {
    /**
     * Prints a massage
     * @param message String message
     */
    void printMessage(String message);
    /**
     * Prints an error massage
     * @param errorMessage String errorMessage
     */
    void printErrorMessage (String errorMessage);
}


