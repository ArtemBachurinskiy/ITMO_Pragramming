package commands;

import collection.CollectionManager;
import output.OutputManager;
import java.io.IOException;

/**
 * The InsertCommand class. The command inserts a new item with the given key in the collection.
 */
public class InsertCommand implements Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    InsertCommand (CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) throws IOException {
        if (!argument.isEmpty())
            collectionManager.insertMovie(argument);
        else
            outputManager.printErrorMessage("Необходимо задать аргумент!");
    }
}