import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal {
    static Parser parser;
    public static void chooseCommandAction(String command, List<String> arguments){
        switch (parser.getCommandName()) {
            case "echo":
                echo(arguments);
                break;
            case "pwd":
//                pwd();
                break;
            case "cd":
//                cd(arguments);
                break;
            case "ls":
//                ls(arguments);
                break;
            case "ls -r":
//                lsRecursive(arguments);
                break;
            case "mkdir":
//                mkdir(arguments);
                break;
            case "rmdir":
//                rmdir(arguments);
                break;
            case "touch":
//                touch(arguments);
                break;
            case "cp":
//                cp(arguments);
                break;
            case "cp -r":
//                cpRecursive(arguments);
                break;
            case "rm":
//                rm(arguments);
                break;
            case "cat":
//                cat(arguments);
                break;
            case "wc":
//                wc(arguments);
                break;
            case ">":
//                redirectOutput(arguments);
                break;
            case ">>":
//                appendOutput(arguments);
                break;
            case "history":
//                history();
                break;
        }
    }
    public static void echo(List<String> arguments){
        for (String s : arguments) {
            System.out.print(s+" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> history = new ArrayList<>();
        List<String> arguments = new ArrayList<>();
        parser = new Parser();
        String command ;

        while(true){
            System.out.print("Enter a command: ");

            String userInput = scanner.nextLine();

            if(userInput.equals("exit")) break;

            if(parser.parse(userInput) == true){
                arguments = parser.getArgs();
                command = parser.getCommandName();
                chooseCommandAction(command, arguments);
            }
            else {
                System.out.println("Invalid command !");
            }
            arguments.clear();
        }
        scanner.close();
        return ;
    }
}