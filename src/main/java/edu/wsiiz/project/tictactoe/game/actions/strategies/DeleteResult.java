package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.util.Constants.ADMIN_PASSWORD_PROMPT;
import static edu.wsiiz.project.tictactoe.util.Constants.ADMIN_USERNAME_PROMPT;

@Component
public class DeleteResult implements GameStrategy {

    @Value("${adminusername}")
    private String adminUsername;
    @Value("${adminpassword}")
    private String adminPassword;

    private final DatabaseService databaseService;
    private final InputUtility inputUtility;

    public DeleteResult(DatabaseService databaseService, InputUtility inputUtility) {
        this.databaseService = databaseService;
        this.inputUtility = inputUtility;
    }

    @Override
    public void execute() {
        String inputAdminUsername = inputUtility.getInput(ADMIN_USERNAME_PROMPT);
        String inputAdminPassword = inputUtility.getInput(ADMIN_PASSWORD_PROMPT);
        if (adminUsername.equals(inputAdminUsername) && adminPassword.equals(inputAdminPassword)) {
            String username = inputUtility.getValidUsername();
            databaseService.deleteResultModel(username);
        } else {
            System.out.println("You entered invalid admin data, try again");
        }
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.DELETE_RESULT;
    }

}
