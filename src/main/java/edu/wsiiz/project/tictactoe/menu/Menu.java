package edu.wsiiz.project.tictactoe.menu;


import edu.wsiiz.project.tictactoe.game.actions.GameActionsFactory;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.game.actions.GameActionName.*;
import static edu.wsiiz.project.tictactoe.util.Constants.ACTION_OPTIONS;
import static edu.wsiiz.project.tictactoe.util.Constants.ACTION_PROMPT;
import static edu.wsiiz.project.tictactoe.util.InputUtility.getValidInput;


@Component
public class Menu {
    private final GameActionsFactory gameActionsFactory;

    public Menu(GameActionsFactory gameActionsFactory) {
        this.gameActionsFactory = gameActionsFactory;
    }

    public void displayMenu() {
        do {
            String choice = getValidInput(ACTION_PROMPT, ACTION_OPTIONS);
            processAction(choice);
        } while (true);
    }

    private void processAction(String choice) {
        switch (choice) {
            case "1" -> gameActionsFactory.findStrategy(PLAY).execute();
            case "2" -> gameActionsFactory.findStrategy(CHECK_RESULT).execute();
            case "3" -> gameActionsFactory.findStrategy(EXPORT_FILE).execute();
            case "4" -> gameActionsFactory.findStrategy(END).execute();
        }
    }



}

