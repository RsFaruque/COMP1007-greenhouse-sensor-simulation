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
            + '\n' + "6. Exit program\n";
            logger.log(menu);

            int choice = getValidInputInRange(scanner, 1, 6);

            String dataRange = "";
            switch (choice) {
                case 1 -> dataRange = "all";
                case 2 -> dataRange = selectZone(scanner);
                case 3 -> dataRange = selectSensorType(scanner);
                case 4 -> addData();
                case 5 -> deleteData();
                case 6 -> endProgram = true;
            }

            if (!dataRange.equals("")) {
                logger.log("\nSelect a statistic:"
                    + '\n' + "1. Total number of readings"
                    + '\n' + "2. Average value"
                    + '\n' + "3. Minimum value"
                    + '\n' + "4. Maximum value"
                    + '\n' + "5. Number of readings outside safe range"
                    + '\n' + "6. Percentage of readings outside safe range"
                    + '\n' + "7. All statistics\n"
                );

                choice = getValidInputInRange(scanner, 1, 7);

                switch(choice) {
                    case 1 -> logger.log("Total data readings of " + dataRange + " is ");
                }
            }

        }

    }

    public static int getValidInputInRange(Scanner scanner, int start, int end) {
        int choice;
        while (true) {
            try {
                logger.log("Enter your choice: ");
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
        logger.log("\nSelect on of the following zones:"
            + '\n' + "1. ZoneA"
            + '\n' + "2. ZoneB"
            + '\n' + "3. ZoneC"
            + '\n'
        );
        
        return switch (getValidInputInRange(scanner, 1, 3)) {
            case 1 -> "ZoneA";
            case 2 -> "ZoneB";
            default -> "ZoneC";
        };
    }
}