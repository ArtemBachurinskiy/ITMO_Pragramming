package server.commands;

import client.movie.Movie;
import general.request.Request;
import general.response.Response;
import server.collection.CollectionManager;
import java.util.Comparator;

/**
 * Класс команды show.
 * Описание команды: вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    ShowCommand (CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder message = new StringBuilder();

        if (collectionManager.getCollectionSize() == 0)
            message.append("Коллекция пуста.");
        else {
            message.append("Элементы коллекции : \n");
            collectionManager.getMoviesStream()
                            .sorted(Comparator.comparing(Movie::getName))
                            .forEachOrdered(movie -> message.append(movie).append("\n"));
        }
        return new Response(request.getCommand(), message.toString());
    }

    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}