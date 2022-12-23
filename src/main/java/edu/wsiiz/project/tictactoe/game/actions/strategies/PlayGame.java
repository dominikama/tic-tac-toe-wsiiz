package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategiesFactory;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.menu.ChooseLevel;
import edu.wsiiz.project.tictactoe.util.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PlayGame implements GameStrategy {

    private static final Scanner SCANNER = new Scanner(System.in);
    private final DatabaseService databaseService;
    private final MoveStrategiesFactory moveStrategiesFactory;

    public PlayGame(DatabaseService databaseService, MoveStrategiesFactory moveStrategiesFactory) {
        this.databaseService = databaseService;
        this.moveStrategiesFactory = moveStrategiesFactory;
    }

    @Override
    public void execute() {
        startGame(level(), chooseSign());
    }
    private  void startGame(Level level, Sign sign) {
        GameBoard board = new GameBoard();
        Player userPlayer = new Player(board, sign, moveStrategiesFactory.findStrategy(MoveStrategyName.USER));
        Player computerPlayer = new Player(board, SignOperator.getOpponentSign(sign), selectStrategy(level));
        board.displayBoard();
        play(userPlayer, computerPlayer);
    }

    private MoveStrategy selectStrategy(Level level) {
        switch (level) {
            case EASY -> {return moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_EASY);}
            case MEDIUM -> {return moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_MEDIUM);}
            case HARD -> {return moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_HARD);}
            default -> throw new IllegalArgumentException("Level is wrong");
        }
    }


    private void play(Player userPlayer, Player computerPlayer) {
        if (userPlayer.getSign() == Sign.X) {
            playGame(userPlayer, computerPlayer);
        } else {
            playGame(computerPlayer, userPlayer);
        }
    }


    private void playGame(Player firstPlayer, Player secondPlayer) {
        while (!checkWin(firstPlayer, secondPlayer)) {
            firstPlayer.makeMove();
            if (checkTie(secondPlayer.getGameBoard().getBoard())) {
                saveResult(GameResult.DRAW);
                System.out.println("It's a tie!");
                break;
            }
            secondPlayer.makeMove();
            if (checkTie(secondPlayer.getGameBoard().getBoard())) {
                saveResult(GameResult.DRAW);
                System.out.println("It's a tie!");
                break;
            }
        }
    }

    private boolean checkWin(Player firstPlayer, Player secondPlayer) {
        int winFirstPlayer = SignOperator.getIntSign(firstPlayer.getSign()) * 3;
        if (ResultCalculator.calculateAll(firstPlayer.getGameBoard().getBoard(), winFirstPlayer)) {
           System.out.println(firstPlayer.getSign() + " wins!");
           saveResult(checkUserResult(firstPlayer.getMoveStrategy(), secondPlayer.getMoveStrategy(), true));
           return true;
        }
        int winSecondPlayer = SignOperator.getIntSign(secondPlayer.getSign()) * 3;
        if (ResultCalculator.calculateAll(secondPlayer.getGameBoard().getBoard(), winSecondPlayer)) {
            System.out.println(secondPlayer.getSign() + " wins!");
            saveResult(checkUserResult(firstPlayer.getMoveStrategy(), secondPlayer.getMoveStrategy(), false));
            return true;
        }
        return false;
    }

    private boolean checkTie(char[][] board) {
        return ResultCalculator.calculateTie(board);
    }

    private GameResult checkUserResult(MoveStrategy firstPlayer, MoveStrategy secondPlayer, boolean firstWon) {
        return firstWon? MoveStrategyName.USER == firstPlayer.getMoveStrategyName()? GameResult.WIN : GameResult.LOOSE :
                MoveStrategyName.USER == secondPlayer.getMoveStrategyName()? GameResult.WIN : GameResult.LOOSE;
    }

    private void saveResult(GameResult gameResult) {
        String username = getValidUsername();
        Optional.ofNullable(databaseService.findResultByUsername(username))
                .ifPresentOrElse(resultModel -> updateExistingUsernameResult(resultModel, gameResult),
                        () -> createNewGameRecord(username, gameResult));
    }
    private void createNewGameRecord(String username, GameResult gameResult) {
        ResultModel resultModel = databaseService.saveResultModel(username);
        updateExistingUsernameResult(resultModel, gameResult);
    }
    private void updateExistingUsernameResult(ResultModel resultModel, GameResult gameResult) {
        resultModel.setScore(resultModel.getScore() + getScoreToAddByResult(gameResult));
    }

    private static int getScoreToAddByResult(GameResult gameResult) {
        return switch (gameResult) {
            case WIN -> 1;
            case LOOSE -> -1;
            case DRAW -> 0;
        };
    }
    private static Level level() {
        String level = getValidInput("""
                Choose level:
                1 - Easy
                2 - Medium
                3 - Hard""", Constants.LEVEL_OPTIONS);
        return ChooseLevel.processLevel(level);
    }

    private static Sign chooseSign() {
        String sign = getValidInput("""
                Choose sign:
                X - you play first
                O - computer plays first""", Constants.SIGN_OPTIONS);
        return processSignInput(sign);
    }

    private static Sign processSignInput(String sign) {
        return switch (sign.toUpperCase()) {
            case "X" -> Sign.X;
            case "O" -> Sign.O;
            default -> throw new IllegalArgumentException("Level needs to be one of: EASY, HARD, MEDIUM");
        };
    }

    private static String getValidUsername() {
        String input;
        do {
            System.out.println("Enter username:  ");
            input = SCANNER.nextLine();
        } while (input == null || input.isEmpty());
        return input;
    }

    private static String getValidInput(String prompt, List<String> validValues) {
        String input;
        do {
            System.out.println(prompt);
            input = SCANNER.nextLine();
        } while (!isValidInput(validValues, input));
        return input;
    }

    private static boolean isValidInput(List<String> validValues, String input) {
        if (input != null && !input.isEmpty() && validValues.contains(input)) {
            return true;
        }
        System.out.println("You entered invalid input, you should type one of: " + String.join(", ", validValues) + "$\" and was: " + input);
        return false;
    }

}
