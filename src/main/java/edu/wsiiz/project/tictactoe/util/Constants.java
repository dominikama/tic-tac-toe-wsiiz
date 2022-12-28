package edu.wsiiz.project.tictactoe.util;

import java.util.List;

public class Constants {
    public static final List<String> LEVEL_OPTIONS = List.of("1", "2", "3");
    public static final List<String> DATABASE_OPTIONS = List.of("1", "2");
    public static final List<String> SIGN_OPTIONS = List.of("X", "O", "x", "o");
    public static final List<String> ACTION_OPTIONS = List.of("1", "2", "3", "4");
    public static final List<String> FILE_STRATEGY_OPTIONS = List.of("1", "2", "3");

    public static final String LEVEL_PROMPT = """
                Choose level:
                1 - Easy
                2 - Medium
                3 - Hard""";
    public static final String ACTION_PROMPT = """
                    Choose action:
                    1. Play Game
                    2. Check result
                    3. Export result to file
                    4. Exit""";
    public static final String SIGN_PROMPT="""
                Choose sign:
                X - you play first
                O - computer plays first""";
    public static final String FILE_PROMPT="""
                Choose option:
                1 - export 10 best results
                2 - export all results""";
    public static final String DB_PROMPT="""
                Choose option:
                1 - check 10 best results
                2 - check all results""";

    public static final String FILE_STRATEGY_PROMPT="""
                Choose format:
                1 - csv
                2 - json
                3 - xml""";
}
