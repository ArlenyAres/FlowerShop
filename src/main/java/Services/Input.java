package Services;

import Exception.InvalidInputException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        Scanner scannerString = new Scanner(System.in);
        Boolean isValid  = false;
        String userInput = "";
        while  (!isValid) {
            try {
                System.out.println(message);
                userInput = scannerString.nextLine();
                if (userInput.isEmpty()) {
                    throw new InvalidInputException("Services.Input cannot be empty.");
                }
                isValid = true;
            } catch (Exception e) {
                System.out.println("Format Error. Introduce a String.");
            }
        }
        return userInput;
    }

    public static int readInt(String message) {
        Scanner scanner = new Scanner(System.in);
        int intInput = 0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print(message);
            try {
                intInput = scanner.nextInt();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Services.Input Invalid! Format Error: Expected an Int format (e.g., 1).");
                scanner.next();
            }
        }
        return intInput;
    }

    public static double readDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        double doubleInput = 0.0;
        boolean isValid = false;

        while (!isValid) {
            System.out.print(message);
            try {
                doubleInput = scanner.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Services.Input Invalid! Format Error: Expected a Double format (e.g., 1.0).");
                scanner.next();
            }
        }
        return doubleInput;
    }

}