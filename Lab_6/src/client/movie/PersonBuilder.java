package client.movie;

import java.io.IOException;

/**
 * Интерфейс, предназначенный для постройки объекта типа Person.
 */
public interface PersonBuilder {
    String buildName() throws IOException;
    java.time.ZonedDateTime buildBirthday();
    Integer buildWeight();
    String buildPassportID() throws IOException;
    Color buildHairColor();
    /**
     * @return новый объект типа Person
     */
    Person buildPerson() throws IOException;
}
