package commands;

import collection.CollectionManager;
import movie.Movie;
import output.OutputManager;

import java.util.Map;

/**
 * The ShowCommand class. The command prints to standard output all elements of the collection in string representation.
 */
public class ShowCommand implements Command{
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    /**
     * @param outputManager the manager that outputs data
     * @param collectionManager the manager of the collection
     */
    ShowCommand (OutputManager outputManager, CollectionManager collectionManager) {
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollectionSize() == 0)
            outputManager.printErrorMessage("Коллекция пуста.");
        else {
            outputManager.printMessage("Элементы коллекции : ");
            for (Map.Entry<String, Movie> element : collectionManager.getCollectionElements()) {
                outputManager.printMessage(element.getKey() + " > " + element.getValue());
            }
        }
    }
}