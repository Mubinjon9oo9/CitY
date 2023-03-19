package Commands;

import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;
import java.util.HashSet;

public class ADD_IF_MAX{

    public ADD_IF_MAX(boolean check) {
        Sample s = new Sample();
        getData g = new getData();
        HashSet<City> cities = new HashSet<>(g.cities);
        String tfame = s.getMyString("Name of country/city");
        double tx = s.getMyDouble("Coordinates X","X");
        long ty = s.getMyLong("Coordinates Y","Y");
        long tarea = s.getMyLong("Area","Area");
        int tpopulation = s.getMyInt("Population","Population",check);
        float tmetersAboveSeaLevel = s.getMyFloat("metersAboveSeaLevel");
        double ttimezone = s.getMyDouble("Timezone","Timezone");
        long tcarCode = s.getMyLong("carCode","carCode");
        Government tgovernment = switch (s.getMyGovernment("Government")) {
            case "DESPOTISM" -> Government.DESPOTISM;
            case "COMMUNISM" -> Government.COMMUNISM;
            case "PUPPET_STATE" -> Government.PUPPET_STATE;
            case "TOTALITARIANISM" -> Government.TOTALITARIANISM;
            case "JUNTA" -> Government.JUNTA;
            default -> Government.NULL;
        };
        String tHumanName = s.getMyString("Governor");
        s.GenerateID(cities);
        for (City city : cities) {
            if (city.getId() >= s.MaxID) {
                s.MaxID = city.getId();
            }
            if (city.getArea() >= s.MaxArea) {
                s.MaxArea = city.getArea();
            }
            if (city.getPopulation() >= s.MaxPopulation) {
                s.MaxPopulation = city.getPopulation();
            }
            if (city.getMetersAboveSeaLevel() >= s.MaxMeters) {
                s.MaxMeters = city.getMetersAboveSeaLevel();
            }
            if (city.getTimezone() >= s.MaxTimezone) {
                s.MaxTimezone = city.getTimezone();
            }
            if (city.getCarCode() >= s.MaxCarCode) {
                s.MaxCarCode = city.getCarCode();
            }
        }
        if (tarea > s.MaxArea || tpopulation > s.MaxPopulation || tmetersAboveSeaLevel > s.MaxMeters || ttimezone > s.MaxTimezone || tcarCode > s.MaxCarCode) {
            Coordinates coordinates = new Coordinates(tx, ty);
            Human thuman = new Human(tHumanName);
            try {
                City city = new City(s.generated_ID, tfame, coordinates, tarea, tpopulation, tmetersAboveSeaLevel, ttimezone, tcarCode, tgovernment, thuman);
                //s.cities.add(city);
            }catch (OUTofLimitExceptions e){
                String es = e.toString();
                s.Send_Out(es,check);
            }
            s.Send_Out("ADDED",check);
        }
    }
}
