import businesslogic.SensorReadingRange;
import csv.SensorData;
import datatypes.SensorReading;
import datatypes.Timestamp;
import java.util.Random;
import java.util.Scanner;
import logger.Logger;

public class Main {
    static Logger logger = new Logger();
    static SensorReadingRange sensorRange = new SensorReadingRange();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean endProgram = false;
        SensorData csvData = new SensorData("D:\\Curtin_university\\semester 1\\COMP1007\\Assignment\\src\\testData2.csv");
        String state = "main";  // use this state to control which menu to display and when to reset dataRange;
        String dataRange = "";

        while (!endProgram) {
            if (state.equals("main")) {
                logger.logAndDisplay("\nWelcome to the Smart greenhouse Monitoring System."
                    + '\n' + "Please select an option:"
                    + '\n' + "1. Statistics for the entire greenhouse"
                    + '\n' + "2. Statistics by zone"
                    + '\n' + "3. Statistics by sensor type"
                    + '\n' + "4. Add data readings"
                    + '\n' + "5. Delete data readings"
                    + '\n' + "6. Exit program\n"
                );
                switch (getValidInputInRange(scanner, 1, 6)) {
                    case 1 -> {dataRange = "all"; state = "sub";}
                    case 2 -> {dataRange = selectZone(scanner); state = "sub";}
                    case 3 -> {dataRange = selectSensorType(scanner); state = "sub";}
                    case 4 -> {addData(scanner, csvData);}
                    case 5 -> {deleteData(scanner, csvData);}
                    case 6 -> {endProgram = true;}
                }    

            } else if (state.equals("sub")) {
                logger.logAndDisplay("\nSelect a statistic:"
                    + '\n' + "1. Total number of readings"
                    + '\n' + "2. Average value"
                    + '\n' + "3. Minimum value"
                    + '\n' + "4. Maximum value"
                    + '\n' + "5. Number of readings outside safe range"
                    + '\n' + "6. Percentage of readings outside safe range"
                    + '\n' + "7. All statistics"
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
                logger.logAndDisplay("Enter your choice: ");
                choice = scanner.nextInt();
                logger.log("" + choice + '\n');
                
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    logger.logAndDisplay("Invalid input. Selection must be within range\n");
                }
            } catch (Exception e) {
                logger.logAndDisplay("Invalid input. Must be an integer.\n");
                scanner.nextLine();
            }
        }
    }

    // ---------- MAIN MENU OPTIONS -------------------------
    public static String selectZone(Scanner scanner) {
        logger.logAndDisplay("\nSelect on of the following zones:"
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
        logger.logAndDisplay("\nSelect sensor type: "
            + '\n' + "1. Temperature"
            + '\n' + "2. Humidity"
            + '\n' + "3. Soil Moiture"
            + '\n' + "4. Light\n"
        );
        return switch (getValidInputInRange(scanner, 1, 4)) {
            case 1 -> "temperature";
            case 2 -> "humidity";
            case 3 -> "soilMoisture";
            default -> "light";
        };
    }

    public static void addData(Scanner scanner, SensorData csvData) {
        logger.logAndDisplay("\nAdd data:\n");
        String type = selectSensorType(scanner);
        String zone = selectZone(scanner);

        double value = 0.0;
        boolean isValid = false;
        do { 
            try {
                
                logger.logAndDisplay("\nSensorReading value: ");
                value = scanner.nextDouble();
                isValid = true;
            } catch (Exception e) {
                logger.logAndDisplay("\nInvalid input for value");
                scanner.nextLine();
            }
        } while (!isValid);        

        isValid = false;
        Timestamp timestamp = null;
        do {
            try {
                logger.logAndDisplay("Enter day: ");
                int day = scanner.nextInt(); 
                logger.logAndDisplay("Enter month: ");
                int month = scanner.nextInt();
                logger.logAndDisplay("Enter year: ");
                int year = scanner.nextInt();
                logger.logAndDisplay("Enter hour: ");
                int hour = scanner.nextInt();
                logger.logAndDisplay("Enter minute: ");
                int minute = scanner.nextInt();

                timestamp = new Timestamp(day, month, year, hour, minute);
                isValid = true;
            } catch (IllegalArgumentException e) {
                logger.logAndDisplay('\n' + e.getMessage() + "\n");
                scanner.nextLine();
            } catch (Exception e2) {
                logger.logAndDisplay("Input must be an int\n");
                scanner.nextLine();
            }
        } while(!isValid);

        Random idDigit = new Random();
        String sensorID = switch(type) {
            case "temperature" -> "TMP";
            case "soilMoisture" -> "SOIL";
            case "humidity" -> "HMD";
            case "light" -> "LGT";
            default -> "";
        } + (idDigit.nextInt(900) + 100);

        csvData.addData(sensorID, type, zone, value, timestamp);

    }

    public static void deleteData(Scanner scanner, SensorData csvData) {
        boolean validIdFormat = false;
        String id = "";
        do { 
            logger.logAndDisplay("\nDelete sensor reading:");
            logger.logAndDisplay("\nEnter sensor ID: ");
            id = scanner.next();
            logger.log(id + '\n'); // show input in log file
            try {
                id = SensorReading.validateSensorID(id);
                validIdFormat = true;
            } catch (IllegalArgumentException e) {
                logger.logAndDisplay("Invalid sensor format: " + id);
                scanner.nextLine();
            }
        } while (!validIdFormat);

        try {
            csvData.deleteData(id);
        } catch (Exception e) {
            logger.logAndDisplay("\n"+ e.getMessage() + '\n');
        }
    }


    // ------------ SUB MENU ------------------------
    public static void subOp1TotalCount(String dataRange, SensorData csvData) {
        logger.logAndDisplay("\nTotal number of " + dataRange + " sensor readings is " + csvData.getTotalCount(dataRange) + '\n');
    }

    public static void subOp2MeanVal(String dataRange, SensorData csvData) {
        logger.logAndDisplay("\nThe average reading value of " + dataRange + " sensors is " + csvData.getMeanValue(dataRange) + '\n');
    }

    public static void subOp3MinVal(String dataRange, SensorData csvData) {
        try {
            logger.logAndDisplay("\nThe lowest sensor reading of " + dataRange + " sensors is " + csvData.getMinValue(dataRange) + '\n');
        } catch (Exception e) {
            logger.logAndDisplay("\nThere are no readings for " + dataRange + " sensors\n");
        }
    }

    public static void subOp4MaxVal(String dataRange, SensorData csvData) {
        try {
            logger.logAndDisplay("\nThe largest sensor reading of " + dataRange + " sensors is " + csvData.getMaxValue(dataRange) + '\n');
        } catch (Exception e) {
            logger.logAndDisplay("\nThere are no readings for " + dataRange + " sensors\n");
        }
    }

    public static void subOp5UnsafeCount(String dataRange, SensorData csvData) {
        logger.logAndDisplay("\n" + sensorRange.unsafeReadingCount(csvData, dataRange) + " sensors of " + dataRange + " are outside the safe region\n");
    }

    public static void subOp6UnsafePercentage(String dataRange, SensorData csvData) {
        logger.logAndDisplay("\n"+ sensorRange.unsafeReadingPercent(csvData, dataRange) + "% of the " + dataRange + " sensors are outside the safe region\n");
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