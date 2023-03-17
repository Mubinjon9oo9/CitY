package Commands;

import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;

public class setData {
    public setData(){
        HashSet<City> cities = new HashSet<City>();
    }
    public void del(int id, String name, double x, long y, LocalDate date, long area, Integer population, float meters, double timezone, long carCode, Government government, String human){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mubinjon9009");
            Statement st = connection.createStatement();
            String com = id+", \'"+name+"\', "+x+", "+y+", \'"+date +"\', "+area+", "+population+", "+meters+", "+timezone
                    +", "+carCode+", \'"+government+"\', \'"+human+"\'";
            System.out.println(com);
            ResultSet rs = st.executeQuery("INSERT into cities values("+com+");");
            rs.close();
            st.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean removeByID(int remid) {
        String del = String.valueOf(remid);
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mubinjon9009");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("DELETE FROM cities WHERE city_id = " + del);
            rs.close();
            st.close();
        } catch (PSQLException e ){
            System.out.println("Done!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public void delAll(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mubinjon9009");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("DELETE FROM cities;");
            rs.close();
            st.close();
        } catch (PSQLException e ){
            System.out.println("Done!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
