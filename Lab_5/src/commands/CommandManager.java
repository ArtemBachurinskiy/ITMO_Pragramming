package commands;

import application.Application;
import collection.CollectionManager;
import output.FilesWriter;
import output.OutputManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The class with a Map of all the commands.
 */
public class CommandManager {
    private ArrayList<String> commandHistory = new ArrayList<>();
    private Map<String, Command> commandMap;
    private OutputManager outputManager;

    /**
     * CommandManager constructor.
     * @param application the Application entity
     * @param outputManager the manager that outputs data
     * @param collectionManager the manager of the collection
     * @param filesWriter the FilesWriter entity that writes data to a file
     */
    public CommandManager(Application application, OutputManager outputManager, CollectionManager collectionManager, FilesWriter filesWriter) {
        this.outputManager = outputManager;
        commandMap = new HashMap<>();
        commandMap.put("help", new HelpCommand(outputManager));
        commandMap.put("info", new InfoCommand(outputManager, collectionManager));
        commandMap.put("show", new ShowCommand(outputManager, collectionManager));
        commandMap.put("insert", new InsertCommand(collectionManager, outputManager));
        commandMap.put("update", new UpdateCommand(outputManager, collectionManager));
        commandMap.put("remove_key", new RemoveKeyCommand(collectionManager, outputManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("save", new SaveCommand(filesWriter));
        commandMap.put("execute_script", new ExecuteScriptCommand(outputManager, application, collectionManager, filesWriter));
        commandMap.put("exit", new ExitCommand(application));
        commandMap.put("remove_greater", new RemoveGreaterCommand(collectionManager, outputManager));
        commandMap.put("remove_lower", new RemoveLowerCommand(collectionManager, outputManager));
        commandMap.put("history", new HistoryCommand(commandHistory, outputManager));
        commandMap.put("max_by_golden_palm_count", new MaxByGoldenPalmCountCommand(collectionManager, outputManager));
        commandMap.put("print_ascending", new PrintAscendingCommand(collectionManager, outputManager));
        commandMap.put("print_field_ascending_golden_palm_count", new PrintFieldAscendingGoldenPalmCountCommand(collectionManager, outputManager));
    }

    /**
     * The method that chooses the Command entity from the commandMap and executes it via Invoker.
     * @param command the command to manage
     * @param argument the argument of the command
     * @throws IOException if an IOException occurs
     */
    public void manageCommand(String command, String argument) throws IOException {
        command = command.toLowerCase();
        if (commandMap.containsKey(command)) {
            commandHistory.add(command);
            try {
                Invoker invoker = new Invoker(commandMap.get(command));
                invoker.executeCommand(argument);
            }
            catch (NumberFormatException e)
            {
                outputManager.printErrorMessage("Неверный тип аргумента!");
            } catch (TransformerException | ParserConfigurationException e) {
                outputManager.printErrorMessage("Не удалось сохранить коллекцию в файл...");
            }
        }
        else if (!command.isEmpty()) {
            outputManager.printErrorMessage("Неопознанная команда. Наберите help для справки.");
        }
    }
}