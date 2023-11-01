import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Parser {
    private String commandName;
    private List<String> args;

    public Parser() {
        commandName = "";
        args = new ArrayList<String>();
    }

    public boolean parse(String input){
        List<String> commandList = Arrays.asList("echo", "pwd", "cd", "ls", "ls -r", "mkdir", "rmdir", "touch", "cp", "cp -r", "rm", "cat", "wc", ">", ">>", "history");

        String[] words = input.split("\\s+");

        if(commandList.contains(words[0])){
            commandName = words[0];
            for (int i = 1; i < words.length; i++) {
                args.add(words[i]);
            }
            return true;
        }
        if(words.length > 1){
            if(commandList.contains(words[0]+" "+words[1])){
                commandName = words[0]+" "+words[1];
                for (int i = 2; i < words.length; i++) {
                    args.add(words[i]);
                }
                return true;
            }
        }
        return false;
    }
    public String getCommandName(){
        return commandName;
    }
    public List<String> getArgs(){
        return args;
    }
}