package edu.wsiiz.project.tictactoe.game.move;

public class Move {
    private MoveStrategy strategy;

    public Move(MoveStrategy strategy) {
        this.strategy = strategy;
    }

    public void makeMove() {
        strategy.makeMove();
    }

}
