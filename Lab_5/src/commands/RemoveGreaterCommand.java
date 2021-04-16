package commands;

import collection.CollectionManager;
import output.OutputManager;
import java.util.Iterator;
import java.util.Set;

/**
 * The RemoveGreaterCommand class.
 * The command removes all items from the collection that are greater than the specified one.
 * In order to know which ones we will remove we should use the show command first.
 */
public class RemoveGreaterCommand implements Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    RemoveGreaterCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) {
        if (!argument.isEmpty())
        {
            boolean found = false;
            Set<String> set = collectionManager.getCollectionKeySet();
            for (String string : set)
                if (string.equals(argument)) {
                    found = true;
                    break;
                }
            if (found) {
                boolean removed = false;
                Iterator<String> iterator = set.iterator();
                while (iterator.hasNext()) {
                    String string = iterator.next();
                    if (!argument.equals(string)) {
                        iterator.remove();
                        removed = true;
                    } else break;
                }
                if (removed)
                    outputManager.printMessage("Элементы успешно удалены!");
            }
            else
                outputManager.printErrorMessage("В коллекции нет элемента с ключом \'" + argument + "\'!");
        }
        else
            outputManager.printErrorMessage("Необходимо задать аргумент!");
    }
}
