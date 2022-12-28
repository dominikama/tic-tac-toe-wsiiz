package edu.wsiiz.project.tictactoe.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class InputUtility {

    private final Scanner SCANNER = new Scanner(System.in);

    public  String getValidInput(String prompt, List<String> validValues) {
        String input;
        do {
            System.out.println(prompt);
            input = SCANNER.nextLine();
        } while (!isValidInput(validValues, input));
        return input;
    }

    public  String getValidUsername() {
        String input;
        do {
            System.out.println("Enter username:  ");
            input = SCANNER.nextLine();
        } while (input == null || input.isEmpty());
        return input;
    }

    private  boolean isValidInput(List<String> validValues, String input) {
        if (input != null && !input.isEmpty() && validValues.contains(input)) {
            return true;
        }
        System.out.println("You entered invalid input, you should type one of: " + String.join(", ", validValues) + "$\" and was: " + input);
        return false;
    }

}
