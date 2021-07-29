package server.file;

import server.collection.CollectionManager;
import general.output.OutputManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс, предназначенный для управления файлом, хранящим коллекцию.
 * Имя файла передаётся серверному приложению как аргумент командной строки.
 */
public class FileManager {
    private String[] args;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private File file;

    public FileManager(String[] args, OutputManager outputManager, CollectionManager collectionManager) {
        this.args = args;
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    File getFile() {
        return this.file;
    }

    /**
     * Метод создаёт объект типа File.
     * Проверяет многие аспекты файла для дальнейшей работы с ним (его чтения).
     */
    public void manageFile(){
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
                    outputManager.printlnErrorMessage("Файла с таким именем не существует. Файл с заданным именем будет создан автоматически.");
                else {
                    outputManager.printlnErrorMessage("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                    file = new File("Movies.xml");
                }
            }
            catch (IOException | SecurityException e) {
                outputManager.printlnErrorMessage("Имя или путь к файлу неверны. При вводе команды save создастся файл с именем Movies.xml в текущей директории.");
                file = new File("Movies.xml");
            }
        }
        else {
            if (!file.canRead()) {
                outputManager.printlnErrorMessage("Отсутствуют права доступа на чтение файла.");
                System.exit(-1);
            }
            if (!file.canWrite()) {
                outputManager.printlnErrorMessage("Отсусвуют права доступа на запись в файл.");
                System.exit(-1);
            }
            if (file.length() != 0) {
                FilesReader filesReader = new XMLFilesReader(outputManager, collectionManager);
                try {
                    filesReader.readFile(file);
                }
                catch (FileNotFoundException ignored) {
                }
            }
        }
    }
}
