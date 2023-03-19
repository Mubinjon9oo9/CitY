package Commands;

import CollectionData.City;
import CollectionData.SortByEnum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MIN_BY_GOVERNMENT{

    public MIN_BY_GOVERNMENT(boolean check) {
        HashSet<City> cities = new HashSet<>(new getData().cities);
        Sample s = new Sample();
        ArrayList<City> list1 = new ArrayList<>(cities);
        Collections.sort(list1, new SortByEnum());
        s.Send_Out("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment",check);
        for (City city : list1) {
            s.Send_Out(city.toString(),check);
        }
    }
}
