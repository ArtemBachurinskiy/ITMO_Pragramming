package commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * The Invoker class. This class invokes a specific given command.
 */
class Invoker {
    private Command command;

    /**
     * @param command A specific given command.
     */
    Invoker(Command command) {
        this.command = command;
    }

    /**
     * @param argument the argument needed for the specific command
     * @throws IOException if an IOException occurs
     * @throws TransformerException if a TransformerException occurs
     * @throws ParserConfigurationException if a ParserConfigurationException occurs
     */
    void executeCommand(String argument) throws IOException, TransformerException, ParserConfigurationException {
        command.execute(argument);
    }
}
