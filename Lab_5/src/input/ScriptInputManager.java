package input;

import java.io.IOException;
import java.util.Scanner;

/**
 * The class for reading script4 files.
 */
public class ScriptInputManager implements InputManager {
    private Scanner scanner;

    /**
     * @param scanner the Scanner entity
     */
    public ScriptInputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * @return scanner.nextLine()
     * @throws IOException if an IOException occurs
     */
    @Override
    public String readLine() throws IOException {
        return scanner.nextLine();
    }

    /**
     * @return scanner.hasNextLine()
     * @throws IOException if an IOException occurs
     */
    @Override
    public boolean hasNextLine() throws IOException {
        return scanner.hasNextLine();
    }
}
