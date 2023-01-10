package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.GameBoard;
import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.ResultCalculator;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.game.actions.strategies.helpers.PlayingUtility;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategiesFactory;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.MoveStrategyName;
import edu.wsiiz.project.tictactoe.util.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static edu.wsiiz.project.tictactoe.util.Constants.*;

@Component
public class PlayGame implements GameStrategy {

    private final DatabaseService databaseService;
    private final MoveStrategiesFactory moveStrategiesFactory;

    private final InputUtility inputUtility;

    private final PlayingUtility playingUtility;

    public PlayGame(DatabaseService databaseService, MoveStrategiesFactory moveStrategiesFactory,
                    InputUtility inputUtility, PlayingUtility playingUtility) {
        this.databaseService = databaseService;
        this.moveStrategiesFactory = moveStrategiesFactory;
        this.inputUtility = inputUtility;
        this.playingUtility = playingUtility;
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
        return playingUtility.processLevel(level);
    }

    private Sign chooseSign() {
        String sign = inputUtility.getValidInput(SIGN_PROMPT, SIGN_OPTIONS);
        return playingUtility.processSignInput(sign);
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
            saveResult(GameResult.TIE);
            return true;
        }
        return false;
    }

    private boolean evaluateWin(Player player) {
        int winAmount = SignOperator.getIntSign(player.getSign()) * 3;
        if (ResultCalculator.calculateAll(player.getGameBoard().getBoard(), winAmount)) {
            System.out.println(player.getSign() + " wins!");
            saveResult(playingUtility.checkUserResult(player));
            return true;
        }
        return false;
    }

    private void saveResult(GameResult gameResult) {
        String username = inputUtility.getValidUsername();
        String password = inputUtility.getValidPassword();
        Optional.ofNullable(databaseService.findResultByUsername(username))
                .ifPresentOrElse(resultModel -> updateExistingUsernameResult(resultModel, password, gameResult),
                        () -> createNewGameRecord(username,password, gameResult));
    }
    private void createNewGameRecord(String username, String passsword, GameResult gameResult) {
        ResultModel resultModel = databaseService.saveResultModel(username, passsword);
        System.out.println("New user saved in our database!");
        updateExistingUsernameResult(resultModel, passsword, gameResult);
    }
    private void updateExistingUsernameResult(ResultModel resultModel, String password,  GameResult gameResult) {
        int i = 0;
        while (!resultModel.getPassword().equals(password) && i < 3) {
            int triesLeft = 3 - i;
            System.out.println("Your password is incorrect, please try again, left chances: " + triesLeft + "," +
                    " after that your result will be lost");
            password = inputUtility.getValidPassword();
            i++;
        }
        if (resultModel.getPassword().equals(password)) {
            databaseService.updateResultScore(resultModel.getUsername(), playingUtility.getScoreToAddByResult(gameResult));
        } else {
            System.out.println("Sorry you lost your score due to the wrong password");
        }
    }

}
