package server.commands;

import general.request.Request;
import general.response.Response;

public interface ServerCommand {
    Response execute(Request request);
    String getDescription();
}
