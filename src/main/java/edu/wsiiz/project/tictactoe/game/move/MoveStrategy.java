package edu.wsiiz.project.tictactoe.game.move;

import edu.wsiiz.project.tictactoe.game.Player;

public interface MoveStrategy {
    void makeMove(Player player);
    MoveStrategyName getMoveStrategyName();
}
