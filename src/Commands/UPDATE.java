package Commands;

import CollectionData.City;
import CollectionData.Coordinates;
import CollectionData.Government;
import CollectionData.Human;
import InteractiveEditing.OUTofLimitExceptions;

import java.util.HashSet;

public class UPDATE{

    public UPDATE(String [] com, boolean check) {
        Sample s = new Sample();
        new REMOVE_BY_ID(com,check);
        new ADD(check);
        s.Send_Out("UPDATED!",check);
        s.Send_Out("There is NO element with this ID",check);
    }
}
