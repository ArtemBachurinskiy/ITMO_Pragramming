package commands;

import collection.CollectionManager;
import output.OutputManager;

/**
 * The RemoveKeyCommand class. The command removes an item from the collection by its key.
 */
public class RemoveKeyCommand implements Command {
    private CollectionManager collectionManager;
    private OutputManager outputManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    RemoveKeyCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) {
        if (!argument.isEmpty())
            collectionManager.remove_key(argument);
        else
            outputManager.printErrorMessage("Необходимо задать аргумент!");
    }
}
