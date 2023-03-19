package Server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class SavePort {
    public void setDATA(int senderPort, InetAddress senderAdrr){
        try {
            FileWriter writer = new FileWriter("ports.txt");
            writer.write(senderPort+","+senderAdrr);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getData(){
        String fileName = "ports.txt";
        Optional<String> line = null;
        try {
            line = Files.lines(Paths.get(fileName)).findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ab = line.get();
        return ab;
    }
}
