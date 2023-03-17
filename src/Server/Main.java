package Server;

import CollectionData.City;
import Commands.Sample;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        boolean exit = false;
        Manage wait_conn = new Manage();
        wait_conn.start();
        wait_conn.getState();
        ReadFile p3 = new ReadFile();
        Scanner scan = new Scanner(System.in);
        String l = scan.nextLine().toLowerCase(Locale.ROOT);
        if (l.equals("start")){
            exit=true;
            System.out.println("Welcome to the Console");
        }
        while (exit){
            System.out.print("Input COMMAND: ");
            String command = scan.nextLine();
        }


    }
}