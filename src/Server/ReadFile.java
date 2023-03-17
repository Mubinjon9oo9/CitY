package Server;
import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Класс для чтения из файла и конвертирования байтов в текст
 */
public class ReadFile {
    Government government = null;

    /**
     * Чтение файла и конвертация в текст
     */
    /**public void readFile(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input PATH");
            String PathFile = scanner.nextLine();
            File file = new File(PathFile.strip());
            InputStream IS = new FileInputStream(file);
            BufferedInputStream BIS = new BufferedInputStream(IS);
            int code;
            char letter;
            String word = "";
            while ((code = BIS.read()) != -1) {
                if (code != 10 && code != 32) {
                    letter = (char) code;
                    word = word + letter;
                }
                if (code == 10) {
                    System.out.println("\n" + word);
                    InteractiveEditing.Main p1 = new Main();
                    p1.GetNCheck(word);
                    word = "";
                }
                if (code == 32) {
                    word = word + " ";
                }
            }
            BIS.close();
        }catch (IOException e){
            System.err.println("File not exist!\nCheck your file path");
            readFile();
        }
    }*/

    /**
     * Выбор enum GOVERNMENT исходя из входных данных
     * @param gov
     */
    public void chooseGov(String gov){
        switch (gov){
            case "DESPOTISM" -> government = Government.DESPOTISM;
            case "COMMUNISM" -> government = Government.COMMUNISM;
            case "PUPPET_STATE" -> government = Government.PUPPET_STATE;
            case "TOTALITARIANISM" -> government = Government.TOTALITARIANISM;
            case "JUNTA" -> government = Government.JUNTA;
            default -> government=Government.NULL;
        }
    }

    /**
     * Чтение файла коллекции и запись данных в коллекцию
     */
    public HashSet<City> ReadCollectionFile() {
        HashSet<City> hset = new HashSet<City>();
        int id = 0,population = 0,counts=0;
        String name = null,fame = null,gov = null;
        LocalDate creationDate=null;
        long area = 0,carCode = 0,y = 0;
        Float metersAboveSeaLevel = null;
        Double timezone = null,x = null;
        Document doc;
        try {
            File f1 = new File("/Users/mubinjon9009/IdeaProjects/City.server/src/Server/collection.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            doc= dbf.newDocumentBuilder().parse(f1);
            Node rootnode = doc.getFirstChild();
            NodeList nodeList = rootnode.getChildNodes();
            for (int i = 0; i<nodeList.getLength();i++){
                if (nodeList.item(i).getNodeType()!=Node.ELEMENT_NODE) {
                    continue;
                }
                NodeList children = nodeList.item(i).getChildNodes();
                for (int j = 0; j<children.getLength();j++){
                    if (children.item(j).getNodeType()!=Node.ELEMENT_NODE) {
                        continue;
                    }
                    switch (children.item(j).getNodeName()){
                        case "id" -> id = Integer.parseInt(children.item(j).getTextContent());
                        case "name" -> name = children.item(j).getTextContent();
                        case "x" -> x = Double.parseDouble(children.item(j).getTextContent());
                        case "y" -> y = Long.parseLong(children.item(j).getTextContent());
                        case "localdate" -> creationDate=LocalDate.parse(children.item(j).getTextContent());
                        case "area" -> area = Long.parseLong(children.item(j).getTextContent());
                        case "population" -> population = Integer.parseInt(children.item(j).getTextContent());
                        case "metersabovesealevel" -> metersAboveSeaLevel = Float.parseFloat(children.item(j).getTextContent());
                        case "timezone" -> timezone = Double.parseDouble(children.item(j).getTextContent());
                        case "carcode" -> carCode = Long.parseLong(children.item(j).getTextContent());
                        case "Governor" -> fame = children.item(j).getTextContent();
                        case "government"-> chooseGov(children.item(j).getTextContent());
                        default -> System.out.println("Unknown type: "+children.item(j).getNodeName());
                    }
                }
                for (City ci : hset ){
                    if (ci.getId()==id){
                        id+=10;
                        System.err.println("ID ERROR!. ID has been increased by 10");
                    }
                }
                Coordinates coordinates = new Coordinates(x,y);
                Human human = new Human(fame);
                try {
                    City city = new City(id,name,coordinates,creationDate,area,population,metersAboveSeaLevel,timezone,carCode,government,human);
                    hset.add(city);
                    counts++;
                }catch (OUTofLimitExceptions e){
                    System.err.println(e);
                }
            }
        } catch (Exception e) {
            System.err.println("PARSING ERROR! Check file of collections.");
            System.err.println("Fix errors and try again.");
        }
        System.out.println("Imported "+counts+ " collection");
        return hset;
    }
}
