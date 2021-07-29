package server.application;

import general.input.ConsoleInputManager;
import general.input.InputManager;
import general.output.ConsoleOutputManager;
import general.output.OutputManager;
import general.request.Request;
import general.application.Application;
import general.response.Response;
import server.collection.CollectionManager;
import server.commands.SaveCommand;
import server.commands.ServerCommandManager;
import server.commands.ServerInvoker;
import server.connect.ServerConnectionManager;
import server.file.FileManager;
import server.file.FilesWriter;
import server.file.XMLFilesWriter;
import server.request.ServerRequestReceiver;
import server.response.ServerResponseSender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс серверного приложения.
 */
public class ServerApplication implements Application {
    private InputManager inputManager;
    private ServerConnectionManager serverConnectionManager;
    private ServerRequestReceiver serverRequestReceiver;
    private ServerResponseSender serverResponseSender;
    private ServerCommandManager serverCommandManager;
    private FilesWriter filesWriter;
    private boolean shutdown;

    /**
     * Метод, который инициализирует нужные объекты и запускает главный цикл, реализуемый серверным приложением.
     * @param args аргументы командной строки, т.е. имя файла.
     */
    @Override
    public void start(String[] args) {
        shutdown = false;
        OutputManager outputManager = new ConsoleOutputManager();
        if (args.length == 0) {
            outputManager.printlnErrorMessage("Имя файла должно передаваться программе как аргумент командной строки.");
            System.exit(-1);
        }
        CollectionManager collectionManager = new CollectionManager();
        FileManager fileManager = new FileManager(args, outputManager, collectionManager);
        fileManager.manageFile();
        this.filesWriter = new XMLFilesWriter(collectionManager, fileManager, outputManager);
        this.inputManager = new ConsoleInputManager(new BufferedReader(new InputStreamReader(System.in)));
        this.serverConnectionManager = new ServerConnectionManager();
        this.serverRequestReceiver = new ServerRequestReceiver(serverConnectionManager);
        this.serverResponseSender = new ServerResponseSender(serverConnectionManager, outputManager);
        this.serverCommandManager = new ServerCommandManager(this, collectionManager, filesWriter);
        loop();
    }

    /**
     * Метод, реализующий главный цикл серверного приложения.
     */
    @Override
    public void loop() {
        while (!shutdown) {
            serverConnectionManager.acceptConnection();
            if (serverConnectionManager.connectionIsAccepted()) {
                Response response = serverCommandManager.manageRequestOfClient(serverRequestReceiver.receiveRequestFromClient());
                if (response != null)
                    serverResponseSender.sendResponseToClient(response);
            }
            try {
                if (inputManager.readyToRead()) {
                    Request request = new Request(inputManager.readLine().trim().toLowerCase());
                    serverCommandManager.manageRequestOfServer(request);
                }
            } catch (IOException ignored) {
            }
        }
        ServerInvoker invoker = new ServerInvoker(new SaveCommand(filesWriter));
        invoker.executeCommand(new Request("save"));
        serverConnectionManager.closeConnection();
    }

    public void setShutdown() {
        shutdown = true;
    }
}