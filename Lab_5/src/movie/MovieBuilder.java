package movie;

import java.io.IOException;

/**
 * The interface that is supposed to build a Movie entity
 */
public interface MovieBuilder {
    Coordinates buildCoordinates() throws IOException;
    Integer buildCoordinateX() throws IOException;
    Double buildCoordinateY();
    java.time.LocalDateTime buildCreationDate();
    int buildOscarsCount();
    long buildGoldenPalmCount();
    String buildTagline() throws IOException;
    MovieGenre buildGenre();
    /**
     * @param id the given id of the Movie
     * @param name the name of the Movie
     * @return a Movie entity
     * @throws IOException if an IOException occurs
     */
    Movie buildMovie(int id, String name) throws IOException;
}
