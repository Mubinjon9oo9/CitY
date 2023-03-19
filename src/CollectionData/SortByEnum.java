package CollectionData;

import java.util.Comparator;
/**
 * Класс сортировки по ENUM
 */
public class SortByEnum implements Comparator<City> {

    @Override
    public int compare(City a, City b) throws NullPointerException{
        return a.getGovernment().compareTo(b.getGovernment());
    }
}
