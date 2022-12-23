package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.util.Constants.DATABASE_OPTIONS;
import static edu.wsiiz.project.tictactoe.util.Constants.DB_PROMPT;

@Component
public class CheckResults implements GameStrategy {

    private final DatabaseService databaseService;

    public CheckResults(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void execute() {
        String action = InputUtility.getValidInput(DB_PROMPT, DATABASE_OPTIONS);
        processRequest(action);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.CHECK_RESULT;
    }

    private void processRequest(String action) {
        switch (action) {
            case "1" -> System.out.println(databaseService.get10BestResults());
            case "2" -> System.out.println(databaseService.getAllResults());
        }
    }

}
