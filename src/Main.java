import csv.SensorData;
import java.util.Scanner;
import logger.Logger;

public class Main {
    static Logger logger = new Logger();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean endProgram = false;
        SensorData csvData = new SensorData("D:\\Curtin_university\\semester 1\\COMP1007\\Assignment\\data.csv");
        String state = "main";  // use this state to control which menu to display and when to reset dataRange;
        String dataRange = "";

        while (!endProgram) {
            if (state.equals("main")) {
                logger.log("\nWelcome to the Smart greenhouse Monitoring System."
                    + '\n' + "Please select an option:"
                    + '\n' + "1. Statistics for the entire greenhouse"
                    + '\n' + "2. Statistics by zone"
                    + '\n' + "3. Statistics by sensor type"
                    + '\n' + "4. Add data readings"
                    + '\n' + "5. Delete data readings"
                    + '\n' + "6. Exit program\n"
                );
                switch (getValidInputInRange(scanner, 1, 6)) {
                    case 1 -> dataRange = "all";
                    case 2 -> dataRange = selectZone(scanner);
                    case 3 -> dataRange = selectSensorType(scanner);
                    case 4 -> addData(scanner);
                    case 5 -> deleteData(scanner);
                    case 6 -> endProgram = true;
                }    
                state = "sub";    // once range is selected, change state to sub(menu)

            } else if (state.equals("sub")) {
                logger.log("\nSelect a statistic:"
                    + '\n' + "1. Total number of readings"
                    + '\n' + "2. Average value"
                    + '\n' + "3. Minimum value"
                    + '\n' + "4. Maximum value"
                    + '\n' + "5. Number of readings outside safe range"
                    + '\n' + "6. Percentage of readings outside safe range"
                    + '\n' + "7. All statisticsn"
                    + '\n' + "8. Go back to main menu\n"
                );
                switch(getValidInputInRange(scanner, 1, 8)) {
                    case 1 -> subOp1TotalCount(dataRange, csvData);
                    case 2 -> subOp2MeanVal(dataRange, csvData);
                    case 3 -> subOp3MinVal(dataRange, csvData);
                    case 4 -> subOp4MaxVal(dataRange, csvData);
                    case 5 -> subOp5UnsafeCount(dataRange, csvData);
                    case 6 -> subOp6UnsafePercentage(dataRange, csvData);
                    case 7 -> subOp7AllStat(dataRange, csvData);
                    case 8 -> {state = "main"; dataRange = "";}  // if dataRange is not changed, the previous selection remains when the user selects 4 or 5 in the main menu instead of the 1-3
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

    // ---------- MAIN MENU OPTIONS -------------------------
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

    public static String selectSensorType(Scanner scanner) {
        logger.log("\nSelect sensor type: "
            + '\n' + "1. Temperature"
            + '\n' + "2. Humidity"
            + '\n' + "3. Soil Moiture"
            + '\n' + "4. Light"
        );
        return switch (getValidInputInRange(scanner, 1, 4)) {
            case 1 -> "temperature";
            case 2 -> "humidity";
            case 3 -> "soilMoisture";
            default -> "light";
        };
    }

    public static void addData(Scanner scanner) {

    }

    public static void deleteData(Scanner scanner) {

    }


    // ------------ SUB MENU ------------------------
    public static void subOp1TotalCount(String dataRange, SensorData csvData) {
        logger.log("Total number of " + dataRange + " sensor readings is " + csvData.getTotalCount(dataRange) + '\n');
    }

    public static void subOp2MeanVal(String dataRange, SensorData csvData) {
        logger.log("The average reading value of " + dataRange + " sensors is " + csvData.getMeanValue(dataRange) + '\n');
    }

    public static void subOp3MinVal(String dataRange, SensorData csvData) {
        logger.log("The lowest sensor reading of " + dataRange + " sensors is " + csvData.getMinValue(dataRange) + '\n');
    }

    public static void subOp4MaxVal(String dataRange, SensorData csvData) {
        logger.log("The highest sensor reading value of " + dataRange + " sensors is " + csvData.getMaxValue(dataRange) + '\n');
    }

    public static void subOp5UnsafeCount(String dataRange, SensorData csvData) {
        logger.log("Number of readings outside of safe range is " + csvData.getUnsafeReadingCount(dataRange) + '\n');
    }

    public static void subOp6UnsafePercentage(String dataRange, SensorData csvData) {
        logger.log("Percentage of unsafe readings in " + dataRange + ": " + csvData.getUnsafeReadingPercent(dataRange) + '\n');
        logger.log("Percentage of unsafe readings in " + dataRange + " compared to total: " + csvData.getUnsafeReadingPercent(dataRange) + '\n');
    }

    public static void  subOp7AllStat(String dataRange, SensorData csvData) {
        subOp1TotalCount(dataRange, csvData);
        subOp2MeanVal(dataRange, csvData);
        subOp3MinVal(dataRange, csvData);
        subOp4MaxVal(dataRange, csvData);
        subOp5UnsafeCount(dataRange, csvData);
        subOp6UnsafePercentage(dataRange, csvData);
    }
}