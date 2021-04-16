package commands;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * The Command interface is the basic interface for all commands.
 */
public interface Command {
    /**
     * The method that executes the command.
     * @param argument the command's argument, if needed
     * @throws IOException if an IOException occurs
     * @throws TransformerException if a TransformerException occurs
     * @throws ParserConfigurationException if a ParserConfigurationException occurs
     */
    void execute(String argument) throws IOException, TransformerException, ParserConfigurationException;
}