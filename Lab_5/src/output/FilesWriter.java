package output;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

/**
 * The interface which is supposed to write data from the collection to the file
 */
public interface FilesWriter {
    /**
     * Writes data from the collection to the file
     */
    void writeToFile() throws ParserConfigurationException, TransformerException, FileNotFoundException;
}
