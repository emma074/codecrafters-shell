import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Array of built-in commands
        String[] cmd = {"echo", "exit", "type"};

        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                continue; // Skip empty input
            }

            // Exit the shell if the user enters "exit"
            if (input.equals("exit")|| input.equals("exit 0")) {
                System.exit(0);
            }
            // Handle echo command
            else if (input.startsWith("echo ")) {
                System.out.println(input.substring(5));
            }
            // Handle type command
            else if (input.startsWith("type ")) {
                String cmdToCheck = input.substring(5).trim();
                boolean found = false;

                // Loop through the built-in commands to check if the command exists
                for (int i = 0; i < cmd.length; i++) {
                    if (cmdToCheck.equals(cmd[i])) {
                        System.out.println(cmdToCheck + " is a shell builtin");
                        found = true;
                        break;
                    }
                }
                if(!found){
                    String pathenv=System.getenv("PATH");
                    if(pathenv != null && !pathenv.isEmpty()){
                        String paths[]=pathenv.split(":");
                        for(String path:paths){
                            File file=new File(path,cmdToCheck);
                            if(file.exists() && file.canExecute()){
                                System.out.println(cmdToCheck + " is " +file.getAbsolutePath());
                                found=true;
                                break;
                            }
                        }
                    }
                    if(!found){
                        System.out.println(cmdToCheck + ": not found");
                    }
                }
            }
            else {
                // Here we simulate the program and argument output manually
                String[] inputParts = input.split(" ");
                String command = inputParts[0]; // Command is the first word
                String[] arguments = new String[inputParts.length - 1]; // Remaining are arguments
                System.arraycopy(inputParts, 1, arguments, 0, arguments.length);

                System.out.println("Program was passed " + inputParts.length + " args (including program name).");

                // Print only the command's basename (no path)
                System.out.println("Arg #0 (program name): " + command);

                for (int i = 0; i < arguments.length; i++) {
                    System.out.println("Arg #" + (i + 1) + ": " + arguments[i]);
                }
            }
        }
    }
}
