package edu.wsiiz.project.tictactoe.game;

import org.junit.jupiter.api.Test;

import static edu.wsiiz.project.tictactoe.Constants.O_VALUE;
import static edu.wsiiz.project.tictactoe.Constants.X_VALUE;
import static edu.wsiiz.project.tictactoe.game.ResultCalculator.*;
import static org.junit.jupiter.api.Assertions.*;

class ResultCalculatorTest {

    @Test
    public void testCalculateRows_xInFirstRow() {
        char[][] array = {
                {'X', 'X', 'X'},
                {'O', 'O', 'X'},
                {'X', 'X', 'O'}
        };
        assertTrue(calculateRows(array, X_VALUE));
        assertEquals(getNumberOfRow(), 0);
    }

    @Test
    public void testCalculateRows_oInSecondRow() {
        char[][] array = {
                {'X', 'O', 'X'},
                {'O', 'O', 'O'},
                {'X', 'X', 'O'}
        };
        assertTrue(calculateRows(array, O_VALUE));
        assertEquals(getNumberOfRow(), 1);
    }

    @Test
    public void testCalculateRows_nonWin() {
        char[][] array = {
                {'X', 'O', 'X'},
                {'O', ' ', 'O'},
                {'X', 'X', 'O'}
        };
        assertFalse(calculateRows(array, X_VALUE));
        assertFalse(calculateRows(array, O_VALUE));
    }


    @Test
    public void testCalculateColumns_xInFirstColumn() {
        char[][] array = {
                {'X', ' ', 'O'},
                {'X', ' ', 'X'},
                {'X', 'O', 'O'}
        };
        assertTrue(calculateColumns(array, X_VALUE));
        assertEquals(getNumberOfColumn(), 0);
    }

    @Test
    public void testCalculateColumns_oInSecondColumn() {
        char[][] array = {
                {'X', 'O', 'X'},
                {' ', 'O', 'X'},
                {'X', 'O', ' '}
        };
        assertTrue(calculateColumns(array, O_VALUE));
        assertEquals(getNumberOfColumn(), 1);
    }

    @Test
    public void testCalculateColumns_nonWin() {
        char[][] array = {
                {'X', 'O', ' '},
                {' ', ' ', 'O'},
                {'X', 'X', 'O'}
        };
        assertFalse(calculateColumns(array, X_VALUE));
        assertFalse(calculateColumns(array, O_VALUE));
    }


    @Test
    void calculateDiagonalFirst_win() {
        char[][] array = {
                {'X', 'O', ' '},
                {' ', 'X', 'O'},
                {'X', 'O', 'X'}
        };
        assertTrue(calculateDiagonalFirst(array, X_VALUE));
    }

    @Test
    void calculateDiagonalFirst_nonWin() {
        char[][] array = {
                {'X', 'O', ' '},
                {' ', ' ', 'O'},
                {'X', 'X', 'O'}
        };
        assertFalse(calculateDiagonalFirst(array, X_VALUE));
        System.out.println(calculateDiagonalFirst(array, X_VALUE));
        assertFalse(calculateDiagonalFirst(array, O_VALUE));
    }


    @Test
    void calculateDiagonalSecond_win() {
        char[][] array = {
                {'X', 'X', 'O'},
                {' ', 'O', 'O'},
                {'O', 'X', 'X'}
        };
        assertTrue(calculateDiagonalSecond(array, O_VALUE));
    }

    @Test
    void calculateDiagonalSecond_nonWin() {
        char[][] array = {
                {'X', 'O', ' '},
                {' ', ' ', 'O'},
                {'X', 'X', 'O'}
        };
        assertFalse(calculateDiagonalSecond(array, X_VALUE));
        assertFalse(calculateDiagonalSecond(array, O_VALUE));
    }

    @Test
    public void testCalculateTie_noSpace() {
        char[][] array = {
                {'X', 'O', 'O'},
                {'O', 'X', 'X'},
                {'X', 'X', 'O'}
        };
        assertTrue(calculateTie(array));
    }

    @Test
    public void testCalculateTie_spaceInFirstRow() {
        char[][] array = {
                {'X', ' ', 'O'},
                {'O', 'X', 'X'},
                {'X', 'X', 'X'}
        };
        assertFalse(calculateTie(array));
    }

    @Test
    public void testCalculateTie_spaceInSecondColumn() {
        char[][] array = {
                {'X', 'O', 'O'},
                {'O', ' ', 'X'},
                {'X', 'X', 'X'}
        };
        assertFalse(calculateTie(array));
    }

}