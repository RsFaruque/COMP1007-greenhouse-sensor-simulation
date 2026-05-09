import java.util.Scanner;

public class Main {
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

            int choice = getValidInput(scanner, menu, 1, 6);
        }

    }

    public static int getValidInput(Scanner scanner, String prompt, int start, int end) {
        System.out.println(prompt);
        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                
                if (choice >= start && choice <= end) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Selection must be within range");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. Must be an integer.");
            }
        }
    }


    public static void totalReadingsOption1(Scanner scanner) {
        
    }
    public static void avgValOption2(Scanner scanner) {
        
    }
    public static void minValOption3(Scanner scanner) {
        
    }
    public static void maxValOption4(Scanner scanner) {
        
    }
    public static void outsideSafeOption5(Scanner scanner) {
        
    }
    public static void percentOutsideSafeOption6(Scanner scanner) {
        
    }
    public static void allStatOption7(Scanner scanner) {
        
    }

}