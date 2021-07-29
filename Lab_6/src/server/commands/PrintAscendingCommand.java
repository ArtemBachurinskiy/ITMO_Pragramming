package server.commands;

import client.movie.Movie;
import general.request.Request;
import general.response.Response;
import server.collection.CollectionManager;
import java.util.Comparator;

/**
 * Класс команды print_ascending.
 * Описание: вывести элементы коллекции в порядке возрастания
 */
public class PrintAscendingCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    PrintAscendingCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();
        message.append("Выводим элементы в порядке возрастания (по полю id): \n");
        collectionManager.getMoviesStream()
                .sorted(Comparator.comparingInt(Movie::getId))
                .map(movie -> movie.toString().concat("\n"))
                .forEachOrdered(message::append);
        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "print_ascending : вывести элементы коллекции в порядке возрастания";
    }
}
