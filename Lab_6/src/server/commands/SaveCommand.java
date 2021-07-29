package server.commands;

import general.request.Request;
import general.response.Response;
import server.file.FilesWriter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

/**
 * Класс команды save.
 * Описание команды: сохранить коллекцию в файл
 */
public class SaveCommand implements ServerCommand {
    private FilesWriter filesWriter;

    /**
     * @param filesWriter объект FilesWriter, предназначенный для записи информации в файл
     */
    public SaveCommand(FilesWriter filesWriter) {
        this.filesWriter = filesWriter;
    }
    @Override
    public Response execute (Request request) {
        String message = "";
        try {
            filesWriter.writeToFile();
        } catch (ParserConfigurationException | FileNotFoundException | TransformerException e) {
            message = "Не удалось сохранить коллекцию в файл";
        }

        return new Response(request.getCommand(), message);
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
