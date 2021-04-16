package commands;

import output.FilesWriter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

/**
 * The SaveCommand class. The command saves the collection to file.
 */
public class SaveCommand implements Command {
    private FilesWriter filesWriter;

    /**
     * @param filesWriter the FilesWriter entity that writes data to a file
     */
    SaveCommand(FilesWriter filesWriter) {
        this.filesWriter = filesWriter;
    }
    @Override
    public void execute(String argument) throws FileNotFoundException, TransformerException, ParserConfigurationException {
        filesWriter.writeToFile();
    }
}
