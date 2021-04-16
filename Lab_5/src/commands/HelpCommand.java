package commands;

import output.OutputManager;

/**
 * The HelpCommand class. The command displays help information about available commands.
 */
public class HelpCommand implements Command {
    private OutputManager outputManager;

    /**
     * @param outputManager the manager that outputs data
     */
    HelpCommand(OutputManager outputManager) {
        this.outputManager = outputManager;
    }
    @Override
    public void execute(String argument) {
        outputManager.printMessage("Справка по доступным командам!\n" +
                "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "insert null {element} : добавить новый элемент с заданным ключом\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_key null : удалить элемент из коллекции по его ключу\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "history : вывести последние 9 команд (без их аргументов)\n" +
                "max_by_golden_palm_count : вывести любой объект из коллекции, значение поля goldenPalmCount которого является максимальным\n" +
                "print_ascending : вывести элементы коллекции в порядке возрастания\n" +
                "print_field_ascending_golden_palm_count : вывести значения поля goldenPalmCount всех элементов в порядке возрастания");
    }
}
