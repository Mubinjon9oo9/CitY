package Server;

import Commands.Sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;


public class Connection{
    int sender_port,sending_errors=0;
    InetAddress sender_addr;
    public boolean exit = true;
    public Connection(boolean b) {
        exit=b;
    }
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
            DatagramSocket serverSocket = new DatagramSocket(2222);
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
            DatagramSocket serverSocket = new DatagramSocket(2222);
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
                send("Thank you");
                return "exit";
            }
            System.out.println("Received data: " + receivedData);
            serverSocket.close();
        }catch (IOException e){
            System.out.println("Data is corrupted!");
        }
        return receivedData;
    }
    public String getCurrentIP() {
        String result = null;
        try {
            BufferedReader reader = null;
            try {
                URL url = new URL("http://myip.by/");
                InputStream inputStream = null;
                inputStream = url.openStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder allText = new StringBuilder();
                char[] buff = new char[1024];
                int count = 0;
                while ((count = reader.read(buff)) != -1) {
                    allText.append(buff, 0, count);
                }
                Integer indStart = allText.indexOf("\">whois ");
                Integer indEnd = allText.indexOf("</a>", indStart);

                String ipAddress = new String(allText.substring(indStart + 8, indEnd));
                if (ipAddress.split("\\.").length == 4) { // минимальная (неполная)
                    //проверка что выбранный текст является ip адресом.
                    result = ipAddress;
                }
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
}
}
