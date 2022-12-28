package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.springframework.stereotype.Component;

@Component
public class DeleteResult implements GameStrategy {

    private final DatabaseService databaseService;
    private final InputUtility inputUtility;

    public DeleteResult(DatabaseService databaseService, InputUtility inputUtility) {
        this.databaseService = databaseService;
        this.inputUtility = inputUtility;
    }

    @Override
    public void execute() {
        String username = inputUtility.getValidUsername();
        databaseService.deleteResultModel(username);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.DELETE_RESULT;
    }
}
