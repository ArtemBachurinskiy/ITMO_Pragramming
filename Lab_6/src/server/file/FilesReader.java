package server.file;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Базовый интерфейс для чтения файлов.
 */
public interface FilesReader {
    void readFile(File file) throws FileNotFoundException;
}