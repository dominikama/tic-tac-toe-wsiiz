package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.Sign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediumMoveStrategyTest {

    MediumMoveStrategy mediumMoveStrategy = new MediumMoveStrategy();
    @Test
    void testEmptyBoardMove() {
        //create empty board
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(gameBoard, Sign.X, mediumMoveStrategy);
        mediumMoveStrategy.makeMove(player);
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
    void testBlockOpponentWin() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(gameBoard, Sign.X, mediumMoveStrategy);
        gameBoard.modifyElement(0, 0 , 'O');
        gameBoard.modifyElement(0, 1, 'O');
        mediumMoveStrategy.makeMove(player);
        assertEquals('X', gameBoard.getBoard()[0][2]);
    }

    @Test
    void testWinIf2Present() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(gameBoard, Sign.X, mediumMoveStrategy);
        gameBoard.modifyElement(0, 0 , 'X');
        gameBoard.modifyElement(0, 1, 'X');
        mediumMoveStrategy.makeMove(player);
        assertEquals('X', gameBoard.getBoard()[0][2]);
    }
    @Test
    public void testGetMoveStrategyName() {
        // Verify that the getMoveStrategyName method returns the correct value
        assertEquals(MoveStrategyName.COMP_MEDIUM, mediumMoveStrategy.getMoveStrategyName());
    }

}