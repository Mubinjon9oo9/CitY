package Commands;

import CollectionData.City;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class SAVE{

    public SAVE(HashSet<City> cities, boolean check) {
        Sample s = new Sample();
        try {
            FileWriter writer = new FileWriter("/Users/mubinjon9009/IdeaProjects/City.server/src/Server/collection.xml");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n");
            int t = 0;
            for (City city1 : cities) {
                t++;
                writer.write("\t" + "<city" + t + ">\n");
                writer.write("\t\t<id>" + city1.getId() + "</id>\n");
                writer.write("\t\t<name>" + city1.getName() + "</name>\n");
                writer.write("\t\t<x>" + city1.getCoordinates().getX() + "</x>\n");
                writer.write("\t\t<y>" + city1.getCoordinates().getY() + "</y>\n");
                writer.write("\t\t<localdate>" + city1.getCreationDate() + "</localdate>\n");
                writer.write("\t\t<area>" + city1.getArea() + "</area>\n");
                writer.write("\t\t<population>" + city1.getPopulation() + "</population>\n");
                writer.write("\t\t<metersabovesealevel>" + city1.getMetersAboveSeaLevel() + "</metersabovesealevel>\n");
                writer.write("\t\t<timezone>" + city1.getTimezone() + "</timezone>\n");
                writer.write("\t\t<carcode>" + city1.getCarCode() + "</carcode>\n");
                writer.write("\t\t<government>" + city1.getGovernment() + "</government>\n");
                writer.write("\t\t<Governor>" + city1.getGovernor().getName() + "</Governor>\n");
                writer.write("\t</city" + t + ">\n");
            }
            writer.write("</root>");
            writer.close();
            s.Send_Out("Collection SAVED",check);
        }catch (IOException e){
            s.Send_Out("Error in writing to file!",check);
        }
    }
}
