package Commands;

import CollectionData.City;
import CollectionData.SortByID;
import CollectionData.SortByName;
import java.util.ArrayList;
import java.util.Collections;

public class SHOW{

    public SHOW(boolean check) {
        Sample s = new Sample();
        getData g = new getData();
        ArrayList<City> list = new ArrayList<>(g.cities);
        list.sort(new SortByID());
        s.Send_Out("id\t\tname\t\tcoordinates x&y\t\tcreationDate\tarea\t\tpopulation\tmetersAboveSeaLevel\t\ttimezone\tcarCode\t\tgovernor\t\tgovernment|W|", check);
        for (City city : list) {
            s.Send_Out(city.toString()+"|W|",check);
        }
        s.Send_Out("===================================", check);
    }
}
