package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Main{
    /* Порт сервера, к которому собирается
  подключиться клиентский сокет */
    public final static int SERVICE_PORT = 50003;
    public static int sending_errors= 0, num=0;
    public static boolean exit = true;

    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        while (exit){
            boolean b = true;
            System.out.println("===INPUT=COMMAND===");
            String com = scan.nextLine();
            send(com);
            num=0;
            while (b) {
                String a = receive();
                if (a.contains("|W|")){
                    a= a.replace("|W|","");
                    System.out.println(a);
                }else{
                    System.out.println(a);
                    break;
                }
            }
        }
    }
    public static boolean send (String data){
        boolean sending = true;
        if (sending_errors>=10){
            System.out.println("Data transfer error! Data may be corrupted.");
            sending_errors=0;
            return false;
        }
        try{
            DatagramSocket serverSocket = new DatagramSocket(50004);
            byte[] sendingDataBuffer = new byte[1024];
            sendingDataBuffer = data.getBytes();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress,SERVICE_PORT);
            serverSocket.send(outputPacket);
            serverSocket.close();
            System.out.println("Data have been send!");
            System.out.println("==============SERVER===============");
        } catch (IOException e) {
            System.out.println(e);
            sending = false;
            sending_errors++;
            send(data);
        }
        return sending;
    }

    public static String receive () {
        String receivedData = "Data is corrupted";
        try{
            DatagramSocket serverSocket = new DatagramSocket(50004);
            byte[] receivingDataBuffer = new byte[1024];
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            if (num < 1) {
                System.out.println("Waiting for a server answer...");
                num++;
            }
            serverSocket.receive(inputPacket);
            receivedData = new String(inputPacket.getData());
            receivedData = receivedData.replace("\u0000", "").strip();
            serverSocket.close();
        }catch (IOException e){
            System.out.println("Data is corrupted!");
        }
        return receivedData;
    }
}