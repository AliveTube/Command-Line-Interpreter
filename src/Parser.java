import java.util.List;
import java.util.Arrays;

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