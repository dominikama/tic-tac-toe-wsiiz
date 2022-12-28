package edu.wsiiz.project.tictactoe.game.move.strategies;

import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.game.move.strategies.helpers.MiniMaxUtility;
import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class HardMoveStrategyTest {


    MiniMaxUtility mockUtility = mock(MiniMaxUtility.class);
    HardMoveStrategy hardMove = new HardMoveStrategy(mockUtility);
    @Test
    public void testMakeMove() {
        // Set up the mock object to return a specific value when the minimax method is called
        when(mockUtility.minimax(any(char[][].class), anyInt(), anyBoolean(), any(), any())).thenReturn(10);
        // Create a mock Player object
        Player mockPlayer = mock(Player.class);
        // Set up the mock object to return a specific value when the getSign method is called
        when(mockPlayer.getSign()).thenReturn(Sign.X);
        // Set up the mock object to return a mock GameBoard object when the getGameBoard method is called
        GameBoard mockBoard = mock(GameBoard.class);
        when(mockPlayer.getGameBoard()).thenReturn(mockBoard);

        // Set up the mock GameBoard object to return an empty board when the getBoard method is called
        char[][] emptyBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                emptyBoard[i][j] = SignOperator.getCharSign(Sign.SPACE);
            }
        }
        when(mockBoard.getBoard()).thenReturn(emptyBoard);

        // Call the makeMove method
        hardMove.makeMove(mockPlayer);

        // Verify that the board has been modified with the best move
        // Since the mock MiniMaxUtility always returns 10, the best move should be the first empty space on the board
        verify(mockBoard).modifyElement(0, 0, SignOperator.getCharSign(Sign.X));

        // Verify that the displayBoard method was called on the mock GameBoard object
        verify(mockBoard).displayBoard();
    }

    @Test
    public void testGetMoveStrategyName() {
        // Verify that the getMoveStrategyName method returns the correct value
        assertEquals(MoveStrategyName.COMP_HARD, hardMove.getMoveStrategyName());
    }

}