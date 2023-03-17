package Commands;

import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

public class getData {
    HashSet<City> cities = new HashSet<City>();
    public getData(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mubinjon9009");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cities");
            while (rs.next()) {
                int id  = rs.getInt(1);
                String name = rs.getString(2);
                double x = rs.getDouble(3);
                long y = rs.getLong(4);
                LocalDate creationDate = LocalDate.parse(rs.getString(5));
                long area = rs.getLong(6);
                int population = rs.getInt(7);
                float meters = rs.getFloat(8);
                double timezone = rs.getDouble(9);
                long carcode = rs.getLong(10);
                Government gov = getGovernment(rs.getString(11));
                String governor = rs.getString(12);
                Coordinates coordinates = new Coordinates(x,y);
                Human human = new Human(governor);
                try {
                    City city = new City(id,name, coordinates,creationDate,area,population,meters,timezone,carcode,gov,human);
                    cities.add(city);
                } catch (OUTofLimitExceptions e) {
                    System.out.println("ERROR");
                    e.printStackTrace();
                }
            }
            for (City city: cities){
                System.out.println(city);
            }
            rs.close();
            st.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Government getGovernment(String text){
        Government government = null;
        switch (text.toUpperCase(Locale.ROOT)){
            case "DESPOTISM":
                government=Government.DESPOTISM;
            case "COMMUNISM":
                government=Government.COMMUNISM;
            case "PUPPET_STATE":
                government=Government.PUPPET_STATE;
            case "TOTALITARIANISM":
                government=Government.TOTALITARIANISM;
            case "JUNTA":
                government=Government.JUNTA;
                break;
            default:
                government=Government.NULL;
        }
        return government;
    }
}
