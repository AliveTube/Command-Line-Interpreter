import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Terminal {
    static Parser parser;
    static Path cur = Paths.get("");
    static List<String> historyList = new ArrayList<>();
    public static void chooseCommandAction(String command, List<String> arguments) {
        historyList.add(command);
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
                ls();
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
        System.out.println(cur.toString());
    }

    public static void cd(List<String> arguments) {
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
                Path tmp = Paths.get(arguments.get(0)).toAbsolutePath();
                if(!tmp.toFile().exists() || !tmp.toFile().isDirectory()){
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

    public static void ls() {
        File directoryName = new File(cur.toUri());
        File[] fileList = directoryName.listFiles();

        if (fileList != null) {
            for (File file : fileList) {
                System.out.println(file.getName());
            }
        }
        else {
            System.out.println("The directory is empty or doesn't exist!");
        }
    }

    public static void lsRecursive(List<String> arguments) {
        // Implement ls -r command logic
    }

    public static void mkdir(List<String> arguments) {
        if (arguments.size() == 0) {
            System.out.println("Invalid arguments!");
            return;
        }
        for (String arg : arguments) {
            File dir = new File(arg);
            if (!dir.isAbsolute()) {
                dir = new File(cur.toFile(), arg);
            }
            if (!dir.exists() && dir.mkdirs()) {
                System.out.println("Created directory: " + dir.getAbsolutePath());
            }
            else {
                System.out.println("Directory already exists!");
            }
        }
    }

    public static void rmdir(List<String> arguments) {
        if (arguments.size() != 1) {
            System.out.println("Invalid arguments!");
            return;
        }
        String argument = arguments.get(0);
        if(argument.equals("*")){
            File dir = new File(cur.toString());
            File[] subDirs = dir.listFiles();
            for (File subDir : subDirs) {
                if (subDir.exists() && subDir.isDirectory()) {
                    if (subDir.list().length == 0) {
                        if (subDir.delete()) {
                            System.out.println("Deleted directory successfully");
                        }
                        else {
                            System.out.println("Failed to delete directory");
                        }
                    }
                    else {
                        System.out.println("Directory is not empty");
                    }
                }
                else {
                    System.out.println("Invalid arguments!");
                }
            }
        }
        else{
            Path tmp = cur.resolve(arguments.get(0)).toAbsolutePath();
            File dir = new File(tmp.toString());
            if (dir.exists() && dir.isDirectory()) {
                if (dir.list().length == 0) {
                    if (dir.delete()) {
                        System.out.println("Deleted directory successfully");
                    }
                    else {
                        System.out.println("Failed to delete directory");
                    }
                }
                else {
                    System.out.println("Directory is not empty");
                }
            }
            else {
                System.out.println("Invalid arguments!");
            }
        }
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
            File file = new File(cur.resolve(arguments.get(0)).toString());
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
        File file = new File(cur.resolve(arguments.get(0)).toString());
        if(arguments.size() != 1){
            System.out.println("Invalid arguments!");
            return;
        }
        if(Files.exists(file.toPath()) && Files.isRegularFile(cur.resolve(arguments.get(0)))){
            try {
                int linesCount = 0, charCount = 0, wordCount = 0;
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()){
                    linesCount++;
                    String data = myReader.nextLine();
                    charCount += data.length();
                    String[] words = data.split("\\s+");
                    wordCount += words.length;
                }
                System.out.println(linesCount + " " + wordCount + " " + charCount + " " + arguments.get(0));
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File not found!");
        }
    }

    public static void redirectOutput(List<String> arguments) {
        // Implement > command logic
    }

    public static void appendOutput(List<String> arguments) {
        // Implement >> command logic
    }

    public static void history() {
        int counter = 1;
        for (String item : historyList) {
            System.out.println(counter + "- " + item);
            counter++;
        }
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
            // We will delete this later on
            System.out.println(cur.toString());
        }
        scanner.close();
    }
}