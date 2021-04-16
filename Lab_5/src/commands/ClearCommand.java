package commands;

import collection.CollectionManager;

/**
 * The ClearCommand class. The command clears the collection of elements.
 */
public class ClearCommand implements Command {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager the manager of the collection
     */
    ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String argument) {
        collectionManager.ClearCollection();
    }
}
