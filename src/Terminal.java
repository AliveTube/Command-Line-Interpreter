import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Parser {
    private String commandName;
    String[] args;
    public Parser() {
        commandName = "";
        args = new String[0];
    }

    public boolean parse(String input){
        List<String> commandList = Arrays.asList("echo", "pwd", "cd", "ls", "ls -r", "mkdir", "rmdir", "touch", "cp", "cp -r", "rm", "cat", "wc", ">", ">>", "history");

        String[] words = input.split("\\s+");
        if(words.length > 1){
            if(commandList.contains(words[0]+" "+words[1])){
                commandName = words[0] + " " + words[1];
                args = new String[words.length - 2];

                System.arraycopy(words, 2, args, 0, words.length - 2);

                return true;
            }
        }

        if(words.length >= 1){
            if(commandList.contains(words[0])){
                commandName = words[0];
                args = new String[words.length - 1];

                System.arraycopy(words, 1, args, 0, words.length - 1);

                return true;
            }
        }
        return false;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs() {
        return args;
    }
}
public class Terminal {
    static Parser parser;
    static Path cur = Paths.get("");
    static List<String> historyList = new ArrayList<>();
    public static void chooseCommandAction(String command, List<String> arguments) throws IOException {
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
                lsReversed();
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
            case "rm":
                rm(arguments);
                break;
            case "cat":
                cat(arguments);
                break;
            case "wc":
                wc(arguments);
                break;
            case "history":
                history();
                break;
            case "exit":
                exit();
                break;
        }
    }
    public static void echo(List<String> arguments) {
        for (String s : arguments) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void exit() {
        System.exit(0);
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
                Path tmp = cur.resolve(arguments.get(0)).toAbsolutePath();
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

    public static void lsReversed() {
        File directoryName = new File(cur.toUri());
        File[] fileList = directoryName.listFiles();

        if (fileList != null) {
            for (int i = fileList.length - 1; i >= 0; i--) {
                File file = fileList[i];
                System.out.println(file.getName());
            }
        } else {
            System.out.println("The directory is empty or doesn't exist!");
        }
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
        if (arguments.size() != 1) {
            System.out.println("Invalid arguments!");
        }

        Path path1 = cur.resolve(arguments.get(0)).toAbsolutePath();
        try {

            if (!Files.exists(path1)) {
                Files.createFile(path1);
                System.out.println("File created at: " + path1.toAbsolutePath());
            } else {
                System.out.println("File already exists at: " + path1.toAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    public static void cp(List<String> arguments) throws IOException {
        if (arguments.size() != 2) {
            System.out.println("The number of arguments must be 2");
            return;
        }
        Path path1 = Paths.get(arguments.get(0));
        Path path2 = Paths.get(arguments.get(1));
        if (!path1.isAbsolute()) {
            path1 = cur.resolve(arguments.get(0));
        }
        if (!path1.toFile().exists()) {
            System.out.println("The first file name or directory isn't correct");
            return;
        }
        if (!path2.isAbsolute()) {
            path2 = cur.resolve(arguments.get(1));
        }
        if (!path2.toFile().exists()) {
            System.out.println("The second file name or directory isn't correct");
            return;
        }
        String line;
        FileReader file1 = new FileReader(path1.toString());
        BufferedReader readFile1 = new BufferedReader(file1);

        FileWriter file2 = new FileWriter(path2.toString(),true);
        while ((line = readFile1.readLine()) != null) {
            file2.write(line);
            file2.write('\n');
        }
        file1.close();
        file2.close();
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

    public static void cat(List<String> arguments) throws IOException {
        if (arguments.size() > 2 || arguments.isEmpty()) {
            System.out.println("Invalid arguments");
            return;
        }
        Path path1 = Paths.get(arguments.get(0));
        if (!path1.isAbsolute()) {
            path1 = cur.resolve(arguments.get(0));
        }
        if (!path1.toFile().exists()) {
            System.out.println("The first file name or directory isn't correct");
            return;
        }
        String line;
        FileReader file1 = new FileReader(path1.toString());
        BufferedReader readFile1 = new BufferedReader(file1);

        if (arguments.size() == 2) {
            Path path2 = Paths.get(arguments.get(1));
            if (!path2.isAbsolute()) {
                path2 = cur.resolve(arguments.get(1));
            }
            if (!path2.toFile().exists()) {
                System.out.println("The second file name or directory isn't correct");
                return;
            }

            while ((line = readFile1.readLine()) != null) {
                System.out.println(line);
            }

            FileReader file2 = new FileReader(path2.toString());
            BufferedReader readFile2 = new BufferedReader(file2);
            while ((line = readFile2.readLine()) != null) {
                System.out.println(line);
            }
            file2.close();
        }

        else {
            while ((line = readFile1.readLine()) != null) {
                System.out.println(line);
            }
        }
        file1.close();
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

    public static void history() {
        int counter = 1;
        for (String item : historyList) {
            System.out.println(counter + "- " + item);
            counter++;
        }
    }

    public static void main(String[] args) throws IOException {
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
        }
        scanner.close();
    }
}
