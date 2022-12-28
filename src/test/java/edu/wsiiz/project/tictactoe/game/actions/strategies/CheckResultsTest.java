package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckResultsTest {

    @Mock
    private DatabaseService mockDatabaseService;

    @Mock
    private InputUtility inputUtility;

    private CheckResults checkResults;
    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        checkResults = new CheckResults(mockDatabaseService, inputUtility);

    }
    @Test
    void testExecute_10Best() {
        when(inputUtility.getValidInput(anyString(), anyList())).thenReturn("1");
        checkResults.execute();
        verify(mockDatabaseService, times(1)).get10BestResults();
    }

    @Test
    void testExecute_all() {
        when(inputUtility.getValidInput(anyString(), anyList())).thenReturn("2");
        checkResults.execute();
        verify(mockDatabaseService, times(1)).getAllResults();
    }
    @Test
    void testGetActionName() {
        assertEquals(GameActionName.CHECK_RESULT, checkResults.getActionName());
    }
}