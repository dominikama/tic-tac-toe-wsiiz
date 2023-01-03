package edu.wsiiz.project.tictactoe.game.actions.strategies.helpers;

import edu.wsiiz.project.tictactoe.game.Player;
import edu.wsiiz.project.tictactoe.game.move.strategies.EasyMoveStrategy;
import edu.wsiiz.project.tictactoe.game.move.strategies.UserMoveStrategy;
import edu.wsiiz.project.tictactoe.util.GameResult;
import edu.wsiiz.project.tictactoe.util.Level;
import edu.wsiiz.project.tictactoe.util.Sign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlayingUtilityTest {

    private PlayingUtility playingUtility = new PlayingUtility();

    @Mock
    private Player player;

    @Test
    public void testProcessLevel_validInput() {
        // when
        Level level = playingUtility.processLevel("1");

        // then
        assertEquals(Level.EASY, level);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessLevel_invalidInput() {
        // when
        playingUtility.processLevel("4");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessLevel_nullInput() {
        // when
        playingUtility.processLevel(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessLevel_emptyInput() {
        // when
        playingUtility.processLevel("");
    }

    @Test
    public void testProcessSignInput_validInput() {
        // when
        Sign sign = playingUtility.processSignInput("x");

        // then
        assertEquals(Sign.X, sign);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessSignInput_invalidInput() {
        // when
        playingUtility.processSignInput("A");
    }

    @Test
    public void testCheckUserResult_userWins() {
        // given
        when(player.getMoveStrategy()).thenReturn(new UserMoveStrategy());

        // when
        GameResult gameResult = playingUtility.checkUserResult(player);

        // then
        assertEquals(GameResult.WIN, gameResult);
    }

    @Test
    public void testCheckUserResult_userLoses() {
        // given
        when(player.getMoveStrategy()).thenReturn(new EasyMoveStrategy());

        // when
        GameResult gameResult = playingUtility.checkUserResult(player);

        // then
        assertEquals(GameResult.LOOSE, gameResult);
    }

    @Test
    public void testGetScoreToAddByResult_win() {
        // when
        int score = playingUtility.getScoreToAddByResult(GameResult.WIN);

        // then
        assertEquals(1, score);
    }

    @Test
    public void testGetScoreToAddByResult_loose() {
        // when
        int score = playingUtility.getScoreToAddByResult(GameResult.LOOSE);

        // then
        assertEquals(-1, score);
    }
    @Test
    public void testGetScoreToAddByResult_draw() {
        // when
        int score = playingUtility.getScoreToAddByResult(GameResult.DRAW);

        // then
        assertEquals(0, score);
    }
}

