package commands;

import application.Application;

/**
 * The ExitCommand class. The command exits from the program (without saving the collection to the file)
 */
public class ExitCommand implements Command {
    private Application application;

    /**
     * @param application the Application entity
     */
    ExitCommand (Application application) {
        this.application = application;
    }
    @Override
    public void execute(String argument) {
        application.setExit();
    }
}
