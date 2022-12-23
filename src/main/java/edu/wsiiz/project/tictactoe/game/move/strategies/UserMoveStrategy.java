package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.SignOperator;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserMoveStrategy implements MoveStrategy {
    private static final Scanner SCANNER = new Scanner(System.in);
    private int row;
    private int column;
    private GameBoard gameBoard;

    public void makeMove(Player player) {
        this.gameBoard = player.getGameBoard();
        enterMoveData();
        gameBoard.modifyElement(this.row, this.column, SignOperator.getCharSign(player.getSign()));
        gameBoard.displayBoard();
    }

    @Override
    public MoveStrategyName getMoveStrategyName() {
        return MoveStrategyName.USER;
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

    private int getAndValidateCoordinate(String name) {
        String coordinate;
        do {
            System.out.println("Enter the " + name + ":");
            coordinate  = SCANNER.nextLine();
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
