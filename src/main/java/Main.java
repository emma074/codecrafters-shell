import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // Uncomment this block to pass the first stage
<<<<<<< HEAD
        System.out.print("$ ");

=======
>>>>>>> 743c73c87856df9390b16a47e2eedea444bfb614
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("$ ");
        String input = scanner.nextLine();
<<<<<<< HEAD
        System.out.println(input +": command not found");
        System.out.flush();  
}
=======
        if(input.isEmpty()){
            continue;
        }
        else if(input.equals("exit 0")){
            System.exit(0);
        }
        else{

        System.out.println(input +": command not found");
        }


    }
>>>>>>> 743c73c87856df9390b16a47e2eedea444bfb614
}
}
