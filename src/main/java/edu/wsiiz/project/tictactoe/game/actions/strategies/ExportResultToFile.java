package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.csv.CsvExportService;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ExportResultToFile implements GameStrategy {
    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private CsvExportService exportService;

    @Autowired
    private DatabaseService databaseService;


    @Override
    public void execute() {
        String action = getValidInput("""
                Choose option:
                1 - check 10 best results
                2 - check all results
                0 - exit""", Constants.DATABASE_OPTIONS);
        processRequest(action);
    }

    //TODO ADD IMPLEMENTATION
    private void processRequest(String action) {
        switch (action) {
            case "1" -> exportService.writeToCsv(databaseService.get10BestResults());
            case "2" -> exportService.writeToCsv(databaseService.getAllResults());
            case "0" -> System.out.println("NOT IMPL");
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
