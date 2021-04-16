package application;

import collection.CollectionManager;
import commands.CommandManager;
import file.FileManager;
import input.*;
import movie.ConsoleMovieBuilder;
import movie.ConsolePersonBuilder;
import movie.MovieBuilder;
import movie.PersonBuilder;
import output.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * The Application class.
 */
public class Application {
    private boolean exit;
    private InputManager inputManager;
    private CommandManager commandManager;
    private ArrayList<String> usedScriptFiles = new ArrayList<>();

    /**
     * The method that returns the ArrayList of used script4 files to avoid recursion.
     * @return the ArrayList of used script4 files
     */
    public ArrayList<String> getUsedScriptFiles() {
        return usedScriptFiles;
    }

    /**
     * The Application constructor.
     */
    public Application() {
        exit = false;
    }

    /**
     * @return true if we want to end the program;
     *         false otherwise
     */
    public boolean exitIsSet() {
        return exit;
    }

    /**
     * The method that starts the program.
     * @param args parameters of the command line
     */
    public void start(String[] args) throws FileNotFoundException {
        OutputManager outputManager = new ConsoleOutputManager();
        this.inputManager = new ConsoleInputManager(new BufferedReader(new InputStreamReader(System.in)));
        if (args.length == 0) {
            outputManager.printErrorMessage("Имя файла должно передаваться программе как аргумент командной строки.");
            System.exit(-1);
        }
        Validator validator = new Validator(outputManager);
        PersonReader personReader = new ConsolePersonReader(inputManager, outputManager, validator);
        MovieReader movieReader = new ConsoleMovieReader(inputManager, outputManager, validator);
        PersonBuilder personBuilder = new ConsolePersonBuilder(personReader);
        MovieBuilder movieBuilder = new ConsoleMovieBuilder(movieReader, personBuilder);
        CollectionManager collectionManager = new CollectionManager(outputManager, movieBuilder);
        FileManager fileManager = new FileManager(args, outputManager, collectionManager);
        fileManager.manageFile();
        FilesWriter filesWriter = new XMLFilesWriter(collectionManager, fileManager, outputManager);
        this.commandManager = new CommandManager(this, outputManager, collectionManager, filesWriter);
        run();
    }

    /**
     * The method that runs the main loop.
     */
    private void run() {
        String fullCommand;
        String command;
        String argument;

        while (!exit) {
            try {
                argument = "";
                fullCommand = inputManager.readLine();
                fullCommand = fullCommand.trim();

                if (fullCommand.contains(" ")) {
                    command = fullCommand.substring(0, fullCommand.indexOf(" "));
                    argument = fullCommand.substring(fullCommand.indexOf(" ") + 1);
                    argument = argument.trim();
                } else
                    command = fullCommand;

                commandManager.manageCommand(command, argument);
            }
            catch (IOException ignored) {
            }
        }
    }

    public void setExit() {
        exit = true;
    }
}
