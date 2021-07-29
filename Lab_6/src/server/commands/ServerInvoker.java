package server.commands;

import general.request.Request;
import general.response.Response;

public class ServerInvoker {
    private ServerCommand command;

    public ServerInvoker(ServerCommand command) {
        this.command = command;
    }

    public Response executeCommand(Request request) {
        return command.execute(request);
    }
}
