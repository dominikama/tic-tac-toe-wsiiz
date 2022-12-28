package edu.wsiiz.project.tictactoe.game;

import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

public class GameBoard {
    private static final int BOARD_SIZE = 3;
    private char[][] board;

    public char[][] getBoard() {
        return board;
    }

    public GameBoard() {
        createEmptyBoard();
    }

    public void displayBoard() {
        System.out.println("---------");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public void modifyElement(int row, int column, char value) {
        board[row][column] = value;
        System.out.println("Making move " + (row + 1) + ", " + (column + 1));
    }

    public boolean isEmpty(int row, int column) {
        return SignOperator.getIntSign(Sign.SPACE) == board[row][column];
    }

    private void createEmptyBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = SignOperator.getCharSign(Sign.SPACE); // filling array with spaces.
            }
        }
    }
}
