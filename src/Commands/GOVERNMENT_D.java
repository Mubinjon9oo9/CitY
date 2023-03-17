package Commands;

import CollectionData.City;
import CollectionData.SortByName;
import Server.Connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class GOVERNMENT_D{
    public GOVERNMENT_D(boolean check) {
        HashSet<City> cities = new HashSet<>(new getData().cities);
        Sample s = new Sample();
        ArrayList<City> list = new ArrayList<>(cities);
        Collections.sort(list, new SortByName());
        Collections.reverse(list);
        s.Send_Out("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment", check);
        for (City city : list) {
            s.Send_Out(city.toString(),check);
        }
    }
}
