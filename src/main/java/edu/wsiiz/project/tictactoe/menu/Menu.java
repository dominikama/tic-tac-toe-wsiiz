package edu.wsiiz.project.tictactoe.menu;

import edu.wsiiz.project.tictactoe.game.actions.GameActions;
import edu.wsiiz.project.tictactoe.util.Constants;
import edu.wsiiz.project.tictactoe.util.Level;
import edu.wsiiz.project.tictactoe.util.Sign;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void displayMenu() {
        String choice = getValidInput("""
                Choose action:
                1. Play Game
                2. Exit""", Constants.ACTION_OPTIONS);
        processAction(choice);
    }

    private static void processAction(String choice) {
        if ("1".equals(choice)) {
            GameActions.play(level(), chooseSign());
        } else if ("2".equals(choice)) {
            GameActions.endGame();
        } else {
            throw new IllegalArgumentException("Invalid data");
        }
    }

    private static Level level() {
        String level = getValidInput("""
                Choose level:
                1 - Easy
                2 - Medium
                3 - Hard""", Constants.LEVEL_OPTIONS);
        return ChooseLevel.processLevel(level);
    }

    private static Sign chooseSign() {
        String sign = getValidInput("""
                Choose sign:
                X - you play first
                O - computer plays first""", Constants.SIGN_OPTIONS);
        return processSignInput(sign);
    }

    private static Sign processSignInput(String sign) {
        return switch (sign.toUpperCase()) {
            case "X" -> Sign.X;
            case "O" -> Sign.O;
            default -> throw new IllegalArgumentException("Level needs to be one of: EASY, HARD, MEDIUM");
        };
    }

    private static String getValidInput(String prompt, List<String> validValues) {
        String input;
        do {
            System.out.println(prompt);
            input = SCANNER.nextLine();
        } while (!isValidInput(validValues, input));
        return input;
    }

    private static boolean isValidInput(List<String> validValues, String input) {
        if (input != null && !input.isEmpty() && validValues.contains(input)) {
            return true;
        }
        System.out.println("You entered invalid input, you should type one of: " + String.join(", ", validValues) + "$\" and was: " + input);
        return false;
    }
}

