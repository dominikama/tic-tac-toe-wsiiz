package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.Sign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EasyMoveStrategyTest {

    MoveStrategy strategy = new EasyMoveStrategy();
    @Test
    public void testMakeMove() {
        // create a player and game board
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(gameBoard, Sign.X, strategy);
        player.setGameBoard(gameBoard);
        //make a move
        strategy.makeMove(player);

        // check that the move was made on the game board
        char[][] board = gameBoard.getBoard();
        boolean foundMove = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    foundMove = true;
                    break;
                }
            }
        }
        assertTrue(foundMove);
    }

    @Test
    public void testGetMoveStrategyName() {
        assertEquals(MoveStrategyName.COMP_EASY, strategy.getMoveStrategyName());
    }
}