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
import edu.wsiiz.project.tictactoe.menu.ChooseLevel;
import edu.wsiiz.project.tictactoe.util.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static edu.wsiiz.project.tictactoe.util.Constants.*;


@Component
public class PlayGame implements GameStrategy {

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

    @Override
    public GameActionName getActionName() {
        return GameActionName.PLAY;
    }

    private  void startGame(Level level, Sign sign) {
        GameBoard board = new GameBoard();
        Player userPlayer = new Player(board, sign, moveStrategiesFactory.findStrategy(MoveStrategyName.USER));
        Player computerPlayer = new Player(board, SignOperator.getOpponentSign(sign), selectStrategy(level));
        board.displayBoard();
        play(userPlayer, computerPlayer);
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
        String username = InputUtility.getValidUsername();
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
        String level = InputUtility.getValidInput(LEVEL_PROMPT, LEVEL_OPTIONS);
        return ChooseLevel.processLevel(level);
    }

    private static Sign chooseSign() {
        String sign = InputUtility.getValidInput(SIGN_PROMPT, SIGN_OPTIONS);
        return processSignInput(sign);
    }

    private static Sign processSignInput(String sign) {
        return switch (sign.toUpperCase()) {
            case "X" -> Sign.X;
            case "O" -> Sign.O;
        };
    }


}
