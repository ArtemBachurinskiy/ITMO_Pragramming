package general.output;

/**
 * Интерфейс OutputManager
 */
public interface OutputManager {
    /**
     * Печатает сообщение.
     * @param message печатуемое сообщение.
     */
    void printlnMessage(String message);
    /**
     * Печатает сообщение об ошибке
     * @param errorMessage печатуемое сообщение.
     */
    void printlnErrorMessage(String errorMessage);
}


