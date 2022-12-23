package edu.wsiiz.project.tictactoe.menu;


import edu.wsiiz.project.tictactoe.game.actions.strategies.CheckResults;
import edu.wsiiz.project.tictactoe.game.actions.strategies.EndGame;
import edu.wsiiz.project.tictactoe.game.actions.strategies.ExportResultToFile;
import edu.wsiiz.project.tictactoe.game.actions.strategies.PlayGame;
import edu.wsiiz.project.tictactoe.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;


@Component
public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private PlayGame playGame;
    @Autowired
    private CheckResults checkResults;

    @Autowired
    private ExportResultToFile exportResultToFile;

    @Autowired
    private EndGame endGame;

    public void displayMenu() {
        do {
            String choice = getValidInput("""
                    Choose action:
                    1. Play Game
                    2. Check result
                    3. Export result to file
                    4. Exit""", Constants.ACTION_OPTIONS);
            processAction(choice);
        } while (true);
    }

    //todo add here the strategies for each action
    private void processAction(String choice) {
        switch (choice) {
            case "1" -> playGame.execute();
            case "2" -> checkResults.execute();
            case "3" -> exportResultToFile.execute();
            case "4" -> endGame.execute();

        }
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

