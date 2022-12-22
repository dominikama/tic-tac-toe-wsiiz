package edu.wsiiz.project.tictactoe.game;

import edu.wsiiz.project.tictactoe.util.Sign;
import edu.wsiiz.project.tictactoe.util.SignOperator;

public class ResultCalculator {
    private static int numberOfRow = 0;
    private static int numberOfColumn = 0;

    public static boolean calculateRows(char[][] array, int number) {
        for (int i = 0; i < 3; ++i) {
            int sum = 0;
            for (int j = 0; j < 3; ++j) {
                sum += array[i][j];
                if (sum == number) {
                    numberOfRow = i;// It will give the number of row in which sum is adequate.
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean calculateColumns(char[][] array, int number) {
        for (int j = 0; j < 3; ++j) {
            int sum = 0;
            for (int i = 0; i < 3; ++i) {
                sum += array[i][j];
                if (sum == number) {
                    numberOfColumn = j; // It will give the number of column in which sum is adequate.
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean calculateDiagonalFirst(char[][] array, int number) {
        int sum = 0;
        int i = 0;
        while (i < 3) {
            sum += array[i][i];
            if (sum == number) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    public static boolean calculateDiagonalSecond(char[][] array, int number) {
        int sum = 0;
        int i = 0;
        while (i < 3) {
            sum += array[i][2 - i];
            if (sum == number) {
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    public static boolean calculateAll(char[][] array, int number)
    {
        return calculateColumns(array, number) || calculateRows(array, number) || calculateDiagonalFirst(array, number)
                || calculateDiagonalSecond(array, number);
    }

    public static boolean calculateTie(char[][] array)
    {
        char spaceVal = SignOperator.getCharSign(Sign.SPACE);
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (array[i][j] == spaceVal)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static int getNumberOfRow()
    {
        return numberOfRow;
    }

    public static int getNumberOfColumn()
    {
        return numberOfColumn;
    }
}
