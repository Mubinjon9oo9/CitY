package InteractiveEditing;

import java.util.Scanner;
/**
 * Класс для получения комманд из командной строки.
 */
public class newCommand {
    public String command;
    public String getCommand() {
        System.out.print("\ninput new command$ ");
        Scanner scan = new Scanner(System.in);
        command = scan.nextLine();
        return command;
    }
}
