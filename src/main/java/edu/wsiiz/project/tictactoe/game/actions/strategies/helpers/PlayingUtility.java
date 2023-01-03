package edu.wsiiz.project.tictactoe.game.actions.strategies.helpers;

import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.GameResult;
import edu.wsiiz.project.tictactoe.util.Level;
import edu.wsiiz.project.tictactoe.util.Sign;
import org.springframework.stereotype.Component;

@Component
public class PlayingUtility {

    public Level processLevel(String userData) {
        if (userData != null && !userData.isEmpty()) {
            return switch (userData) {
                case "1" -> Level.EASY;
                case "2" -> Level.MEDIUM;
                case "3" -> Level.HARD;
                default -> throw new IllegalArgumentException("Level needs to be one of: EASY, HARD, MEDIUM");
            };
        }
        throw new IllegalArgumentException("User data needs to be present");
    }

    public Sign processSignInput(String sign) {
        return switch (sign.toUpperCase()) {
            case "X" -> Sign.X;
            case "O" -> Sign.O;
            default -> throw new IllegalArgumentException("Invalid sign");
        };
    }

    public GameResult checkUserResult(Player player) {
        return MoveStrategyName.USER == player.getMoveStrategy().getMoveStrategyName()? GameResult.WIN : GameResult.LOOSE;
    }

    public int getScoreToAddByResult(GameResult gameResult) {
        return switch (gameResult) {
            case WIN -> 1;
            case LOOSE -> -1;
            case DRAW -> 0;
        };
    }

}
