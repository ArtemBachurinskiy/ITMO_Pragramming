package commands;

import collection.CollectionManager;
import movie.Movie;
import output.OutputManager;
import java.util.ArrayList;
import java.util.Map;

/**
 * The MaxByGoldenPalmCountCommand class.
 * The command displays any object from the collection, the value of the goldenPalmCount field of which is the maximum
 */
public class MaxByGoldenPalmCountCommand implements Command {
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    MaxByGoldenPalmCountCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) {
        if (collectionManager.getCollectionSize() > 0) {
            long maxGoldenPalmCount = 0;
            int index = 0, indexOfMax = 0;
            ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
            for (Map.Entry<String, Movie> element : elements) {
                if (element.getValue().getGoldenPalmCount() > maxGoldenPalmCount) {
                    maxGoldenPalmCount = element.getValue().getGoldenPalmCount();
                    indexOfMax = index;
                }
                index++;
            }
            outputManager.printMessage(elements.get(indexOfMax).getKey() + " > " + elements.get(indexOfMax).getValue());
        }
        else
            outputManager.printErrorMessage("В коллекции нет элементов!");
    }
}
