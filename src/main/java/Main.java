import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Array of built-in commands
        String[] cmd = {"echo", "exit", "type","pwd"};

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
            else if(input.equals("pwd")){
                String currdir=System.getProperty("user.dir");
                System.out.println(currdir);
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
                try {
                    // Split input into command and arguments
                    String[] inputParts = input.split(" ");
                    ProcessBuilder processBuilder = new ProcessBuilder(inputParts);
                    processBuilder.redirectErrorStream(true); // Combine stdout and stderr

                    // Start the process and capture output
                    Process process = processBuilder.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;

                    // Print each line of the process output
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }

                    // Wait for the process to finish
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        System.out.println("Command exited with code " + exitCode);
                    }

                } catch (Exception e) {
                    // Output for invalid commands
                    String[] inputParts = input.split(" ");
                    System.out.println(inputParts[0] + ": command not found");
                }
            }
        }
    }
}
