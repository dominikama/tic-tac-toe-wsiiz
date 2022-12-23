package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.SignOperator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class EasyMoveStrategy implements MoveStrategy {
    private int row;
    private int column;
    private GameBoard gameBoard;
    private final Random random = new Random();

    public void makeMove(Player player) {
        this.gameBoard = player.getGameBoard();
        enterMoveData();
        gameBoard.modifyElement(this.row, this.column, SignOperator.getCharSign(player.getSign()));
        gameBoard.displayBoard();
    }
    @Override
    public MoveStrategyName getMoveStrategyName() {
        return MoveStrategyName.COMP_EASY;
    }

    private void enterMoveData() {
        boolean empty;
        do {
            randomCoordinates();
            empty = gameBoard.isEmpty(this.row, this.column);
        } while (!empty);
    }

    private void randomCoordinates() {
        this.row = random.nextInt(3);
        this.column = random.nextInt(3);
    }
}