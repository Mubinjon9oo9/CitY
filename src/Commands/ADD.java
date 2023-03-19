package Commands;

import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Класс для выполнения команды ADD
 */
public class ADD{

    public ADD(boolean check) {
        Sample s = new Sample();
        getData getData = new getData();
        HashSet<City> cities = new HashSet<City>(getData.cities);
        s.GenerateID(cities);
        String fame = s.getMyString("Name of country/city");
        double x = s.getMyDouble("Coordinates X","X");
        long y = s.getMyLong("Coordinates Y","Y");
        long area = s.getMyLong("Area","Area");
        int population = s.getMyInt("Population","Population",check);
        float metersAboveSeaLevel = s.getMyFloat("metersAboveSeaLevel");
        double timezone = s.getMyDouble("Timezone","Timezone");
        long carCode = s.getMyLong("carCode","carCode");
        Government government = switch (s.getMyGovernment("Government")) {
            case "DESPOTISM" -> Government.DESPOTISM;
            case "COMMUNISM" -> Government.COMMUNISM;
            case "PUPPET_STATE" -> Government.PUPPET_STATE;
            case "TOTALITARIANISM" -> Government.TOTALITARIANISM;
            case "JUNTA" -> Government.JUNTA;
            default -> Government.NULL;
        };
        String HumanName = s.getMyString("Governor");
        Coordinates coordinates = new Coordinates(x, y);
        Human human = new Human(HumanName);
        try {
            City city0 = new City(s.generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
            setData setData = new setData();
            setData.del(s.generated_ID, fame, coordinates.getX(),coordinates.getY(), LocalDate.now(), area, population, metersAboveSeaLevel, timezone, carCode, government, human.getName());
            cities.add(city0);
            s.Send_Out("Done",check);
        }catch (OUTofLimitExceptions e){
            String ex = e.toString();
            s.Send_Out(ex,check);
        }
    }
}
