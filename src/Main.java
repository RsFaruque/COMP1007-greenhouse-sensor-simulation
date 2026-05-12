import java.util.Scanner;
import logger.Logger;

public class Main {
    static Logger logger = new Logger();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean endProgram = false;

        while (!endProgram) {
            String menu = "Welcome to the Smart greenhouse Monitoring System."
            + '\n' + "Please select an option:"
            + '\n' + "1. Statistics for the entire greenhouse"
            + '\n' + "2. Statistics by zone"
            + '\n' + "3. Statistics by sensor type"
            + '\n' + "4. Add data readings"
            + '\n' + "5. Delete data readings"
            + '\n' + "6. Exit program"
            + "\n\n" + "Your choice: ";

            // 

            int choice = getValidInput(scanner, menu, 1, 6);

            String dataRange = "all";
            switch (choice) {
                case 1 -> dataRange = "all";
                case 2 -> dataRange = ""
            }
        }

    }

    public static int getValidInput(Scanner scanner, String prompt, int start, int end) {
        logger.log(prompt);
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    logger.log("Invalid input. Selection must be within range\n");
                }
            } catch (IllegalArgumentException e) {
                logger.log("Invalid input. Must be an integer.");
            }
        }
    }


    public static String selectZone(Scanner scanner) {
        
    }
}