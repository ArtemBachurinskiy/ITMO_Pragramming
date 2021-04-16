package file;

import collection.CollectionManager;
import input.FilesReader;
import input.XMLFilesReader;
import output.OutputManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The class that manages the file, which name is given via arguments of command line.
 */
public class FileManager {
    private String[] args;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private File file;

    /**
     * @param args the arguments of command line
     * @param outputManager the manager that outputs data
     * @param collectionManager the manager of the collection
     */
    public FileManager(String[] args, OutputManager outputManager, CollectionManager collectionManager) {
        this.args = args;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    /**
     * @return this.file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * The method creates a File entity from arguments of command line, if possible.
     * It checks many aspects of the file in order to work with it.
     * @throws FileNotFoundException if a FileNotFoundException occurs
     */
    public void manageFile() throws FileNotFoundException {
        StringBuilder filename = new StringBuilder();
        for (int i = 0; i < args.length; i++)
        {
            filename.append(args[i]);
            if (filename.toString().contains(".xml"))
                break;
            if (i < args.length - 1)
                filename.append(" "); // восстанавливаем пробелы
        }
        if (!filename.toString().contains(".xml"))
            filename.append(".xml");
        file = new File(filename.toString());

        // если файла с таким именем не существует
        if (!file.exists()) {
            try {
                boolean fileNameIsWright = file.createNewFile();
                if (fileNameIsWright)
                    outputManager.printErrorMessage("Файла с таким именем не существует. Файл с заданным именем будет создан автоматически.");
                else {
                    outputManager.printErrorMessage("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                    file = new File("Movies.xml");
                }
            }
            catch (IOException | SecurityException e) {
                outputManager.printErrorMessage("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                file = new File("Movies.xml");
            }
        }
        else {
            if (!file.canRead()) {
                outputManager.printErrorMessage("Отсутствуют права доступа на чтение файла.");
                System.exit(-1);
            }
            if (!file.canWrite()) {
                outputManager.printErrorMessage("Отсусвуют права доступа на запись в файл.");
                System.exit(-1);
            }
            if (file.length() != 0) {
                FilesReader filesReader = new XMLFilesReader(outputManager, collectionManager);
                filesReader.readFile(file);
            }
        }
    }
}
