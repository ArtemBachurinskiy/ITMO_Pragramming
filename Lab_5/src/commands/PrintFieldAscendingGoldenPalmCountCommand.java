package commands;

import collection.CollectionManager;
import movie.Movie;
import output.OutputManager;
import java.util.ArrayList;
import java.util.Map;

/**
 * The PrintFieldAscendingGoldenPalmCountCommand class.
 * The command displays the values of the goldenPalmCount field of all elements in ascending order.
 */
public class PrintFieldAscendingGoldenPalmCountCommand implements Command {
    private OutputManager outputManager;
    private CollectionManager collectionManager;

    /**
     * @param collectionManager the manager of the collection
     * @param outputManager the manager that outputs data
     */
    PrintFieldAscendingGoldenPalmCountCommand(CollectionManager collectionManager, OutputManager outputManager) {
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }
    @Override
    public void execute(String argument) {
        ArrayList<Long> golden_palms = new ArrayList<>();
        ArrayList<Map.Entry<String, Movie>> elements = collectionManager.getCollectionElements();
        for (Map.Entry<String, Movie> entry : elements) {
            golden_palms.add(entry.getValue().getGoldenPalmCount());
        }

        Long t;
        boolean changeDone = true;
        while (changeDone) {
            changeDone = false;
            for (int i = 0; i < golden_palms.size() - 1; i++)
                if (golden_palms.get(i) > golden_palms.get(i + 1)) {
                    t = golden_palms.get(i + 1);
                    golden_palms.set(i + 1, golden_palms.get(i));
                    golden_palms.set(i, t);
                    changeDone = true;
                }
        }
        for (Long l : golden_palms)
            outputManager.printMessage(l.toString());
    }
}
