import java.time.LocalDateTime;

/**
 * This class is made to create entities of Movie
 */
public class Movie implements Comparable<Movie> {

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int oscarsCount; //Значение поля должно быть больше 0
    private long goldenPalmCount; //Значение поля должно быть больше 0
    private String tagline; //Длина строки не должна быть больше 214, Поле может быть null
    private MovieGenre genre; //Поле не может быть null
    private Person operator; //Поле не может быть null

    Movie(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, int oscarsCount, long goldenPalmCount, String tagline, MovieGenre genre, Person operator) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.oscarsCount = oscarsCount;
        this.goldenPalmCount = goldenPalmCount;
        this.tagline = tagline;
        this.genre = genre;
        this.operator = operator;
    }

    void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }
    void setGoldenPalmCount(long goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }
    void setTagline(String tagline) {
        this.tagline = tagline;
    }
    void setGenre(MovieGenre genre) {
        this.genre = genre;
    }
    void setOperator(Person operator) {
        this.operator = operator;
    }

    Integer getId() {
        return id;
    }
    String getName() {
        return name;
    }
    Coordinates getCoordinates() {
        return coordinates;
    }
    LocalDateTime getCreationDate() {
        return creationDate;
    }
    int getOscarsCount() {
        return oscarsCount;
    }
    long getGoldenPalmCount() {
        return goldenPalmCount;
    }
    String getTagline() {
        return tagline;
    }
    MovieGenre getGenre() {
        return genre;
    }
    Person getOperator() {
        return operator;
    }

    @Override
    public String toString()
    {
        return "Movie [ id : " + id +
                ", name : " + name +
                ", coordinates " + "(x = " + coordinates.getX() + ", y = " + coordinates.getY() + ")" +
                ", oscarsCount : " + oscarsCount +
                ", goldenPalmCount : " + goldenPalmCount +
                ", tagline : " + tagline +
                ", genre : " + genre +
                ", operator : " + operator.toString() + " ]";
    }

    // сортировку по умолчанию по полю id
    @Override
    public int compareTo(Movie movie)
    {
        return (this.id - movie.getId());
    }
}

