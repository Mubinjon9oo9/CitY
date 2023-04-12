package Commands;

import CollectionData.City;
import InteractiveEditing.OUTofLimitExceptions;
import Server.Connection;

import java.util.*;

public class Sample {
    String [] command;
    public int generated_ID, MaxID = 0, MaxPopulation = 0, s_population;
    public long s_area, s_carCode, MaxArea = 0, MaxCarCode = 0;
    public float s_metersAboveSeaLevel, MaxMeters = 0;
    public double s_timezone, MaxTimezone = 0;
    public ArrayList<String> history = new ArrayList<>();  

    public Sample() {

    }

    public void Sampling(String com, boolean check){
        command = com.strip().split(" ");
        switch (command[0].toUpperCase(Locale.ROOT).strip()){
            case "ADD":
                new ADD(check);
                break;
            case "ADD_IF_MAX":
                new ADD_IF_MAX(check);
                break;
                case "CLEAR":
                new CLEAR(check);
                break;
            case "EXECUTE_SCRIPT":
                new EXECUTE_SCRIPT(check);
                break;
            case "GOVERNMENT_D":
                new GOVERNMENT_D(check);
                break;
            case "GOVERNMENT_I":
                new GOVERNMENT_I(check);
                break;
            case "HELP":
                new HELP(check);
                break;
            case "HISTORY":
                new HISTORY();
                break;
            case "INFO":
                new INFO(check);
                break;
            case "MIN_BY_GOVERNMENT":
                new MIN_BY_GOVERNMENT(check);
                break;
            case "REMOVE_BY_ID":
                new REMOVE_BY_ID(command, check);
                break;
            case "REMOVE_GREATER":
                new REMOVE_GREATER(new getData().cities, check);
                break;
            case "SHOW":
                new SHOW(check);
                break;
            case "UPDATE":
                new UPDATE(command,check);
                break;
            default:
                Send_Out("There is no command: "+command[0],check);
        }
    }
    public void Send_Out(String outs, boolean check){
        if (!check){
            System.out.println(outs);
        } else{
            Connection conn = new Connection();
            conn.send(outs);
        }
    }
    /**
     * Этот метод получает коллекцию, сравнивает ее данные элемента с указаными в комманде. Если хоть один параметр больше указанны, то этот элемент будет удален.
     * @param set Наша коллекция
     * @return set Измененная коллекция
     */
    public void remove_gr(HashSet<City> set, boolean check) {
        Iterator<City> iterator = set.iterator();
        setData setData = new setData();
        while (iterator.hasNext()) {
            City city = iterator.next();
            if (city.getArea() > s_area) {
                setData.removeByID(city.getId());
            } else if (city.getPopulation() > s_population) {
                setData.removeByID(city.getId());
            } else if (city.getMetersAboveSeaLevel() > s_metersAboveSeaLevel) {
                setData.removeByID(city.getId());
            } else if (city.getTimezone() > s_timezone) {
                setData.removeByID(city.getId());
            } else if (city.getCarCode() > s_carCode) {
                setData.removeByID(city.getId());
            }
        }
        Send_Out("Removed: "+ " elements",check);
    }
    /**
     * Этот метод нужен для генерации уникального ID
     * @param set Наша коллекция
     */
    public void GenerateID(HashSet<City> set){

        for (City city : set) {
            if (city.getId()>generated_ID){
                generated_ID=city.getId();
            }
        }
        generated_ID+=1;
    }
    /**
     * Метод получает от пользователя данные типа Integer, обрабатывает и возращает в корректном виде
     * @param msg
     */
    public int getMyInt(String msg, String b, boolean check){
        int gInt=0;
            Connection conn = new Connection();
            conn.send("Input INTEGER for " + msg + ": ");
            try {
                gInt = Integer.parseInt(conn.receive());
            } catch (InputMismatchException e) {
                conn.send("Wrong TYPE");
                gInt = getMyInt(msg, b,check);
            }
        if (b.equals("Population")) {
            try {
                if (gInt <= 0) {
                    throw new OUTofLimitExceptions("Population - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                Send_Out(e.toString(),check);
                gInt=getMyInt(msg,b,check);
            }
        }
        return gInt;
    }

    /**
     * Метод получает от пользователя данные типа String, обрабатывает и возращает в корректном виде
     * @param msg
     */
    public String getMyString(String msg){
        Connection conn = new Connection();
        String a = "\"Input STRING for \""+msg+"\": \"";
        conn.send(a);
        String gString;
        String [] cs= {"0","1","2","3","4","5","6","7","8","9",",",".","/","[","]","{","}"};
        gString= conn.receive();
        System.out.println(gString);
        for (String as :cs){
            if (gString.contains(as)){
                conn.send("Wrong TYPE");
                gString=getMyString(msg);
                break;
            }
            else if (gString.equals("")){
                conn.send("Field cannot be EMPTY");
                gString=getMyString(msg);
                break;
            }
        }
        return gString;
    }

    /**
     * Метод получает от пользователя данные типа String, обрабатывает и возращает в корректном виде для последующего перевода в тип GOVERNMENT
     * @param msg
     */
    public String getMyGovernment(String msg){
        Connection conn = new Connection();
        conn.send("DESPOTISM\nCOMMUNISM\nPUPPET_STATE\nTOTALITARIANISM\nJUNTA\nNULL");
        conn.send("Input ENUM for "+msg+": ");
        String gGovernment;
        String [] cs= {"0","1","2","3","4","5","6","7","8","9",",",".","/","[","]","{","}"};
        gGovernment=conn.receive();
        for (String as :cs){
            if (gGovernment.contains(as)){
                conn.send("Wrong TYPE");
                gGovernment=getMyGovernment(msg);
                break;
            }
            else if (gGovernment.equals("")){
                conn.send("Field cannot be EMPTY");
                gGovernment=getMyGovernment(msg);
                break;
            }
        }
        gGovernment=gGovernment.toUpperCase(Locale.ROOT);
        if (gGovernment.equals("DESPOTISM") || gGovernment.equals("COMMUNISM") || gGovernment.equals("PUPPET_STATE") || gGovernment.equals("TOTALITARIANISM") || gGovernment.equals("JUNTA") || gGovernment .equals("NULL")){
            gGovernment=gGovernment.strip();
        }else{
            conn.send("Fix errors and try again");
            getMyGovernment(msg);
        }
        return gGovernment;
    }

    /**
     * Метод получает от пользователя данные типа LONG, обрабатывает и возращает в корректном виде
     * @param msg
     */
    public long getMyLong(String msg,String b) {
        Connection conn = new Connection();
        conn.send("Input LONG for "+msg+": ");
        long gLong;
        try {
            gLong=Long.parseLong(conn.receive());

        }catch (InputMismatchException e){
            conn.send("Wrong TYPE");
            gLong=getMyLong(msg,b);
        }
        if (b.equals("Y")) {
            try {
                if (gLong > 572) {
                    throw new OUTofLimitExceptions("Y - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                System.err.println(e);
                gLong=getMyLong(msg,b);
            }
        }
        if (b.equals("Area")) {
            try {
                if (gLong <= 0) {
                    throw new OUTofLimitExceptions("Area - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                System.err.println(e);
                gLong=getMyLong(msg,b);
            }
        }
        if (b.equals("carCode")) {
            try {
                if (gLong <= 0 || gLong > 1000) {
                    throw new OUTofLimitExceptions("carCode - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                System.err.println(e);
                gLong=getMyLong(msg,b);
            }
        }
        return gLong;
    }

    /**
     * Метод получает от пользователя данные типа DOUBLE, обрабатывает и возращает в корректном виде
     * @param msg
     */
    public double getMyDouble(String msg, String b){
        Connection conn = new Connection();
        conn.send("Input DOUBLE for "+msg+": ");
        double gDouble;
        try {
            gDouble=Double.parseDouble(conn.receive());
        }catch (InputMismatchException  | NumberFormatException e){
            conn.send("Wrong TYPE. Try to change dot \".\" to comma \",\"");
            gDouble=getMyDouble(msg,b);
        }
        if (b.equals("X")) {
            try {
                if (gDouble > 812) {
                    throw new OUTofLimitExceptions("X - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                System.err.println(e);
                gDouble=getMyDouble(msg,b);
            }
        }
        if (b.equals("Timezone")) {
            try {
                if (gDouble < -13 || gDouble > 15) {
                    throw new OUTofLimitExceptions("Timezone - Out of limit");
                }
            }catch (OUTofLimitExceptions e){
                System.err.println(e);
                gDouble=getMyDouble(msg,b);
            }
        }
        return gDouble;
    }

    /**
     * Метод получает от пользователя данные типа FLOAT, обрабатывает и возращает в корректном виде
     * @param msg
     */
    public float getMyFloat(String msg){
        Connection conn = new Connection();
        conn.send("Input FLOAT for "+msg+": ");
        float gFloat;
        try {
            gFloat=Float.parseFloat(conn.receive());
        }catch (InputMismatchException | NumberFormatException e){
            conn.send("Wrong TYPE. Try to change dot \".\" to comma \",\"");
            gFloat=getMyFloat(msg);
        }
        return gFloat;
    }
}
