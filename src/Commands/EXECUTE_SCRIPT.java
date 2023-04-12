package Commands;

import Server.Connection;

public class EXECUTE_SCRIPT{

    public EXECUTE_SCRIPT(boolean check) {
        String all = new Connection().receive();
        System.out.println(all);
        //for (String a:all){
          //  System.out.println(a);
        //}
    }
}
