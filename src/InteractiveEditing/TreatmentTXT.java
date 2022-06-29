package InteractiveEditing;
/**
 * Класс для обработки и выбора команд
 */
public class TreatmentTXT {

    public boolean com_HELP(String txt){
        String [] words = txt.split(" ");
        if (words[0].equals("help")){
            return true;
        }
        else return false;
    }
    public boolean com_HISTORY(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("history")){
            return true;
        }
        return false;
    }
    public boolean Exit(String txt){
        txt=txt.replace(" ","");
        if (txt.contains("exit")) {
            return false;
        }
        return true;
    }
    public boolean com_INFO(String txt){
        txt= txt.replace(" ", "");
        if (txt.equals("info")){
            return true;
        }
        else return false;
    }
    public boolean com_ADD(String txt){
        String words[] = txt.split(" ");
        if (words[0].equals("add")){

            return true;
        }
        else return false;
    }
    public boolean com_SHOW(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("show")){
            return true;
        }
        else return false;
    }
    public boolean com_CLEAR(String txt){
        txt=txt.replace(" ","");
        if(txt.equals("clear")){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean com_SAVE(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("save")){
            return true;
        }
        else return false;
    }
    public boolean com_REMOVE_BY_ID(String txt){
        String [] words = txt.split(" ");
        if(words[0].equals("remove_by_id")){
            return true;
        }
        else return false;
    }
    public boolean com_UPDATE(String txt){
        String [] words = txt.split(" ");
        if(words[0].equals("update")){
            return true;
        }
        else return false;
    }
    public boolean com_GOVERNMENT_I(String txt) {
        txt=txt.replace(" ","");
        if (txt.equals("print_field_ascending_governor")) {
            return true;
        }
        else return false;
    }
    public boolean com_GOVERNMENT_D(String txt) {
        txt=txt.replace(" ","");
        if (txt.equals("print_field_descending_governor")) {
            return true;
        }
        else return false;
    }
    public boolean com_ADD_IF_MAX(String txt){
        String [] words = txt.split(" ");
        if (words[0].equals("add_if_max")){
            return true;
        }
        else return false;
    }
    public boolean com_REMOVE_GREATER(String txt){
        String [] words = txt.split(" ");
            if (words[0].equals("remove_greater")){
                return true;
            }
            else return false;
    }
    public boolean com_MIN_BY_GOVERNMENT(String txt){
        txt = txt.replace(" ","");
        if (txt.equals("min_by_government")){
            return true;
        }
        else return false;
    }

}
