package edu.wsiiz.project.tictactoe.util;

public class SignOperator {
    public static char getCharSign(Sign sign) {
        return switch (sign) {
            case X -> 'X';
            case O -> 'O';
            case SPACE -> ' ';
        };
    }

    public static int getIntSign(Sign sign) {
        return switch (sign) {
            case X -> 88;
            case O -> 79;
            case SPACE -> 32;
        };
    }

    public static Sign getOpponentSign(Sign currentPlayerSign) {
        return (currentPlayerSign == Sign.X) ? Sign.O : Sign.X;
    }
}
