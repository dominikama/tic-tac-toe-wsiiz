package edu.wsiiz.project.tictactoe.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    private GameBoard gameBoard = new GameBoard();
    @Test
    void modifyElement() {
        gameBoard.modifyElement(0, 0, 'X');
        assertEquals('X', gameBoard.getBoard()[0][0]);
    }

    @Test
    void isEmpty_true() {
        assertTrue(gameBoard.isEmpty(1,1));
    }
    @Test
    void isEmpty_false() {
        gameBoard.modifyElement(0, 0, 'X');
        assertFalse(gameBoard.isEmpty(0,0));
    }
}