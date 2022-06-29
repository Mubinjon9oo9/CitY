package InteractiveEditing;

import CollectionData.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
/**
 * Главный класс
 */
public class Main {

    public static int generated_ID, MaxID = 0, MaxPopulation = 0, s_population;
    public static long s_area, s_carCode, MaxArea = 0, MaxCarCode = 0;
    public static float s_metersAboveSeaLevel, MaxMeters = 0;
    public static double s_timezone, MaxTimezone = 0;
    public static HashSet<City> cities = new HashSet<>();
    public static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        newCommand p1 = new newCommand();
        TreatmentTXT p2 = new TreatmentTXT();
        ReadFile p3 = new ReadFile();
        cities = p3.ReadCollectionFile();
        System.out.println("Формат ввода элементов({element})\ncommand {String, double, long, long, int, float, double, long, government, Human}");
        do {
            String [] comd = p1.getCommand().split(" ");
            history.add(comd[0]);
            if (p1.command.contains("execute_script")) {
                p3.readFile();
            }
            else if (p2.com_HELP(p1.command)){
                System.out.println("""
                        help : вывести справку по доступным командам
                        info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                        show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                        add {element} : добавить новый элемент в коллекцию
                        update id {element} : обновить значение элемента коллекции, id которого равен заданному
                        remove_by_id id : удалить элемент из коллекции по его id
                        clear : очистить коллекцию
                        save : сохранить коллекцию в файл
                        execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                        exit : завершить программу (без сохранения в файл)
                        add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                        remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                        history : вывести последние 7 команд (без их аргументов)
                        min_by_government : вывести любой объект из коллекции, значение поля government которого является минимальным
                        print_field_ascending_governor : вывести значения поля governor всех элементов в порядке возрастания
                        print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания""");
            }
            else if (p2.com_HISTORY(p1.command)) {
                if (history.size() > 7) {
                    for (int i = 8; i >= 2; i--) {
                        System.out.println(history.get(history.size() - i));
                    }
                } else {
                    System.out.println("Команд меньше 7");
                }
            }
            else if (p2.com_INFO(p1.command)){
                int p = cities.size();
                System.out.println("Элементов в коллеции: " + p);
                System.out.println("Данные коллекции и их типы:");
                System.out.println("ID\t\tName\tCoordinates X & Y\t\tCreationDate\tArea\tPopulation\t\tMetersAboveSeaLevel\t\tTimezone\tCarCode\t\tGovernment\tGovernor");
                System.out.println("int\t\tString\tdouble & long\t\t\tnull\t\t\tlong\tint\t\t\t\tfloat\t\t\t\t\tdouble\t\tlong\t\tenum\t\tString");
                System.out.println("""
                        Типы принимаемых данных enum(Government):
                            DESPOTISM,
                            COMMUNISM,
                            PUPPET_STATE,
                            TOTALITARIANISM,
                            JUNTA
                            null""");
            }
            else if (p2.com_ADD(p1.command)) {
                GenerateID(cities);
                String fame = getMyString("Название (Name of country/city)");
                double x = getMyDouble("Coordinates X");
                long y = getMyLong("Coordinates Y");
                long area = getMyLong("Площадь (Area)");
                int population = getMyInt("Население (Population)");
                float metersAboveSeaLevel = getMyFloat("Высота над уровнем моря (metersAboveSeaLevel)");
                double timezone = getMyDouble("Временная зона (Timezone)");
                long carCode = getMyLong("Код машин (carCode)");
                Government government = switch (getMyGovernment("Тип правления (Government)")) {
                    case "DESPOTISM" -> Government.DESPOTISM;
                    case "COMMUNISM" -> Government.COMMUNISM;
                    case "PUPPET_STATE" -> Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> Government.TOTALITARIANISM;
                    case "JUNTA" -> Government.JUNTA;
                    default -> Government.NULL;
                };
                String HumanName = getMyString("Правитель (Governor)");
                Coordinates coordinates = new Coordinates(x, y);
                Human human = new Human(HumanName);
                try {
                    City city0 = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                    cities.add(city0);
                }catch (OUTofLimitExceptions e){
                    System.err.println(e);
                }
            }
            else if (p2.com_SHOW(p1.command)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                Collections.sort(list, new SortByID());
                System.out.println("id\t\tname\t\tcoordinates x&y\t\tcreationDate\tarea\t\tpopulation\tmetersAboveSeaLevel\t\ttimezone\tcarCode\t\tgovernor\t\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
            else if (p2.com_SAVE(p1.command)) {
                try {
                    FileWriter writer = new FileWriter("/Users/mubinjon9009/IdeaProjects/City/src/InteractiveEditing/collection.xml");
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
                    System.out.println("Коллекция сохранена в файл");
                }catch (IOException e){
                    System.err.println("Сохранение не удалось");
                }
            }
            else if (p2.com_CLEAR(p1.command)) {
                cities.clear();
                System.out.println("Коллекция очищена");
            }
            else if (p2.com_REMOVE_BY_ID(p1.command)) {
                String[] com = p1.command.split(" ");
                int Rid = Integer.parseInt(com[1]);
                cities = remove_cl(cities,Rid);
            }
            else if (p2.com_UPDATE(p1.command)) {
            String[] com = p1.command.split(" ");
            int Rid = Integer.parseInt(com[1]);
                String fame = getMyString("Название (Name of country/city)");
                double x = getMyDouble("Coordinates X");
                long y = getMyLong("Coordinates Y");
                long area = getMyLong("Площадь (Area)");
                int population = getMyInt("Население (Population)");
                float metersAboveSeaLevel = getMyFloat("Высота над уровнем моря (metersAboveSeaLevel)");
                double timezone = getMyDouble("Временная зона (Timezone)");
                long carCode = getMyLong("Код машин (carCode)");
                Government government = switch (getMyGovernment("Тип правления (Government)")) {
                    case "DESPOTISM" -> Government.DESPOTISM;
                    case "COMMUNISM" -> Government.COMMUNISM;
                    case "PUPPET_STATE" -> Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> Government.TOTALITARIANISM;
                    case "JUNTA" -> Government.JUNTA;
                    default -> Government.NULL;
                };
                String HumanName = getMyString("Правитель (Governor)");
                cities =remove_cl(cities,Rid);
                Coordinates coordinates = new Coordinates(x, y);
                Human human = new Human(HumanName);
                try {
                    City city0 = new City(Rid, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                    cities.add(city0);
                }catch (OUTofLimitExceptions e){
                    System.err.println(e);
                }
                System.out.println("UPDATED!");
            }
            else if (p2.com_GOVERNMENT_I(p1.command)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
            else if (p2.com_GOVERNMENT_D(p1.command)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                Collections.reverse(list);
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
            else if (p2.com_ADD_IF_MAX(p1.command)) {
                String[] words = p1.command.split(" ");
                String fame = words[1].replace(",", "").replace("{","");
                double x = Double.parseDouble(words[2].replace(",", ""));
                long y = Long.parseLong(words[3].replace(",", ""));
                long area = Long.parseLong(words[4].replace(",", ""));
                int population = Integer.parseInt(words[5].replace(",", ""));
                float metersAboveSeaLevel = Float.parseFloat(words[6].replace(",", ""));
                double timezone = Double.parseDouble(words[7].replace(",", ""));
                long carCode = Long.parseLong(words[8].replace(",", ""));
                Government government = switch (words[9].replace(",", "")) {
                    case "DESPOTISM" -> Government.DESPOTISM;
                    case "COMMUNISM" -> Government.COMMUNISM;
                    case "PUPPET_STATE" -> Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> Government.TOTALITARIANISM;
                    case "JUNTA" -> Government.JUNTA;
                    default -> Government.NULL;
                };
                String HumanName = words[10].replace("}", "");
                GenerateID(cities);
                for (City city : cities) {
                    if (city.getId() >= MaxID) {
                        MaxID = city.getId();
                    }
                    if (city.getArea() >= MaxArea) {
                        MaxArea = city.getArea();
                    }
                    if (city.getPopulation() >= MaxPopulation) {
                        MaxPopulation = city.getPopulation();
                    }
                    if (city.getMetersAboveSeaLevel() >= MaxMeters) {
                        MaxMeters = city.getMetersAboveSeaLevel();
                    }
                    if (city.getTimezone() >= MaxTimezone) {
                        MaxTimezone = city.getTimezone();
                    }
                    if (city.getCarCode() >= MaxCarCode) {
                        MaxCarCode = city.getCarCode();
                    }
                }
                if (area > MaxArea) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);

                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (population > MaxPopulation) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (metersAboveSeaLevel > MaxMeters) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (timezone > MaxTimezone) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (carCode > MaxCarCode) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {

                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                }
            }
            else if (p2.com_REMOVE_GREATER(p1.command)) {
                String[] words = p1.command.split(" ");
                s_area = Long.parseLong(words[4].replace(",", ""));
                s_population = Integer.parseInt(words[5].replace(",", ""));
                s_metersAboveSeaLevel = Float.parseFloat(words[6].replace(",", ""));
                s_timezone = Double.parseDouble(words[7].replace(",", ""));
                s_carCode = Long.parseLong(words[8].replace(",", ""));
                cities = remove_gr(cities);
            }
            else if (p2.com_MIN_BY_GOVERNMENT(p1.command)){
                ArrayList<City> list1 = new ArrayList<>(cities);
                Collections.sort(list1, new SortByEnum());
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list1) {
                    System.out.println(city);
                }
            }
            else {
                System.out.println("Команда \""+comd[0] + "\" не найдена!\nИсправьте ошибки и введите заново\nДля просмотра перечня команд введите \"help\"");
            }
        } while (p2.Exit(p1.command));
    }
    /**
     * @param set Наша коллекция
     * @param rid Индекс элемента который нужно удалить в коллекции
     * @return set Возращает уже измененную коллекцию
     * Этот метод принимает на вход коллекцию и удаляет указанный элемент, затем обратно возвращает. Элемент указывается с помощью ID.
     */
    public static HashSet<City> remove_cl(HashSet<City> set, int rid){
    boolean bl = true;
    Iterator<City> iterator = set.iterator();
    while (iterator.hasNext()){
        City city = iterator.next();
        if (city.getId()==rid){
            iterator.remove();
            System.out.println("Элемент с таким ID удален из коллекции!");
            bl = false;
        }
    }
    if(bl){
            System.err.println("В коллекции нет элемента с таким ID!");
        }
    return set;
}
    /**
     * @param set Наша коллекция
     * @return set Измененная коллекция
     * Этот метод получает коллекцию, сравнивает ее данные элемента с указаными в комманде. Если хоть один параметр больше указанны, то этот элемент будет удален.
     */
    public static HashSet<City> remove_gr(HashSet<City> set) {
        Iterator<City> iterator = set.iterator();
        int kl = 0;
        while (iterator.hasNext()) {
            City city = iterator.next();
            if (city.getArea() > s_area) {
                iterator.remove();
                kl++;
            } else if (city.getPopulation() > s_population) {
                iterator.remove();
                kl++;
            } else if (city.getMetersAboveSeaLevel() > s_metersAboveSeaLevel) {
                iterator.remove();
                kl++;
            } else if (city.getTimezone() > s_timezone) {
                iterator.remove();
                kl++;
            } else if (city.getCarCode() > s_carCode) {
                iterator.remove();
                kl++;
            }

        }
        System.out.println("Элементов удалено:" + kl);
        return set;
    }
    /**
     * @param set Наша коллекция
     * Этот метод нужен для генерации уникального ID
     */
    public static void GenerateID(HashSet<City> set){

        for (City city : set) {
            if (city.getId()>generated_ID){
                generated_ID=city.getId();
            }
        }
        generated_ID+=1;
    }
    /**
     * @param commands Команда для выполнения
     * Этот метод нужен для выполнения команды из считанного файла
     */
    public void GetNCheck(String commands){
        TreatmentTXT p2 = new TreatmentTXT();
        commands = commands.replace(",","").replace("{","").replace("}","");
        String [] comd = commands.split(" ");
        history.add(comd[0]);
        if (p2.com_HELP(commands)){
                System.out.println("""
                        help : вывести справку по доступным командам
                        info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                        show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                        add {element} : добавить новый элемент в коллекцию
                        update id {element} : обновить значение элемента коллекции, id которого равен заданному
                        remove_by_id id : удалить элемент из коллекции по его id
                        clear : очистить коллекцию
                        save : сохранить коллекцию в файл
                        execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                        exit : завершить программу (без сохранения в файл)
                        add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
                        remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                        history : вывести последние 7 команд (без их аргументов)
                        min_by_government : вывести любой объект из коллекции, значение поля government которого является минимальным
                        print_field_ascending_governor : вывести значения поля governor всех элементов в порядке возрастания
                        print_field_descending_governor : вывести значения поля governor всех элементов в порядке убывания""");
            }
        else if (p2.com_INFO(commands)){
                int p = cities.size();
                System.out.println("Элементов в коллеции: " + p);
                System.out.println("Данные коллекции и их типы:");
                System.out.println("ID\t\tName\tCoordinates X & Y\t\tCreationDate\tArea\tPopulation\t\tMetersAboveSeaLevel\t\tTimezone\tCarCode\t\tGovernment\tGovernor");
                System.out.println("int\t\tString\tdouble & long\t\t\tnull\t\t\tlong\tint\t\t\t\tfloat\t\t\t\t\tdouble\t\tlong\t\tenum\t\tString");
                System.out.println("""
                        Типы принимаемых данных enum(Government):
                            DESPOTISM,
                            COMMUNISM,
                            PUPPET_STATE,
                            TOTALITARIANISM,
                            JUNTA
                            null""");
            }
        else if (p2.com_HISTORY(commands)) {
            if (history.size() > 7) {
                for (int i = 8; i >= 2; i--) {
                    System.out.println(history.get(history.size() - i));
                }
            } else {
                System.out.println("Команд меньше 7");
            }
        }
        else if (p2.com_ADD(commands)) {
            GenerateID(cities);
            try {
                String fame = comd[1];
                double x = Double.parseDouble(comd[2]);
                long y = Long.parseLong(comd[3]);
                long area = Long.parseLong(comd[4]);
                int population = Integer.parseInt(comd[5]);
                float metersAboveSeaLevel = Float.parseFloat(comd[6]);
                double timezone = Double.parseDouble(comd[7]);
                long carCode = Long.parseLong(comd[8]);
                Government government = null;
                switch (comd[9]) {
                    case "DESPOTISM" -> government = Government.DESPOTISM;
                    case "COMMUNISM" -> government = Government.COMMUNISM;
                    case "PUPPET_STATE" -> government = Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> government = Government.TOTALITARIANISM;
                    case "JUNTA" -> government = Government.JUNTA;
                    default -> government = Government.NULL;
                }
                String HumanName = comd[10];
                Coordinates coordinates = new Coordinates(x, y);
                Human human = new Human(HumanName);
                try {
                    City city0 = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                    cities.add(city0);
                }catch (OUTofLimitExceptions e){
                    System.err.println(e);
                }
            }catch (NumberFormatException e){
                System.err.println("\nДобавление в колекцию не удалось, так как в команде неверный тип данных!");
            }
        }
        else if (p2.com_SHOW(commands)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                Collections.sort(list, new SortByID());
                System.out.println("id\t\tname\t\tcoordinates x&y\t\tcreationDate\tarea\t\tpopulation\tmetersAboveSeaLevel\t\ttimezone\tcarCode\t\tgovernor\t\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
        else if (p2.com_SAVE(commands)) {
                try {
                    FileWriter writer = new FileWriter("/Users/mubinjon9009/IdeaProjects/City/src/InteractiveEditing/collection.xml");
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
                    System.out.println("Коллекция сохранена в файл");
                }catch (IOException e){
                    System.err.println("Сохранение не удалось");
                }
            }
        else if (p2.com_CLEAR(commands)) {
                cities.clear();
                System.out.println("Коллекция очищена");
            }
        else if (p2.com_REMOVE_BY_ID(commands)) {
                String[] com = commands.split(" ");
                int Rid = Integer.parseInt(com[1]);
                cities = remove_cl(cities,Rid);
                System.out.println("REMOVED!");
            }
        else if (p2.com_UPDATE(commands)) {
                int Rid = Integer.parseInt(comd[1]);
                String fame = comd[2];
                double x = Double.parseDouble(comd[3]);
                long y = Long.parseLong(comd[4]);
                long area = Long.parseLong(comd[5]);
                int population = Integer.parseInt(comd[6]);
                float metersAboveSeaLevel = Float.parseFloat(comd[7]);
                double timezone = Double.parseDouble(comd[8]);
                long carCode = Long.parseLong(comd[9]);
                Government government = null;
                switch (comd[10].toUpperCase(Locale.ROOT)) {
                    case "DESPOTISM" -> government = Government.DESPOTISM;
                    case "COMMUNISM" -> government = Government.COMMUNISM;
                    case "PUPPET_STATE" -> government = Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> government = Government.TOTALITARIANISM;
                    case "JUNTA" -> government = Government.JUNTA;
                    default -> population=Integer.parseInt(comd[10]);
                }
                String HumanName = comd[11];
                cities =remove_cl(cities,Rid);
                Coordinates coordinates = new Coordinates(x, y);
                Human human = new Human(HumanName);
                try {
                City city0 = new City(Rid, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                cities.add(city0);
                System.out.println("UPDATED!");
                } catch (OUTofLimitExceptions e) {
                    System.err.println(e);
            }catch (NumberFormatException e){
                System.err.println("Введено не правильное значение!\nПовторите обновление коллекции заново");
            }
            }
        else if (p2.com_GOVERNMENT_I(commands)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
        else if (p2.com_GOVERNMENT_D(commands)) {
                ArrayList<City> list = new ArrayList<>(cities);
                Collections.sort(list, new SortByName());
                Collections.reverse(list);
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list) {
                    System.out.println(city);
                }
            }
        else if (p2.com_ADD_IF_MAX(commands)) {
                String[] words = commands.replace(",","").replace("{","").replace("}","").split(" ");
                String fame = words[1];
                double x = Double.parseDouble(words[2]);
                long y = Long.parseLong(words[3]);
                long area = Long.parseLong(words[4]);
                int population = Integer.parseInt(words[5]);
                float metersAboveSeaLevel = Float.parseFloat(words[6]);
                double timezone = Double.parseDouble(words[7]);
                long carCode = Long.parseLong(words[8]);
                Government government = null;
                switch (words[9]) {
                    case "DESPOTISM" -> government = Government.DESPOTISM;
                    case "COMMUNISM" -> government = Government.COMMUNISM;
                    case "PUPPET_STATE" -> government = Government.PUPPET_STATE;
                    case "TOTALITARIANISM" -> government = Government.TOTALITARIANISM;
                    case "JUNTA" -> government = Government.JUNTA;
                    default -> government = Government.NULL;
                }
                String HumanName = words[10];
                GenerateID(cities);
                for (City city : cities) {
                    if (city.getArea() >= MaxArea) {
                        MaxArea = city.getArea();
                    }
                    if (city.getPopulation() >= MaxPopulation) {
                        MaxPopulation = city.getPopulation();
                    }
                    if (city.getMetersAboveSeaLevel() >= MaxMeters) {
                        MaxMeters = city.getMetersAboveSeaLevel();
                    }
                    if (city.getTimezone() >= MaxTimezone) {
                        MaxTimezone = city.getTimezone();
                    }
                    if (city.getCarCode() >= MaxCarCode) {
                        MaxCarCode = city.getCarCode();
                    }
                }
                if (area > MaxArea) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (population > MaxPopulation) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (metersAboveSeaLevel > MaxMeters) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (timezone > MaxTimezone) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                } else if (carCode > MaxCarCode) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Human human = new Human(HumanName);
                    try {
                        City city = new City(generated_ID, fame, coordinates, area, population, metersAboveSeaLevel, timezone, carCode, government, human);
                        cities.add(city);
                    }catch (OUTofLimitExceptions e){
                        System.err.println(e);
                    }
                    System.out.println("Элемент добавлен");
                }
            }
        else if (p2.com_REMOVE_GREATER(commands)) {
                String[] words = commands.replace(",","").replace("{","").replace("}","").split(" ");
                s_area = Long.parseLong(words[4]);
                s_population = Integer.parseInt(words[5]);
                s_metersAboveSeaLevel = Float.parseFloat(words[6]);
                s_timezone = Double.parseDouble(words[7]);
                s_carCode = Long.parseLong(words[8]);
                cities = remove_gr(cities);
            }
        else if (p2.com_MIN_BY_GOVERNMENT(commands)){
                ArrayList<City> list1 = new ArrayList<>(cities);
                Collections.sort(list1, new SortByEnum());
                System.out.println("id\tname\tcoordinates x&y\tcreationDate\tarea\tpopulation\tmetersAboveSeaLevel\ttimezone\tcarCode\tgovernor\tgovernment");
                for (City city : list1) {
                    System.out.println(city);
                }
            }
        else {
            System.out.println(comd[0] + " не найдена!\nИсправьте ошибки и введите заново");
        }
    }
    public static int getMyInt(String msg){
        System.out.print("\nВведите значение типа int для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        int gInt;
        try {
            gInt=scanner.nextInt();
        }catch (InputMismatchException e){
            System.err.println("Не правильный тип данных!");
            gInt=getMyInt(msg);
        }
        return gInt;
    }
    public static String getMyString(String msg){
        System.out.print("\nВведите значение типа String для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        String gString;
        String [] cs= {"0","1","2","3","4","5","6","7","8","9",",",".","/","[","]","{","}"};
        gString=scanner.nextLine();
        for (String as :cs){
            if (gString.contains(as)){
                System.err.println("Не правильный тип данных!");
                gString=getMyString(msg);
                break;
            }
            else if (gString.equals("")){
                System.err.println("Поле не может быть пустым!");
                gString=getMyString(msg);
                break;
            }
        }
        return gString;
    }
    public static String getMyGovernment(String msg){
        System.out.println("DESPOTISM\nCOMMUNISM\nPUPPET_STATE\nTOTALITARIANISM\nJUNTA\nNULL");
        System.out.print("\nВведите значение типа enum для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        String gGovernment;
        String [] cs= {"0","1","2","3","4","5","6","7","8","9",",",".","/","[","]","{","}"};
        gGovernment=scanner.nextLine();
        for (String as :cs){
            if (gGovernment.contains(as)){
                System.err.println("Не правильный тип данных!");
                gGovernment=getMyString(msg);
                break;
            }
            else if (gGovernment.equals("")){
                System.err.println("Поле не может быть пустым!");
                gGovernment=getMyString(msg);
                break;
            }
        }
        gGovernment=gGovernment.toUpperCase(Locale.ROOT);
        if (gGovernment.equals("DESPOTISM") || gGovernment.equals("COMMUNISM") || gGovernment.equals("PUPPET_STATE") || gGovernment.equals("TOTALITARIANISM") || gGovernment.equals("JUNTA") || gGovernment .equals("NULL")){
            gGovernment=gGovernment.strip();
        }else{
            System.err.println("Исправьте ошибку и повторите ввод.");
            getMyGovernment(msg);
        }
        return gGovernment;
    }
    public static long getMyLong(String msg){
        System.out.print("\nВведите значение типа long для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        long gLong;
        try {
            gLong=scanner.nextLong();
        }catch (InputMismatchException e){
            System.err.println("Не правильный тип данных!");
            gLong=getMyLong(msg);
        }
        return gLong;
    }
    public static double getMyDouble(String msg){
        System.out.print("\nВведите значение типа double для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        double gDouble;
        try {
            gDouble=scanner.nextDouble();
        }catch (InputMismatchException e){
            System.err.println("Не правильный тип данных! Или поменяйте точку\".\" на запятую \",\"");
            gDouble=getMyDouble(msg);
        }
        return gDouble;
    }
    public static float getMyFloat(String msg){
        System.out.print("\nВведите значение типа float для поля "+msg+": ");
        Scanner scanner = new Scanner(System.in);
        float gFloat;
        try {
            gFloat=scanner.nextFloat();
        }catch (InputMismatchException e){
            System.err.println("Не правильный тип данных! Или поменяйте точку\".\" на запятую \",\"");
            gFloat=getMyFloat(msg);
        }
        return gFloat;
    }
}