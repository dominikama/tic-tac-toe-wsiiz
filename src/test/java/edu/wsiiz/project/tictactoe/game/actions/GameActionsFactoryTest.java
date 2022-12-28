package edu.wsiiz.project.tictactoe.game.actions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GameActionsFactoryTest {

    @Mock
    private GameStrategy endGame;
    @Mock
    private GameStrategy playGame;

    private GameActionsFactory gameActionsFactory;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(endGame.getActionName()).thenReturn(GameActionName.END);
        when(playGame.getActionName()).thenReturn(GameActionName.PLAY);
        Set<GameStrategy> gameStrategySet = new HashSet<>();
        gameStrategySet.add(endGame);
        gameStrategySet.add(playGame);
        gameActionsFactory = new GameActionsFactory(gameStrategySet);
    }
    @Test
    void testFindStrategy_success() {
        assertSame(endGame, gameActionsFactory.findStrategy(GameActionName.END));
        assertSame(playGame, gameActionsFactory.findStrategy(GameActionName.PLAY));
    }

    @Test
    void testNullWhenNotFind() {
        assertNull(gameActionsFactory.findStrategy(GameActionName.EXPORT_FILE));
    }
}