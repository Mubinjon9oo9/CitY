package Server;

import CollectionData.Government;
import Commands.Sample;
import java.io.*;
import java.util.Scanner;

/**
 * Класс для чтения из файла и конвертирования байтов в текст
 */
public class ReadFile {
    Government government = null;

    /**
     * Чтение файла и конвертация в текст
     */
    public void readFile(boolean check){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input PATH");
            String PathFile = scanner.nextLine();
            File file = new File(PathFile.strip());
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
                    System.out.println("\n" + word);

                    word = "";
                }
                if (code == 32) {
                    word = word + " ";
                }
            }
            BIS.close();
            new Sample().Sampling(word,check);
        }catch (IOException e){
            System.err.println("File not exist!\nCheck your file path");
            readFile(check);
        }
    }
}
