package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.strategies.EasyMoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.strategies.UserMoveStrategy;
import edu.wsiiz.project.tictactoe.menu.ChooseLevel;
import edu.wsiiz.project.tictactoe.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PlayGame implements GameStrategy {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private  DatabaseService databaseService;

    @Override
    public void execute() {
        startGame(level(), chooseSign());
    }
    private  void startGame(Level level, Sign sign) {
        GameBoard board = new GameBoard();
        board.displayBoard();
        MoveStrategy userPlayer = new UserMoveStrategy(board, sign);
        //todo add creation for the strategies
        MoveStrategy computerPlayer = //selectStrategy(level, SignOperator.getOpponentSign(sign) , board);
                new EasyMoveStrategy(board, SignOperator.getOpponentSign(sign));
        play(userPlayer, computerPlayer, sign);
    }


    private void play(MoveStrategy userPlayer, MoveStrategy computerPlayer, Sign sign) {
        if (sign == Sign.X) {
            play(userPlayer, computerPlayer);
        } else {
            play(computerPlayer, userPlayer);
        }
    }


    private void play(MoveStrategy firstPlayer, MoveStrategy secondPlayer) {
        while (!checkWin(firstPlayer, secondPlayer)) {
            firstPlayer.makeMove();
            if (checkTie(secondPlayer.getBoard().getBoard())) {
                saveResult(GameResult.DRAW);
                System.out.println("It's a tie!");
                break;
            }
            secondPlayer.makeMove();
            if (checkTie(secondPlayer.getBoard().getBoard())) {
                saveResult(GameResult.DRAW);
                System.out.println("It's a tie!");
                break;
            }
        }
    }

    private boolean checkWin(MoveStrategy firstPlayer, MoveStrategy secondPlayer) {
        int winFirstPlayer = SignOperator.getIntSign(firstPlayer.getSign()) * 3;
        if (ResultCalculator.calculateAll(firstPlayer.getBoard().getBoard(), winFirstPlayer)) {
           System.out.println(firstPlayer.getSign() + " wins!");
           saveResult(checkUserResult(firstPlayer, secondPlayer, true));
           return true;
        }
        int winSecondPlayer = SignOperator.getIntSign(secondPlayer.getSign()) * 3;
        if (ResultCalculator.calculateAll(secondPlayer.getBoard().getBoard(), winSecondPlayer)) {
            System.out.println(secondPlayer.getSign() + " wins!");
            saveResult(checkUserResult(firstPlayer, secondPlayer, false));
            return true;
        }
        return false;
    }

    private boolean checkTie(char[][] board) {
        return ResultCalculator.calculateTie(board);
    }

    private GameResult checkUserResult(MoveStrategy firstPlayer, MoveStrategy secondPlayer, boolean firstWon) {
        return firstWon? firstPlayer instanceof UserMoveStrategy? GameResult.WIN : GameResult.LOOSE :
                secondPlayer instanceof UserMoveStrategy? GameResult.WIN : GameResult.LOOSE;
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
