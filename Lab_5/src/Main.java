import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This is the Main class.
 * @author Artem Bachurinskiy
 * */
public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException, SAXException {

        // нет аргументов командной строки
        if (args.length == 0) {
            System.err.println("Имя файла должно передаваться программе как аргумент командной строки.");
            System.exit(1);
        }

        StringBuilder filename = new StringBuilder();
        for (int i = 0; i < args.length; i++)
        {
            filename.append(args[i]);
            if (filename.toString().contains(".xml"))
                break;
            if (i < args.length - 1)
                filename.append(" "); // восстанавливаем пробелы
        }
        if (!filename.toString().contains(".xml"))
            filename.append(".xml");
        MovieHashtable movieHashtable = new MovieHashtable(filename.toString());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String fullCommand;
        String command;
        String argument = "";
        ArrayList<String> commandHistory = new ArrayList<>();
        final int COMMANDS_TO_PRINT = 9;
        boolean hasArgument;
        ArrayList<BufferedReader> bufferedReaderArrayList = new ArrayList<>();
        ArrayList<Path> script_files = new ArrayList<>();
        int scriptLevel = 0;

        while (true) {
            fullCommand = bufferedReader.readLine();

            // reading Script file, in case EOF use "exit" scenario
            if (scriptLevel != 0 && (fullCommand == null || fullCommand.isEmpty())) {
                command = "exit";
                hasArgument = false;
            }
            else {
                if (fullCommand.contains(" ") && (!fullCommand.startsWith(" "))) {
                    command = fullCommand.substring(0, fullCommand.indexOf(" "));
                    argument = fullCommand.substring(fullCommand.indexOf(" ") + 1);
                    argument = argument.trim();
                    if (!argument.isEmpty()) //&& !argument.isBlank())
                        hasArgument = true;
                    else
                        hasArgument = false;
                }
                else {
                       command = fullCommand;
                       hasArgument = false;
                }
            }

            if (scriptLevel != 0) {
                System.out.println('\n' + command.toUpperCase());
            }

            switch (command) {
                case "help":
                    commandHistory.add(command);
                    movieHashtable.help();
                    break;
                case "info":
                    commandHistory.add(command);
                    movieHashtable.info();
                    break;
                case "show":
                    commandHistory.add(command);
                    movieHashtable.show();
                    break;
                case "insert":
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    commandHistory.add(command);
                    movieHashtable.insert(argument, bufferedReader);
                    break;
                case "update":
                    Integer id;
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    try {
                        id = Integer.parseInt(argument);
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Аргумент должен быть значением типа Integer!");
                        break;
                    }
                    commandHistory.add(command);
                    movieHashtable.update(id, bufferedReader);
                    break;
                case "remove_key":
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    commandHistory.add(command);
                    movieHashtable.remove_key(argument);
                    break;
                case "clear":
                    commandHistory.add(command);
                    movieHashtable.clear();
                    break;
                case "save":
                    commandHistory.add(command);
                    movieHashtable.save();
                    break;
                case "execute_script":
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    commandHistory.add(command);

                    File file = new File(argument);
                    if (!file.exists()) {
                        System.err.println("Файла скрипта с таким именем не существует. ");
                    }
                    else if (!file.canRead()) {
                        System.err.println("Отсутствуют права доступа на чтение файла скрипта.");
                    }
                    else {
                        int i;
                        for (i = 0; i < script_files.size(); i++) {
                            Path p = script_files.get(i);
                            if (p.toString().equals(file.toPath().toString()))
                                break;
                        }
                        if (i < bufferedReaderArrayList.size()) {
                            System.err.println("execute_script не может вызывать ранее использованный script файл! Игнорируем данную команду execute_script...");
                        }
                        else {
                            bufferedReaderArrayList.add(scriptLevel, bufferedReader);
                            script_files.add(scriptLevel, file.toPath());
                            scriptLevel++;
                            bufferedReader = new BufferedReader(new FileReader(file));
                        }
                    }
                    break;
                case "exit":
                    if (scriptLevel == 0)
                    {
                        bufferedReader.close();
                        System.exit(0);
                    }
                    else
                    {
                        commandHistory.add(command);
                        bufferedReader.close();

                        scriptLevel --;
                        bufferedReader = bufferedReaderArrayList.get(scriptLevel);
                        bufferedReaderArrayList.remove(scriptLevel);
                        script_files.remove(scriptLevel);
                    }
                    break;
                case "remove_greater":
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    commandHistory.add(command);
                    movieHashtable.remove_greater(argument);
                    break;
                case "remove_lower":
                    if (!hasArgument) {
                        System.err.println("Необходимо задать аргумент!");
                        break;
                    }
                    commandHistory.add(command);
                    movieHashtable.remove_lower(argument);
                    break;
                case "history":
                    int beginIndex;
                    if (commandHistory.size() <= COMMANDS_TO_PRINT)
                        beginIndex = 0;
                    else beginIndex = commandHistory.size() - COMMANDS_TO_PRINT;

                    System.out.println("Последние " + COMMANDS_TO_PRINT + " команд (по порядку ввода): ");
                    for (int i = beginIndex; i < commandHistory.size(); i++) {
                        System.out.println(commandHistory.get(i));
                        if (i != commandHistory.size() - 1)
                            System.out.println('\u2193');
                    }
                    commandHistory.add(command);
                    break;
                case "max_by_golden_palm_count":
                    commandHistory.add(command);
                    movieHashtable.max_by_golden_palm_count();
                    break;
                case "print_ascending":
                    commandHistory.add(command);
                    movieHashtable.print_ascending();
                    break;
                case "print_field_ascending_golden_palm_count":
                    commandHistory.add(command);
                    movieHashtable.print_field_ascending_golden_palm_count();
                    break;
                case "":
                    break;
                default:
                    command = command.trim();
                    if (command.isEmpty())
                        break;
                    else if (command.startsWith(" "))
                        System.err.println("Команда не может начинаться с пробела!");
                    else
                        System.err.println("Неопознанная команда. Наберите help для справки.");
            }
        }
    }
}
