package commands;

import collection.CollectionManager;
import movie.Movie;
import output.OutputManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The UpdateCommand class. The command updates the value of the collection element whose id is equal to the given
 */
public class UpdateCommand implements Command{
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param outputManager the manager that outputs data
     * @param collectionManager the manager of the collection
     */
    UpdateCommand(OutputManager outputManager, CollectionManager collectionManager) {
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) throws IOException {
        if (!argument.isEmpty()) {
            boolean found = false;
            ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
            for (Map.Entry<String, Movie> element : elements)
                if (element.getValue().getId().equals(Integer.parseInt(argument))) {
                    found = true;
                    outputManager.printMessage("Обновляем поля элемента коллекции...");
                    collectionManager.insertMovie(element.getKey());
                }
            if (!found)
                outputManager.printErrorMessage("Элемента коллекции с id = " + argument + " нет.");
        }
        else
            outputManager.printErrorMessage("Необходимо задать аргумент!");
    }
}
