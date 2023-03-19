package Commands;

import CollectionData.City;
import java.util.HashSet;

public class REMOVE_GREATER{

    public REMOVE_GREATER(HashSet<City> cities, boolean check) {
        Sample s = new Sample();
        s.s_area = s.getMyLong("Area","Area");
        s.s_population = s.getMyInt("Population","Population",check);
        s.s_metersAboveSeaLevel = s.getMyFloat("metersAboveSeaLevel");
        s.s_timezone = s.getMyDouble("Timezone","Timezone");
        s.s_carCode = s.getMyLong("carCode","carCode");
        s.remove_gr(cities,check);
    }
}
