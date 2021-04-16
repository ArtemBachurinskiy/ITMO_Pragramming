package movie;

/**
 * This class contains fields Integer x and Double y
 * */
public class Coordinates {
    private Integer x; //Значение поля должно быть больше -268, Поле не может быть null
    private Double y; //Максимальное значение поля: 477, Поле не может быть null

    public Coordinates(Integer x, Double y) {
        this.x = x;
        this.y = y;

    }
    public Integer getX() {
        return this.x;
    }
    public Double getY() {
        return this.y;
    }
}