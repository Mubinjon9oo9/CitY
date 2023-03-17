package InteractiveEditing;
/**
 * Класс для обработки и выбора команд
 */
public class TreatmentTXT {
    /**
     * Проверяет входные данные на команду "HELP"
     * @param txt
     */
    public boolean com_HELP(String txt){
        String [] words = txt.split(" ");
        if (words[0].equals("help")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "HISTORY"
     * @param txt
     */
    public boolean com_HISTORY(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("history")){
            return true;
        }
        return false;
    }

    /**
     * Проверяет входные данные на команду "EXIT"
     * @param txt
     */
    public boolean Exit(String txt){
        txt=txt.replace(" ","");
        if (txt.contains("exit")) {
            return false;
        }
        return true;
    }

    /**
     * Проверяет входные данные на команду "INFO"
     * @param txt
     */
    public boolean com_INFO(String txt){
        txt= txt.replace(" ", "");
        if (txt.equals("info")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "ADD"
     * @param txt
     */
    public boolean com_ADD(String txt){
        String words[] = txt.split(" ");
        if (words[0].equals("add")){

            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "SHOW"
     * @param txt
     */
    public boolean com_SHOW(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("show")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "CLEAR"
     * @param txt
     */
    public boolean com_CLEAR(String txt){
        txt=txt.replace(" ","");
        if(txt.equals("clear")){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Проверяет входные данные на команду "SAVE"
     * @param txt
     */
    public boolean com_SAVE(String txt){
        txt=txt.replace(" ","");
        if (txt.equals("save")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "REMOVE_BY_ID"
     * @param txt
     */
    public boolean com_REMOVE_BY_ID(String txt){
        String [] words = txt.split(" ");
        if(words[0].equals("remove_by_id")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "UPDATE"
     * @param txt
     */
    public boolean com_UPDATE(String txt){
        String [] words = txt.split(" ");
        if(words[0].equals("update")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "GOVERNMENT_I"
     * @param txt
     */
    public boolean com_GOVERNMENT_I(String txt) {
        txt=txt.replace(" ","");
        if (txt.equals("print_field_ascending_governor")) {
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "GOVERNMENT_D"
     * @param txt
     */
    public boolean com_GOVERNMENT_D(String txt) {
        txt=txt.replace(" ","");
        if (txt.equals("print_field_descending_governor")) {
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "ADD_IF_MAX"
     * @param txt
     */
    public boolean com_ADD_IF_MAX(String txt){
        String [] words = txt.split(" ");
        if (words[0].equals("add_if_max")){
            return true;
        }
        else return false;
    }

    /**
     * Проверяет входные данные на команду "REMOVE_GREATER"
     * @param txt
     */
    public boolean com_REMOVE_GREATER(String txt){
        String [] words = txt.split(" ");
            if (words[0].equals("remove_greater")){
                return true;
            }
            else return false;
    }

    /**
     * Проверяет входные данные на команду "MIN_BY_GOVERNMENT"
     * @param txt
     */
    public boolean com_MIN_BY_GOVERNMENT(String txt){
        txt = txt.replace(" ","");
        if (txt.equals("min_by_government")){
            return true;
        }
        else return false;
    }
}