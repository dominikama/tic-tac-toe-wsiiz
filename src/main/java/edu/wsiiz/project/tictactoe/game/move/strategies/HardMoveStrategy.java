package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.game.move.strategies.helpers.MiniMaxUtility;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;
import org.springframework.stereotype.Component;

@Component
public class HardMoveStrategy implements MoveStrategy {
    private GameBoard board;
    private Sign sign;
    private Sign opponentSign;

    private final MiniMaxUtility miniMaxUtility;

    public HardMoveStrategy(MiniMaxUtility miniMaxUtility) {
        this.miniMaxUtility = miniMaxUtility;
    }

    public void makeMove(Player player) {
        setUpData(player);
        int bestScore = Integer.MIN_VALUE;
        int[] bestCoordinates = {-1, -1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == SignOperator.getCharSign(Sign.SPACE)) {
                    board.getBoard()[i][j] = SignOperator.getCharSign(sign);
                    int score = miniMaxUtility.minimax(board.getBoard(), 0, false, sign, opponentSign);
                    board.getBoard()[i][j] = SignOperator.getCharSign(Sign.SPACE);
                    if (score > bestScore) {
                        bestScore = score;
                        bestCoordinates = new int[] {i, j};
                    }
                }
            }
        }
        board.modifyElement(bestCoordinates[0], bestCoordinates[1], SignOperator.getCharSign(sign));
        board.displayBoard();
    }

    @Override
    public MoveStrategyName getMoveStrategyName() {
        return MoveStrategyName.COMP_HARD;
    }

    private void setUpData(Player player) {
        this.sign = player.getSign();
        this.opponentSign = SignOperator.getOpponentSign(sign);
        this.board = player.getGameBoard();
    }


}



