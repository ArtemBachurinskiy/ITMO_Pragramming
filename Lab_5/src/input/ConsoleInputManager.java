package input;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The class which is intended to read from the console.
 */
public class ConsoleInputManager implements InputManager {
    private BufferedReader bufferedReader;

    /**
     * @param bufferedReader A BufferedReader entity
     */
    public ConsoleInputManager(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    /**
     * @return bufferedReader.readLine()
     * @throws IOException if an IOException occurs
     */
    @Override
    public String readLine() throws IOException{
        return bufferedReader.readLine();
    }

    /**
     * @return bufferedReader.ready()
     * @throws IOException if an IOException occurs
     */
    @Override
    public boolean hasNextLine() throws IOException {
        return bufferedReader.ready();
    }
}
