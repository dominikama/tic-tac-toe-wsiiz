package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

public class UserMoveStrategy implements MoveStrategy {
    private int row;
    private int column;
    private GameBoard gameBoard;
    private Sign playerSign;

    public UserMoveStrategy(GameBoard gameBoard, Sign playerSign) {
        this.gameBoard = gameBoard;
        this.playerSign = playerSign;
    }


    public void makeMove() {
        enterMoveData();
        gameBoard.modifyElement(this.row, this.column, SignOperator.getCharSign(playerSign));
        gameBoard.displayBoard();
    }

    private void enterMoveData() {
        boolean empty;
        do {
            this.row = getAndValidateCoordinate("row") - 1;
            this.column = getAndValidateCoordinate("column") - 1;
            empty = gameBoard.isEmpty(this.row, this.column);
            if (!empty) {
                System.out.println("This spot is already taken (row=" + this.row + ", column=" + this.column + "), try again");
            }
        } while (!empty);
    }

    public GameBoard getBoard() {
        return this.gameBoard;
    }

    public Sign getSign() {
        return this.playerSign;
    }

    private int getAndValidateCoordinate(String name) {
        String coordinate;
        do {
            System.out.println("Enter the " + name + ":");
            coordinate  = System.console().readLine();
        } while (!isValid(coordinate));

        return Integer.parseInt(coordinate);
    }

    private boolean isValid(String coordinate) {
        boolean valid = coordinate != null && !coordinate.isEmpty() && coordinate.matches("[0-9]+")
                && Integer.parseInt(coordinate) <= 3 && Integer.parseInt(coordinate) > 0;
        if (!valid) {
            System.out.println("Invalid data, coordinates should be between 1-3 and was " + coordinate);
        }
        return valid;
    }
}
