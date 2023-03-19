package Commands;

public class CLEAR {

    public CLEAR(boolean check) {
        Sample s = new Sample();
        new setData().delAll();
        s.Send_Out("Collection CLEARED",check);
    }
}
