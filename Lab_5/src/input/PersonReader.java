package input;

import movie.Color;
import java.io.IOException;

/**
 * The basic interface for reading Person entities.
 */
public interface PersonReader {
    /**
     * @return name
     * @throws IOException if an IOException occurs
     */
    String readPersonName() throws IOException;
    /**
     * @return birthday
     */
    java.time.ZonedDateTime readBirthday();
    /**
     * @return weight
     */
    Integer readWeight();
    /**
     * @return passportID
     * @throws IOException if an IOException occurs
     */
    String readPassportID() throws IOException;
    /**
     * @return color
     */
    Color readHairColor();
}
