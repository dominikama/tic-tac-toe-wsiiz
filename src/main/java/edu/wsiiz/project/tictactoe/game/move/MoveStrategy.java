package edu.wsiiz.project.tictactoe.game.move;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.util.Sign;

public interface MoveStrategy {
    void makeMove();
    Sign getSign();

    GameBoard getBoard();
}
