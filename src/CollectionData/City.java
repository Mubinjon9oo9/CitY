package CollectionData;
import InteractiveEditing.OUTofLimitExceptions;

import java.time.LocalDate;

/**
 * Класс коллекции
 */
public class City {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Float metersAboveSeaLevel;
    private Double timezone; //Значение поля должно быть больше -13, Максимальное значение поля: 15
    private Long carCode; //Значение поля должно быть больше 0, Максимальное значение поля: 1000, Поле не может быть null
    private Government government; //Поле может быть null
    private Human governor; //Поле не может быть null

    public City(int id, String name, Coordinates coordinates, LocalDate creationDate, long area, Integer population, Float metersAboveSeaLevel, Double timezone, long carCode, Government government, Human governor) throws OUTofLimitExceptions {
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate= creationDate;
        this.area=area;
        this.population=population;
        this.metersAboveSeaLevel=metersAboveSeaLevel;
        this.timezone=timezone;
        this.carCode=carCode;
        this.government=government;
        this.governor=governor;
        CheckLimits();
    }
    public City(int id, String name, Coordinates coordinates, long area, Integer population, Float metersAboveSeaLevel, Double timezone, long carCode, Government government, Human governor) throws OUTofLimitExceptions {
        this.id=id;
        this.name=name;
        this.coordinates=coordinates;
        this.creationDate= LocalDate.now();
        this.area=area;
        this.population=population;
        this.metersAboveSeaLevel=metersAboveSeaLevel;
        this.timezone=timezone;
        this.carCode=carCode;
        this.government=government;
        this.governor=governor;
        CheckLimits();
    }
    private void CheckLimits() throws OUTofLimitExceptions {
        /**
         * Метод для проверки на максимум и минимум вводимых в коллекцию значений
         */
        if (this.id <= 0) {
            throw new OUTofLimitExceptions("ID - Out of limit");
        }
        if (this.name == null || this.name.equals("")) {
            throw new OUTofLimitExceptions("Name - Out of limit");
        }
        if (this.coordinates.getX() == null || this.coordinates.getX() > 812) {
            throw new OUTofLimitExceptions("X - Out of limit");
        }
        if (this.coordinates.getY() == null || this.coordinates.getY() > 572) {
            throw new OUTofLimitExceptions("Y - Out of limit");
        }
        if (this.creationDate == null) {
            throw new OUTofLimitExceptions("CreationDate - Out of limit");
        }
        if (this.area <= 0) {
            throw new OUTofLimitExceptions("Area - Out of limit");
        }
        if (this.population <= 0) {
            throw new OUTofLimitExceptions("Population - Out of limit");
        }
        if (this.timezone < -13 || this.timezone > 15) {
            throw new OUTofLimitExceptions("TimeZone - Out of limit");
        }
        if (this.carCode == null || this.carCode <= 0 || this.carCode > 1000) {
            throw new OUTofLimitExceptions("CarCode - Out of limit");
        }
        if (this.governor.getName() == null || this.governor.getName().equals("")) {
            throw new OUTofLimitExceptions("HumanName - Out of limit");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Float getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public Double getTimezone() {
        return timezone;
    }

    public Long getCarCode() {
        return carCode;
    }

    public Government getGovernment() {
        return government;
    }

    public Human getGovernor() {
        return governor;
    }

    public Coordinates getCoordinates() {

        return coordinates;
    }

    @Override
    public String toString() {
        /**
         * Метод для регулировки отображения коллекции при выводе.
         */
        String timez="\t\t\t\t\t";
        String governm="\t\t";
        String car="\t\t\t";
        String are="\t\t";
        String pop="\t\t";
        if (population<100){
            pop = "\t\t\t";
        }
        if (area<10){
            are = "\t\t\t";
        }
        if (metersAboveSeaLevel<10 && metersAboveSeaLevel>=0){
            timez="\t\t\t\t\t\t";
        }
        if (timezone>=10 || timezone <0) {
            car="\t\t";
        }
        if (government==Government.NULL){
            governm="\t";
        }
        return id + "\t\t" + name + "\t\t" + coordinates + "\t\t"+ creationDate + "\t\t" +
                area + are + population + pop + metersAboveSeaLevel + timez + timezone + car +
                carCode+ "\t\t\t" + governor  + governm  + government;
    }

}

