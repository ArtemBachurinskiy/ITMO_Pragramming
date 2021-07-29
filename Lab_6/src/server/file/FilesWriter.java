package server.file;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

/**
 * Интерфейс, предназначенный для записи данных коллекции в файл.
 */
public interface FilesWriter {
    void writeToFile() throws ParserConfigurationException, TransformerException, FileNotFoundException;
}
