package input;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * The basic interface for reading files.
 */
public interface FilesReader {
    /**
     * Read the given file.
     * @param file File entity
     * @throws FileNotFoundException if a FileNotFoundException occurs
     */
    void readFile(File file) throws FileNotFoundException;
}
