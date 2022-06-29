package CollectionData;

import java.util.Comparator;
/**
 * Класс сортировки по имени
 */
public class SortByName implements Comparator<City> {

    @Override
    public int compare(City city1, City city2) {
        return city1.getName().compareTo(city2.getName());
    }
}
