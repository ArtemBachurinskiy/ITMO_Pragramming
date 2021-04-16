package commands;

import collection.CollectionManager;
import output.OutputManager;

/**
 * The InfoCommand class.
 * The command prints information about the collection (type, initialization date, number of elements, etc.)
 * to the standard output stream.
 */
public class InfoCommand implements Command{
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    /**
     * @param outputManager the manager that outputs data
     * @param collectionManager the manager of the collection
     */
    InfoCommand (OutputManager outputManager, CollectionManager collectionManager) {
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        outputManager.printMessage("Информация о данной коллекции : \n" +
                "Тип : " + collectionManager.getCollectionType() + '\n' +
                "Дата иниацилизации : " + collectionManager.getCollectionCreationDate() + '\n' +
                "Количество элементов : " + collectionManager.getCollectionSize());
    }
}
