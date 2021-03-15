/**
 * This class contains fields Integer x and Double y
 * */
public class Coordinates {
    private Integer x; //Значение поля должно быть больше -268, Поле не может быть null
    private Double y; //Максимальное значение поля: 477, Поле не может быть null

    Coordinates (Integer x, Double y) {
        this.x = x;
        this.y = y;

    }
    Integer getX() {
        return this.x;
    }
    Double getY() {
        return this.y;
    }
}