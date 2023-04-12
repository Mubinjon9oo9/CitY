package Client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;
/** Класс для взаимодейтсвия пользователя и сервера*/
public class Main {
    /**
     * Порт сервера, к которому собирается
     * подключиться клиентский сокет
     */
    static int send_port = 0, re_port = 0;
    public static int sending_errors = 0, num = 0;
    public static boolean exit = true;
    public static String ips;

    /**
     * Главный метод взаимодейтсвующий с пользователем
     */
    public static void main(String[] args) throws IOException {
        setPort();
        Scanner scan = new Scanner(System.in);
        while (exit) {
            boolean b = true;
            System.out.println("===INPUT=COMMAND===");
            String com = scan.nextLine();
            if (com.contains("execute_script")) {
                send(readFile(com));
            } else {
                send(com);
                num = 0;
                while (b) {
                    String a = receive();
                    if (a.contains("|W|")) {
                        a = a.replace("|W|", "");
                        System.out.println(a);
                    } else {
                        System.out.println(a);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Метод отправляющий данные на сервер
     *
     * @param data (String)
     * @return sending (boolean)
     */
    public static boolean send(String data) {
        boolean sending = true;
        if (sending_errors >= 10) {
            System.out.println("Data transfer error! Data may be corrupted.");
            sending_errors = 0;
            return false;
        }
        try {
            DatagramSocket serverSocket = new DatagramSocket(re_port);
            re_port = serverSocket.getLocalPort();
            byte[] sendingDataBuffer = new byte[1024];
            sendingDataBuffer = data.getBytes();
            InetAddress IPAddress = InetAddress.getByName(ips);
            DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, send_port);
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

    public static void setPort() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Input Port: ");
        try {
            //int port = scan.nextInt();
            //if (port == 0) {
            //    throw new InputMismatchException();
            //}
            send_port = 2222;
            ips = scan.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid type of Data\nInput INTEGER type required\nPort cannot be 0!");
            setPort();
        }
    }

    /**
     * Класс для получения данных от сервера
     * Класс получает данные, обрабатывает и возвращает в качестве текста для вывода пользователю.
     *
     * @return receivedData (String)
     */
    public static String receive() {

        String receivedData = "Data is corrupted";
        try {
            DatagramSocket serverSocket = new DatagramSocket(re_port);
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
        } catch (IOException e) {
            System.out.println("Data is corrupted!");
        }
        return receivedData;
    }

    /**
     * Чтение файла и конвертация в текст
     */
    public static String readFile(String exec) {
        try {
            String[] com = exec.split(" ");
            send(com[0]);
            File file = new File(com[1].strip());
            InputStream IS = new FileInputStream(file);
            BufferedInputStream BIS = new BufferedInputStream(IS);
            int code;
            char letter;
            String word = "";
            while ((code = BIS.read()) != -1) {
                if (code != 10 && code != 32) {
                    letter = (char) code;
                    word = word + letter;
                }
                if (code == 10) {
                    word = word + "\n";
                }
                if (code == 32) {
                    word = word + " ";
                }
            }
            BIS.close();
            return word;
        } catch (IOException e) {
            System.err.println("File not exist!\nCheck your file path");
        }
        return "Something has gone wrong!";
    }
}