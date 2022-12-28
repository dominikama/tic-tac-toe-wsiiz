package edu.wsiiz.project.tictactoe.game;

import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.util.Sign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Player {
    private GameBoard gameBoard;
    private Sign sign;

    private MoveStrategy moveStrategy;

    public void makeMove() {
        this.moveStrategy.makeMove(this);
    }

}
