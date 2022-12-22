package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

import java.util.Random;

public class EasyMoveStrategy implements MoveStrategy {
    private int row;
    private int column;
    private GameBoard gameBoard;
    private Random random = new Random();
    private Sign playerSign;

    public EasyMoveStrategy(GameBoard gameBoard, Sign playerSign) {
        this.gameBoard = gameBoard;
        this.playerSign = playerSign;
    }

    public void makeMove() {
        enterMoveData();
        gameBoard.modifyElement(this.row, this.column, SignOperator.getCharSign(playerSign));
        gameBoard.displayBoard();
    }

    public GameBoard getBoard() {
        return this.gameBoard;
    }

    public Sign getSign() {
        return this.playerSign;
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