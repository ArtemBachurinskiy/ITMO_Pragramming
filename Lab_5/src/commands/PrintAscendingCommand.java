package commands;

import collection.CollectionManager;
import movie.Movie;
import output.OutputManager;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The PrintAscendingCommand class. The command displays the elements of the collection in ascending order.
 */
public class PrintAscendingCommand implements Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    PrintAscendingCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }
    @Override
    public void execute(String argument) {
        outputManager.printMessage("Выводим элементы в порядке возрастания (ключи по алфавиту): ");
        Set<String> keySet = collectionManager.getCollectionKeySet();
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        TreeSet<String> keyTreeSet = new TreeSet<>(keySet);
        for (String string : keyTreeSet) {
            for (Map.Entry<String, Movie> element : elements)
                if (element.getKey().equals(string))
                    outputManager.printMessage(string + " > " + element.getValue());
        }
    }
}
