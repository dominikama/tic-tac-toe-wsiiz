package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategiesFactory;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static edu.wsiiz.project.tictactoe.util.Constants.*;

//todo - refactor this since it is not testable
@Component
public class PlayGame implements GameStrategy {

    private final DatabaseService databaseService;
    private final MoveStrategiesFactory moveStrategiesFactory;

    private final InputUtility inputUtility;

    public PlayGame(DatabaseService databaseService, MoveStrategiesFactory moveStrategiesFactory,
                    InputUtility inputUtility) {
        this.databaseService = databaseService;
        this.moveStrategiesFactory = moveStrategiesFactory;
        this.inputUtility = inputUtility;
    }

    @Override
    public void execute() {
        GameBoard board = new GameBoard();
        Sign sign = chooseSign();
        Player userPlayer = new Player(board, sign, moveStrategiesFactory.findStrategy(MoveStrategyName.USER));
        Player computerPlayer = new Player(board, SignOperator.getOpponentSign(sign), selectStrategy(level()));
        board.displayBoard();
        play(userPlayer, computerPlayer);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.PLAY;
    }

    private Level level() {
        String level = inputUtility.getValidInput(LEVEL_PROMPT, LEVEL_OPTIONS);
        return processLevel(level);
    }

    private Sign chooseSign() {
        String sign = inputUtility.getValidInput(SIGN_PROMPT, SIGN_OPTIONS);
        return processSignInput(sign);
    }

    private Level processLevel(String userData) {
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

    private static Sign processSignInput(String sign) {
        return switch (sign.toUpperCase()) {
            case "X" -> Sign.X;
            case "O" -> Sign.O;
            default -> throw new IllegalArgumentException("Invalid sign");
        };
    }

    private MoveStrategy selectStrategy(Level level) {
        return switch (level) {
            case EASY -> moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_EASY);
            case MEDIUM ->moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_MEDIUM);
            case HARD -> moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_HARD);
        };
    }


    private void play(Player userPlayer, Player computerPlayer) {
        if (userPlayer.getSign() == Sign.X) {
            playGame(userPlayer, computerPlayer);
        } else {
            playGame(computerPlayer, userPlayer);
        }
    }


    private void playGame(Player firstPlayer, Player secondPlayer) {
        while (!evaluateWin(secondPlayer) && !checkTie(secondPlayer.getGameBoard().getBoard())) {
            firstPlayer.makeMove();
            if (checkTie(firstPlayer.getGameBoard().getBoard()) || evaluateWin(firstPlayer)) {
                break;
            }
            secondPlayer.makeMove();
        }
    }

    private boolean checkTie(char[][] board) {
        if (ResultCalculator.calculateTie(board)) {
            System.out.println("It's a tie!");
            saveResult(GameResult.DRAW);
            return true;
        }
        return false;
    }

    private boolean evaluateWin(Player player) {
        int winAmount = SignOperator.getIntSign(player.getSign()) * 3;
        if (ResultCalculator.calculateAll(player.getGameBoard().getBoard(), winAmount)) {
            System.out.println(player.getSign() + " wins!");
            saveResult(checkUserResult(player));
            return true;
        }
        return false;
    }
    private GameResult checkUserResult(Player player) {
        return MoveStrategyName.USER == player.getMoveStrategy().getMoveStrategyName()? GameResult.WIN : GameResult.LOOSE;
    }

    private void saveResult(GameResult gameResult) {
        String username = inputUtility.getValidUsername();
        Optional.ofNullable(databaseService.findResultByUsername(username))
                .ifPresentOrElse(resultModel -> updateExistingUsernameResult(resultModel, gameResult),
                        () -> createNewGameRecord(username, gameResult));
    }
    private void createNewGameRecord(String username, GameResult gameResult) {
        ResultModel resultModel = databaseService.saveResultModel(username);
        System.out.println("New user saved in our database!");
        updateExistingUsernameResult(resultModel, gameResult);
    }
    private void updateExistingUsernameResult(ResultModel resultModel, GameResult gameResult) {
        databaseService.updateResultScore(resultModel.getUsername(), getScoreToAddByResult(gameResult));
    }

    private int getScoreToAddByResult(GameResult gameResult) {
        return switch (gameResult) {
            case WIN -> 1;
            case LOOSE -> -1;
            case DRAW -> 0;
        };
    }
}
