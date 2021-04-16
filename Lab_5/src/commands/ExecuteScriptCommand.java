package commands;

import application.Application;
import collection.CollectionManager;
import input.InputManager;
import input.ScriptInputManager;
import output.FilesWriter;
import output.OutputManager;

import java.io.*;
import java.util.Scanner;

/**
 * The ExecuteScriptCommand class. The command executes a script4 file with commands.
 * The commands are written in it in the same way as the user writes them in interactive mode.
 */
public class ExecuteScriptCommand implements Command {
    private Application application;
    private OutputManager outputManager;
    private CollectionManager collectionManager;
    private FilesWriter filesWriter;

    /**
     * @param outputManager the manager that outputs data
     * @param application the Application entity
     * @param collectionManager the manager of the collection
     * @param filesWriter the FilesWriter entity that writes data to a file
     */
    ExecuteScriptCommand(OutputManager outputManager, Application application, CollectionManager collectionManager, FilesWriter filesWriter) {
        this.outputManager = outputManager;
        this.application = application;
        this.collectionManager = collectionManager;
        this.filesWriter = filesWriter;
    }

    @Override
    public void execute(String scriptName) throws IOException {
        if (!scriptName.isEmpty()) {
            File scriptFile = new File(scriptName);
            if (application.getUsedScriptFiles().contains(scriptName)) {
                outputManager.printErrorMessage("execute_script не может вызывать ранее использованный скрипт-файл " + '\"' + scriptName + '\"' + "! Пропускаем данную команду execute_script..." + '\n');
            }
            else if (!scriptFile.exists()) {
                outputManager.printErrorMessage("Файла скрипта с именем \"" + scriptName + "\" не существует.");
            }
            else if (!scriptFile.canRead()) {
                outputManager.printErrorMessage("Отсутствуют права доступа на чтение файла скрипта \"" + scriptName + "\"!");
            }
            else {
                application.getUsedScriptFiles().add(scriptName);
                InputManager inputManager = new ScriptInputManager(new Scanner(new FileReader(scriptFile)));
                CommandManager commandManager = new CommandManager(application, outputManager, collectionManager, filesWriter);
                String fullCommand;
                String command;
                String argument;
                while (inputManager.hasNextLine() && !application.exitIsSet()) {
                    argument = "";
                    fullCommand = inputManager.readLine();
                    fullCommand = fullCommand.trim();

                    if (fullCommand.contains(" ")) {
                        command = fullCommand.substring(0, fullCommand.indexOf(" "));
                        argument = fullCommand.substring(fullCommand.indexOf(" ") + 1);
                        argument = argument.trim();
                    } else
                        command = fullCommand;

                    if (!command.equals("")) {
                        if (!argument.equals(""))
                            outputManager.printMessage(command + " " + argument + " (" + scriptName + ")");
                        else
                            outputManager.printMessage(command + " (" + scriptName + ")");
                    }
                    commandManager.manageCommand(command, argument);
                    if (!command.equals("") && !command.equals("execute_script") && inputManager.hasNextLine()) { //если исполнялась какая-то команда и она была не execute_script
                        outputManager.printMessage("");
                    }
                }
                outputManager.printMessage("");
                application.getUsedScriptFiles().remove(scriptName);
            }
        }
        else
            outputManager.printErrorMessage("Необходимо задать имя файла скрипта!");
    }
}
