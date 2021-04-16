package input;

import movie.MovieGenre;
import java.io.IOException;

/**
 * The basic interface for reading Movie entities.
 */
public interface MovieReader {
    /**
     * @return coordinate x
     * @throws IOException if an IOException occurs
     */
    Integer readCoordinateX() throws IOException;

    /**
     * @return coordinate y
     */
    Double readCoordinateY();

    /**
     * @return oscarsCount
     */
    int readOscarsCount();

    /**
     * @return goldenPalmCount
     */
    long readGoldenPalmCount();

    /**
     * @return tagline
     * @throws IOException if an IOException occurs
     */
    String readTagline() throws IOException;

    /**
     * @return genre
     */
    MovieGenre readGenre();
}
