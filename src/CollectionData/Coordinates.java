package CollectionData;

/**
 * Класс получения координат
 */
public class Coordinates {
    private Double x; //Максимальное значение поля: 812, Поле не может быть null
    private Long y; //Максимальное значение поля: 572, Поле не может быть null

    public Coordinates(double x, long y){
        this.x=x;
        this.y=y;
    }

    public Double getX() {
        return x;
    }

    public Long getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + "\t" + y +"\t";
    }
}
