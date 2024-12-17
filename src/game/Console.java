package game;

import java.util.Arrays;
import java.util.Scanner;

public class Console {
    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public static String getString(String prompt, String[] allowedValues) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine();

            for (String s : allowedValues) {
                if (s.equalsIgnoreCase(value)) {
                    return value;
                }
            }

            System.out.println("Error! Value must be in this list: " + Arrays.toString(allowedValues) + ".");
        }
    }

    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid integer value.");
            }
        }
    }

    public static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error! Invalid numeric value.");
            }
        }
    }
}