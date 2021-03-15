import java.time.ZonedDateTime;

/**
 * This class is made to create entities of Person
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.ZonedDateTime birthday; //Поле не может быть null
    private Integer weight; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Длина строки не должна быть больше 30, Поле не может быть null
    private Color hairColor; //Поле не может быть null

    Person(String name, ZonedDateTime birthday, Integer weight, String passportID, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    void setName(String name) {
        this.name = name;
    }
    void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }
    void setWeight(Integer weight) {
        this.weight = weight;
    }
    void setPassportID(String passportID) {
        this.passportID = passportID;
    }
    void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public String getName() {
        return name;
    }
    public ZonedDateTime getBirthday() {
        return birthday;
    }
    public Integer getWeight() {
        return weight;
    }
    public String getPassportID() {
        return passportID;
    }
    public Color getHairColor() {
        return hairColor;
    }

    @Override
    public String toString() {
        return "Person [ name : " + name +
               ", weight : " + weight +
               ", passportID : " + passportID +
               ", hairColor : " + hairColor + " ]";
    }
}