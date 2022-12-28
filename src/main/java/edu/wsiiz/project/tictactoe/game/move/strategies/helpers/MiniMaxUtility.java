package edu.wsiiz.project.tictactoe.game.move.strategies.helpers;

import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.game.ResultCalculator.calculateAll;

@Component
public class MiniMaxUtility {

    public int minimax(char[][] array, int depth, boolean isMax, Sign sign, Sign opponentSign){
        int score = evaluate(array, sign, opponentSign);
        if (score == 10 || score == -10 || (score == 0 && ResultCalculator.calculateTie(array))) {
            return score;
        }

        Sign currentSign = isMax ? sign : opponentSign;
        int bestScore = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (array[i][j] == SignOperator.getCharSign(Sign.SPACE)) {
                    array[i][j] = SignOperator.getCharSign(currentSign);
                    int temporary = minimax(array, depth + 1, !isMax, sign, opponentSign);
                    array[i][j] = SignOperator.getCharSign(Sign.SPACE);
                    bestScore = isMax ? Math.max(bestScore, temporary) : Math.min(bestScore, temporary);
                }
            }
        }
        return isMax ? bestScore - depth : bestScore + depth;
    }

    public int evaluate(char[][] array, Sign sign, Sign opponentSign) {
        if (calculateAll(array, SignOperator.getIntSign(opponentSign) * 3)) {
            return -10;
        } else if (calculateAll(array, SignOperator.getIntSign(sign) * 3)) {
            return 10;
        } else {
            return 0;
        }
    }
}
