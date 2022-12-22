package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

import java.util.Random;

import static edu.wsiiz.project.tictactoe.game.ResultCalculator.getNumberOfColumn;
import static edu.wsiiz.project.tictactoe.game.ResultCalculator.getNumberOfRow;

public class MediumMoveStrategy implements MoveStrategy {

    private int _row;
    private int _column;
    private final GameBoard board;
    private final Sign sign;
    private final Random random = new Random();
    private boolean moveMade = false;

    public MediumMoveStrategy(GameBoard board, Sign sign) {
        this.board = board;
        this.sign = sign;
    }

    public void makeMove() {
        enterData();
        board.modifyElement(this._row, this._column, SignOperator.getCharSign(sign));
        board.displayBoard();
    }

    public GameBoard getBoard() {
        return this.board;
    }

    public Sign getSign() {
        return this.sign;
    }

    private void enterData() {
        moveMade = checkColumns() || (checkRows() || (checkFirstDiagnoal() || checkSecondDiagnoal()));
        if (!moveMade) {
            generateRandomMove();
        }
    }

    private boolean checkColumns() {
        if (ResultCalculator.calculateColumns(board.getBoard(), SignOperator.getIntSign(Sign.X) * 2 + SignOperator.getIntSign(Sign.SPACE)) ||
                ResultCalculator.calculateColumns(board.getBoard(), SignOperator.getIntSign(Sign.O) * 2 + SignOperator.getIntSign(Sign.SPACE))) {
            int numberOfColumn = getNumberOfColumn();
            for (int i = 0; i < 3; i++) {
                if (board.isEmpty(i, numberOfColumn)) {
                    this._row = i;
                    this._column = numberOfColumn;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRows() {
        if (ResultCalculator.calculateRows(board.getBoard(), SignOperator.getIntSign(Sign.X) * 2 + SignOperator.getIntSign(Sign.SPACE))
                || ResultCalculator.calculateRows(board.getBoard(), SignOperator.getIntSign(Sign.O) * 2 + SignOperator.getIntSign(Sign.SPACE))) {
            int numberOfRow = getNumberOfRow();
            for (int i = 0; i < 3; i++) {
                if (board.isEmpty(numberOfRow, i)) {
                    this._row = numberOfRow;
                    this._column = i;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFirstDiagnoal() {
        if (ResultCalculator.calculateDiagonalFirst(board.getBoard(), SignOperator.getIntSign(Sign.X) * 2 + SignOperator.getIntSign(Sign.SPACE))
                || ResultCalculator.calculateDiagonalFirst(board.getBoard(), SignOperator.getIntSign(Sign.O) * 2 + SignOperator.getIntSign(Sign.SPACE))) {
            for (int i = 0; i < 3; i++) {
                if (board.isEmpty(i, i)) {
                    this._row = i;
                    this._column = i;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkSecondDiagnoal() {
        if (ResultCalculator.calculateDiagonalSecond(board.getBoard(), SignOperator.getIntSign(Sign.X) * 2 + SignOperator.getIntSign(Sign.SPACE))
                || ResultCalculator.calculateDiagonalSecond(board.getBoard(), SignOperator.getIntSign(Sign.O) * 2 + SignOperator.getIntSign(Sign.SPACE))) {
            for (int i = 0; i < 3; i++) {
                if (board.isEmpty(i, 2 - i)) {
                    this._row = i;
                    this._column = 2 - i;
                    return true;
                }
            }
        }
        return false;
    }

    private void generateRandomMove() {
        boolean empty;
        do {
            randomCoordinates();
            empty = board.isEmpty(this._row, this._column);
        } while (!empty);
    }
    private void randomCoordinates() {
        this._row = random.nextInt(3);
        this._column = random.nextInt(3);
    }

}
