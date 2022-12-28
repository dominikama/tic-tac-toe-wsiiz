package edu.wsiiz.project.tictactoe.game.move;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MoveStrategiesFactoryTest {

    @Mock
    private MoveStrategy easy;
    @Mock
    private MoveStrategy hard;

    private MoveStrategiesFactory moveStrategiesFactory;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(easy.getMoveStrategyName()).thenReturn(MoveStrategyName.COMP_EASY);
        when(hard.getMoveStrategyName()).thenReturn(MoveStrategyName.COMP_HARD);
        Set<MoveStrategy> moveStrategies = new HashSet<>();
        moveStrategies.add(easy);
        moveStrategies.add(hard);
        moveStrategiesFactory = new MoveStrategiesFactory(moveStrategies);
    }
    @Test
    void testFindStrategy_success() {
        assertSame(easy, moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_EASY));
        assertSame(hard, moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_HARD));
    }

    @Test
    void testNullWhenNotFind() {
        assertNull(moveStrategiesFactory.findStrategy(MoveStrategyName.COMP_MEDIUM));
    }
}