package server.commands;

import general.request.Request;
import general.response.Response;
import server.collection.CollectionManager;

/**
 * Класс команды remove_key.
 * Описание команды: удалить элемент из коллекции по его ключу
 */
public class RemoveKeyCommand implements ServerCommand {
    private CollectionManager collectionManager;

    /**
     * @param collectionManager менеджер коллекции
     */
    RemoveKeyCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        String message;
        if (!request.getArgument().isEmpty()) {
            if (collectionManager.remove_key(request.getArgument()))
                message = "Элемент успешно удалён!";
            else
                message = "В коллекции нет элемента с заданным ключом!";
        }
        else
            message = "Необходимо задать аргумент!";

        return new Response(request.getCommand(), message);
    }

    public String getDescription() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
    }
}
