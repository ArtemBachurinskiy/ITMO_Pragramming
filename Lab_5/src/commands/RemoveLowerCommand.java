package commands;

import collection.CollectionManager;
import output.OutputManager;
import java.util.Iterator;
import java.util.Set;

/**
 * The RemoveLowerCommand class.
 * The command removes all elements from the collection that are less than the given one.
 * In order to know which ones we will remove we should use the show command first.
 */
public class RemoveLowerCommand implements Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    RemoveLowerCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }
    @Override
    public void execute(String argument) {
        // удалить из коллекции все элементы, меньшие, чем заданный,
        // т.е. ниже него по списку (узнать какие это эл-ты мы сможем выполнив команду show())

        if (!argument.isEmpty())
        {
            Set<String> setToRetain = collectionManager.getCollectionKeySet();
            boolean found = false;
            boolean removed = false;

            Iterator<String> iterator = setToRetain.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                if (argument.equals(string))
                    found = true;
                if (found && !argument.equals(string)) {
                    iterator.remove();
                    removed = true;
                }
            }
            if(!found)
                outputManager.printErrorMessage("В коллекции нет элемента с ключом \'" + argument + "\'!");
            if(removed)
                outputManager.printMessage("Элементы успешно удалены!");
        }
        else
            outputManager.printErrorMessage("Необходимо задать аргумент!");
    }
}
