package general.request;

import client.movie.Movie;
import java.io.Serializable;

/**
 * Класс, который представляет из себя запрос, отправляемый серверу.
 */
public class Request implements Serializable {
    private String command;
    private String argument;
    private Movie movie;

    public Request(String command, String argument, Movie movie) {
        this.command = command;
        this.argument = argument;
        this.movie = movie;
    }
    public Request(String command) {
        this.command = command;
        this.argument = null;
        this.movie = null;
    }

    public String getCommand() {
        return command;
    }
    public String getArgument() {
        return argument;
    }
    public Movie generateMovie (int id) {
        movie.setId(id);
        return movie;
    }
    public Movie getMovie() {
        return movie;
    }
}