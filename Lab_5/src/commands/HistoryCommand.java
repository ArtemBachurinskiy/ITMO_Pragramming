package commands;

import output.OutputManager;
import java.util.ArrayList;

/**
 * The HistoryCommand class. The command prints the last 9 commands (without their arguments).
 */
public class HistoryCommand implements Command {
    private ArrayList<String> commandHistory;
    private OutputManager outputManager;

    /**
     * @param commandHistory an ArrayList of used commands
     * @param outputManager the manager that outputs data
     */
    HistoryCommand (ArrayList<String> commandHistory, OutputManager outputManager) {
        this.commandHistory = commandHistory;
        this.outputManager = outputManager;
    }

    @Override
    public void execute(String argument) {
        int beginIndex;
        final int COMMANDS_TO_PRINT = 9;
        if (commandHistory.size() <= COMMANDS_TO_PRINT) {
            beginIndex = 0;
            outputManager.printMessage("Последние исполненные команды (" + commandHistory.size() + ") : " );
        }
        else {
            beginIndex = commandHistory.size() - COMMANDS_TO_PRINT;
            outputManager.printMessage("Последние исполненные команды (" + COMMANDS_TO_PRINT + ") : ");
        }

        for (int i = beginIndex; i < commandHistory.size(); i++) {
            outputManager.printMessage(commandHistory.get(i));
        }
    }
}
