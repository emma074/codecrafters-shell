import java.io.File;
import java.io.IOException;
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
                String[] inputParts = input.split(" ");
                String command = inputParts[0];  // Command is the first word
                String[] arguments = new String[inputParts.length - 1]; // Remaining are arguments
                System.arraycopy(inputParts, 1, arguments, 0, arguments.length);

                String pathEnv = System.getenv("PATH");
                File executable = null;

                if (pathEnv != null && !pathEnv.isEmpty()) {
                    String[] paths = pathEnv.split(":");
                    for (String path : paths) {
                        File file = new File(path, command);
                        if (file.exists() && file.canExecute()) {
                            executable = file;
                            break;
                        }
                    }
                }

                if (executable == null) {
                    System.out.println(command + ": command not found");
                } else {
                    // Build the command with arguments
                    String[] cmdWithArgs = new String[arguments.length + 1];
                    cmdWithArgs[0] = executable.getAbsolutePath();
                    System.arraycopy(arguments, 0, cmdWithArgs, 1, arguments.length);

                    // Execute the command
                    ProcessBuilder processBuilder = new ProcessBuilder(cmdWithArgs);
                    processBuilder.inheritIO();  // Direct output to the current terminal

                    try {
                        Process process = processBuilder.start();
                        process.waitFor(); // Wait for the process to complete
                    } catch (IOException | InterruptedException e) {
                        System.out.println("Error executing command: " + e.getMessage());
                         }
                         System.out.println("Program was passed " + cmdWithArgs.length +"args (including program name).");
                         String basename = new File(cmdWithArgs[0]).getName();
                    System.out.println("Arg #0 (program name): " + basename);
                         for(int i=1;i<cmdWithArgs.length;i++){
                            System.out.println("Args  #" + i + ":" + cmdWithArgs[i]);
                         }
        
        }
    }
}
        }
    }
