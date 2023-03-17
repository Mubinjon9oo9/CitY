package Server;

import Commands.Sample;

public class Manage extends Thread{
    @Override
    public void run(){
        Sample s = new Sample();
        Connection conn = new Connection();
        while (conn.exit){
            System.out.println("Waiting for a client to connect...");
            s.Sampling(conn.receive(),true);
        }
    }
}
