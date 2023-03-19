package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Connection{
    public final int SERVICE_PORT=50003;
    int sender_port,sending_errors=0;
    InetAddress sender_addr;
    public boolean exit = true;
    public Connection(){

    }

    public boolean send (String data){
        boolean sending = true;
        if (sending_errors>=10){
            System.out.println("Data transfer error! Data may be corrupted.");
            sending_errors=0;
            return false;
        }
        try{
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            byte[] sendingDataBuffer;
            sendingDataBuffer = data.getBytes();
            SavePort s = new SavePort();
            String [] file = s.getData().split(",");
            int senderPort = Integer.parseInt(file[0]);
            InetAddress senderAddress = InetAddress.getByName(file[1].replace("/","").strip());
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, senderAddress,senderPort);
            serverSocket.send(outputPacket);
            serverSocket.close();
        } catch (IOException e) {
            sending = false;
            sending_errors++;
            send(data);
        }
        return sending;
    }

    
    public String receive () {
        String receivedData = "Data is corrupted";
        try{
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            byte[] receivingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            serverSocket.receive(inputPacket);
            receivedData = new String(inputPacket.getData());
            receivedData = receivedData.replace("\u0000", "").strip();
            sender_port = inputPacket.getPort();
            sender_addr = inputPacket.getAddress();
            SavePort s = new SavePort();
            s.setDATA(sender_port,sender_addr);
            if (receivedData.equals("exit")) {
                exit = false;
                serverSocket.close();
                return "Thank you";
            }
            System.out.println("Received data: " + receivedData);
            serverSocket.close();
        }catch (IOException e){
            System.out.println("Data is corrupted!");
        }
        return receivedData;
    }
}
