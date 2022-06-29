package CollectionData;

import java.util.Comparator;
/**
 * Класс сортировки по ID
 */
public class SortByID implements Comparator<City> {

    @Override
    public int compare(City city1, City city2) {
        if (city1.getId()<city2.getId()){
            return -1;
        }
        if(city1.getId()==city2.getId()){
            return 0;
        }
        return 1;
    }
}
