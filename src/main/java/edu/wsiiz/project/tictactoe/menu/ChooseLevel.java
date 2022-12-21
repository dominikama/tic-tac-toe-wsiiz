package edu.wsiiz.project.tictactoe.menu;

import edu.wsiiz.project.tictactoe.util.Level;

public class ChooseLevel {
    public static Level processLevel(String userData) {
        if (userData != null && !userData.isEmpty()) {
            return switch (userData) {
                case "1" -> Level.EASY;
                case "2" -> Level.MEDIUM;
                case "3" -> Level.HARD;
                default -> throw new IllegalArgumentException("Level needs to be one of: EASY, HARD, MEDIUM");
            };
        }
        throw new IllegalArgumentException("User data needs to be present");
    }
}
