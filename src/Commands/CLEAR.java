package Commands;

import CollectionData.City;
import Server.Connection;

import java.util.HashSet;

public class CLEAR {

    public CLEAR(boolean check) {
        Sample s = new Sample();
        new setData().delAll();
        s.Send_Out("Collection CLEARED",check);
    }
}
