package client.movie;

import java.io.IOException;

/**
 * Интерфейс, предназначенный для постройки объета типа Movie.
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
     * @param id поле id объекта Movie.
     * @param name поле name объекта Movie.
     * @return новый объект типа Movie
     */
    Movie buildMovie(int id, String name) throws IOException;
}
