package movie;

import java.io.IOException;

/**
 * The interface that is supposed to build a Person entity
 */
public interface PersonBuilder {
    String buildName() throws IOException;
    java.time.ZonedDateTime buildBirthday();
    Integer buildWeight();
    String buildPassportID() throws IOException;
    Color buildHairColor();
    /**
     * @return a Person entity
     * @throws IOException if an IOException occurs
     */
    Person buildPerson() throws IOException;
}
