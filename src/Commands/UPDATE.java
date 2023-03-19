package Commands;

public class UPDATE{

    public UPDATE(String [] com, boolean check) {
        Sample s = new Sample();
        new REMOVE_BY_ID(com,check);
        new ADD(check);
        s.Send_Out("UPDATED!",check);
        s.Send_Out("There is NO element with this ID",check);
    }
}
