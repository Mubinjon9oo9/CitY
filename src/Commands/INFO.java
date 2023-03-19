package Commands;

import CollectionData.City;
import java.util.HashSet;

public class INFO{

    public INFO(boolean check) {
        HashSet<City> cities = new HashSet<>(new getData().cities);
        Sample s = new Sample();
        int p = cities.size();
        s.Send_Out("Count of elements in collection: " + p+"\n" +
                "Collection data and types:\n" +
                "ID\t\tName\tCoordinates X & Y\t\tCreationDate\tArea\tPopulation\t\tMetersAboveSeaLevel\t\tTimezone\tCarCode\t\tGovernment\tGovernor\n" +
                "int\t\tString\tdouble & long\t\t\tnull\t\t\tlong\tint\t\t\t\tfloat\t\t\t\t\tdouble\t\tlong\t\tenum\t\tString\n" +
                """
                        Enums (Government):
                            DESPOTISM,
                            COMMUNISM,
                            PUPPET_STATE,
                            TOTALITARIANISM,
                            JUNTA
                            null""",check);
    }
}
