package edu.wsiiz.project.tictactoe.game.move.strategies.helpers;

import edu.wsiiz.project.tictactoe.util.Sign;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiniMaxUtilityTest {

    MiniMaxUtility utility = new MiniMaxUtility();
    @Test
    public void testMinimax_0() {
        // create an empty 3x3 board
        char[][] board = new char[][]{
                {'X', 'O', 'X'},
                {'O', 'O', 'X'},
                {'X', ' ', 'O'}
        };
        int score = utility.minimax(board, 0, true, Sign.X, Sign.O);
        assertEquals(0, score);

    }
    @Test
    public void testMinimax_10() {
        // test with board that has a winning move for X
        char[][] board = new char[][]{
                {'X', 'X', ' '},
                {'O', 'O', ' '},
                {' ', ' ', ' '}
        };
        int score = utility.minimax(board, 0, true, Sign.X, Sign.O);
        assertEquals(10, score);
    }
    @Test
    public void testMinimax_minus10() {
        // test with board that has a winning move for O
        char[][]board = new char[][] {
                {'O', 'X', ' '},
                {'O', 'O', 'O'},
                {'X', 'X', 'O'}
        };
        int score = utility.minimax(board, 0, true, Sign.X, Sign.O);
        assertEquals(-10, score);
    }

    @Test
    public void testEvaluate_0() {
        // create an empty 3x3 board
        char[][] board = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };

        int score = utility.evaluate(board, Sign.X, Sign.O);
        assertEquals(0, score);
    }
    @Test
    public void testEvaluate_10() {
        // test with board that has a winning move for X
        char[][] board = new char[][]{
                {'X', 'X', 'X'},
                {'O', 'O', ' '},
                {' ', ' ', ' '}
        };
        MiniMaxUtility utility = new MiniMaxUtility();
        int score = utility.evaluate(board, Sign.X, Sign.O);
        assertEquals(10, score);
    }

    @Test
    public void testEvaluate_minus10() {

        // test with board that has a winning move for O
        char[][] board = new char[][] {
                {'O', 'X', ' '},
                {'O', 'O', 'O'},
                {'X', 'X', 'O'}
        };
        int score = utility.evaluate(board, Sign.X, Sign.O);
        assertEquals(-10, score);
    }
}