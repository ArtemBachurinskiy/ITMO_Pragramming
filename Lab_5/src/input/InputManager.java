package input;

import java.io.IOException;

/**
 * The basic interface for all input.
 */
public interface InputManager {

    /**
     * The method reads the input line.
     * @return the input line
     * @throws IOException if an IOException occurs
     */
    String readLine() throws IOException;

    /**
     * Checks if there is a next line.
     * @return true if there is a next line;
     *         false otherwise
     * @throws IOException if an IOException occurs
     */
    boolean hasNextLine() throws IOException;
}
