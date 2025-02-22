import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String currdir=System.getProperty("user.dir");

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
                System.out.println(currdir);
            }
            else if (input.startsWith("cd ")) {
                String path = input.substring(3).trim(); // Extract the path
                File targetDir = null;
            
                if (path.equals("~")) {
                    // Handle cd ~ to go to home directory
                    String homeDir = System.getenv("HOME");
                    if (homeDir == null) {
                        // Fallback to system property if HOME environment variable is not set
                        homeDir = System.getProperty("user.home");
                    }
            
                    // Debugging: Print the home directory to check the value
                    System.out.println("Home Directory: " + homeDir);
            
                    if (homeDir != null) {
                        targetDir = new File(homeDir);
                    } else {
                        System.out.println("cd: ~: No such file or directory");
                        continue; // Skip further processing
                    }
                } 
                else if (path.startsWith("./") || path.startsWith("../") || !path.startsWith("/")) {
                    // Handle relative paths like ./ and ../
                    targetDir = new File(currdir, path); // Resolve relative path against current directory
                } 
                else {
                    // Handle absolute paths
                    targetDir = new File(path);
                }
            
                // Check if the provided path is valid and is a directory
                if (targetDir.exists() && targetDir.isDirectory()) {
                    currdir = targetDir.getCanonicalPath(); // Change to canonical path (resolves any symbolic links)
                    System.setProperty("user.dir", currdir); // Update Java's working directory
                } else {
                    System.out.println("cd: " + path + ": No such file or directory");
                }
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
