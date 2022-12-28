package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import org.springframework.stereotype.Component;

@Component
public class EndGame implements GameStrategy {

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.END;
    }
}
