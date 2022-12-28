package edu.wsiiz.project.tictactoe.game.actions.strategies;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import edu.wsiiz.project.tictactoe.files.CsvExportService;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ExportResultToFileTest {
    @Mock
    private CsvExportService mockExportService;
    @Mock
    private DatabaseService mockDatabaseService;
    @Mock
    private InputUtility mockInputUtility;

    private ExportResultToFile exportResultToFile;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        exportResultToFile = new ExportResultToFile(mockExportService, mockDatabaseService, mockInputUtility);
    }
    @Test
    public void testExecute_best10() {
        when(mockInputUtility.getValidInput(anyString(), any())).thenReturn("1");
        exportResultToFile.execute();
        verify(mockExportService, times(1)).writeToCsv(any());
        verify(mockDatabaseService, times(1)).get10BestResults();
    }

    @Test
    public void testExecute_all() {
        when(mockInputUtility.getValidInput(anyString(), any())).thenReturn("2");
        exportResultToFile.execute();
        verify(mockExportService, times(1)).writeToCsv(any());
        verify(mockDatabaseService, times(1)).getAllResults();
    }

    @Test
    public void testGetActionName() {
        ExportResultToFile exportResultToFile = new ExportResultToFile(mockExportService, mockDatabaseService, mockInputUtility);
        assertEquals(GameActionName.EXPORT_FILE, exportResultToFile.getActionName());
    }
}
