import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Terminal {
    static Parser parser;
    static Path cur = Paths.get("");

    public static void chooseCommandAction(String command, List<String> arguments) {
        switch (command) {
            case "echo":
                echo(arguments);
                break;
            case "pwd":
                pwd();
                break;
            case "cd":
                cd(arguments);
                break;
            case "ls":
                ls(arguments);
                break;
            case "ls -r":
                lsRecursive(arguments);
                break;
            case "mkdir":
                mkdir(arguments);
                break;
            case "rmdir":
                rmdir(arguments);
                break;
            case "touch":
                touch(arguments);
                break;
            case "cp":
                cp(arguments);
                break;
            case "cp -r":
                cpRecursive(arguments);
                break;
            case "rm":
                rm(arguments);
                break;
            case "cat":
                cat(arguments);
                break;
            case "wc":
                wc(arguments);
                break;
            case ">":
                redirectOutput(arguments);
                break;
            case ">>":
                appendOutput(arguments);
                break;
            case "history":
                history();
                break;
        }
    }
    public static void echo(List<String> arguments) {
        for (String s : arguments) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void pwd() {
        // Implement pwd command logic
        System.out.println(cur.toString());
    }

    public static void cd(List<String> arguments) {

        // Implement cd command logic
        if(arguments.isEmpty()){
            cur = Paths.get("");
            cur = cur.toAbsolutePath();
        }
        else if(arguments.size() == 1){
            if(arguments.get(0).equals("..")){
                if(cur.getParent() != null)
                    cur = cur.getParent();
            }
            else{
                Path tmp = Paths.get(arguments.get(0));
                tmp = tmp.toAbsolutePath();
                if(!tmp.toFile().exists()){
                    System.out.println("Invalid path!");
                }
                else{
                    cur = tmp;
                }
            }
        }
        else{
            System.out.println("Invalid arguments!");
        }
    }

    public static void ls(List<String> arguments) {
        // Implement ls command logic
    }

    public static void lsRecursive(List<String> arguments) {
        // Implement ls -r command logic
    }

    public static void mkdir(List<String> arguments) {
        // Implement mkdir command logic
    }

    public static void rmdir(List<String> arguments) {

    }

    public static void touch(List<String> arguments) {
        // Implement touch command logic
    }

    public static void cp(List<String> arguments) {
        // Implement cp command logic
    }

    public static void cpRecursive(List<String> arguments) {
        // Implement cp -r command logic
    }

    public static void rm(List<String> arguments) {
        // Implement rm command logic
        if(arguments.size() != 1){
            System.out.println("Invalid command!");
            return;
        }

        if(Files.exists(cur.resolve(arguments.get(0))) && Files.isRegularFile(cur.resolve(arguments.get(0)))){
            File file
                    = new File(cur.resolve(arguments.get(0)).toString());
            if (file.delete()) {
                System.out.println("File deleted successfully");
            }
            else {
                System.out.println("Failed to delete the file");
            }
        }
        else{
            System.out.println("Invalid arguments!");
        }
    }

    public static void cat(List<String> arguments) {
        // Implement cat command logic
    }

    public static void wc(List<String> arguments) {
        // Implement wc command logic
    }

    public static void redirectOutput(List<String> arguments) {
        // Implement > command logic
    }

    public static void appendOutput(List<String> arguments) {
        // Implement >> command logic
    }

    public static void history() {
        // Implement history command logic
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        parser = new Parser();
        cur = cur.toAbsolutePath();
        String command;

        while (true) {
            List<String> arguments;
            System.out.print("Enter a command: ");

            String userInput = scanner.nextLine();

            if (userInput.equals("exit")) break;

            if (parser.parse(userInput)) {
                String[] argsArray = parser.getArgs();
                arguments = Arrays.asList(argsArray);
                command = parser.getCommandName();
                chooseCommandAction(command, arguments);
            }
            else {
                System.out.println("Invalid command!");
            }

            System.out.println(cur.toString());
        }
        scanner.close();
    }
}
