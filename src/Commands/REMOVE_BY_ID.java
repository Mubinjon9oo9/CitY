package Commands;

public class REMOVE_BY_ID{

    public REMOVE_BY_ID(String [] command, boolean check) {
        Sample s = new Sample();
        try{
            int Rid = Integer.parseInt(command[1]);
            setData set = new setData();
            set.removeByID(Rid);
            s.Send_Out("Element with this ID has been removed",check);
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            s.Send_Out("INCORRECT ID!\nInput correct id",check);
        }
    }
}
