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

            if (input.equals("exit")) {
                System.exit(0); // Exit the shell
            }

            // Check if the input starts with "type"
            if (input.startsWith("type ")) {
                String cmdToCheck = input.substring(5).trim();
                boolean found = false;

                // Loop to check if the command is built-in
                for (int i = 0; i < cmd.length; i++) {
                    if (cmdToCheck.equals(cmd[i])) {
                        System.out.println(cmdToCheck + " is a shell builtin");
                        found = true;
                        break; // Exit the loop once found
                    }
                }

                if (!found) {
                    System.out.println(cmdToCheck + ": not found");
                }
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
}
