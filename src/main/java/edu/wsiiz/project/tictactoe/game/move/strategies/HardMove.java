package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

import static edu.wsiiz.project.tictactoe.game.ResultCalculator.calculateAll;

public class HardMove implements MoveStrategy {
    private GameBoard board;
    private Sign sign;
    private Sign opponentSign;

    public HardMove(GameBoard board, Sign sign) {
        this.sign = sign;
        this.opponentSign = SignOperator.getOpponentSign(sign);
        this.board = board;
    }

    public void makeMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestCoordinates = {-1, -1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getBoard()[i][j] == SignOperator.getCharSign(Sign.SPACE)) {
                    board.getBoard()[i][j] = SignOperator.getCharSign(sign);
                    int score = minimax(board.getBoard(), 0, false);
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


    public GameBoard getBoard() {
        return this.board;
    }

    public Sign getSign() {
        return this.sign;
    }

    private int minimax(char[][] array, int depth, boolean isMax) {
        int score = evaluate(array);
        if (score == 10 || score == -10 || (score == 0 && ResultCalculator.calculateTie(array))) {
            return score;
        }

        Sign currentSign = isMax ? sign : opponentSign;
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (array[i][j] == SignOperator.getCharSign(Sign.SPACE)) {
                    array[i][j] = SignOperator.getCharSign(currentSign);
                    int temporary = minimax(array, depth + 1, !isMax);
                    array[i][j] = SignOperator.getCharSign(Sign.SPACE);
                    bestScore = isMax ? Math.max(bestScore, temporary) : Math.min(bestScore, temporary);
                }
            }
        }
        return isMax ? bestScore - depth : bestScore + depth;
    }

    private int evaluate(char[][] array) {
        if (calculateAll(array, SignOperator.getIntSign(opponentSign) * 3)) {
            return -10;
        } else if (calculateAll(array, SignOperator.getIntSign(sign) * 3)) {
            return 10;
        } else {
            return 0;
        }
    }

}



