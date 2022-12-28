package edu.wsiiz.project.tictactoe.game.actions.strategies;

import static edu.wsiiz.project.tictactoe.Constants.FILE_PROMPT;
import static edu.wsiiz.project.tictactoe.Constants.FILE_STRATEGY_PROMPT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.wsiiz.project.tictactoe.files.FileExportFactory;
import edu.wsiiz.project.tictactoe.files.strategies.CsvExportStrategy;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.files.strategies.JsonExportStrategy;
import edu.wsiiz.project.tictactoe.files.strategies.XmlExportStrategy;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ExportResultToFileTest {
    @Mock
    private FileExportFactory mockExportService;
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
    public void testExecute_best10_whenJSON() {
        JsonExportStrategy exportStrategy = mock(JsonExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("2");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("1");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).get10BestResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }
    @Test
    public void testExecute_best10_whencCSV() {
        CsvExportStrategy exportStrategy = mock(CsvExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("1");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("1");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).get10BestResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }
    @Test
    public void testExecute_best10_whenXML() {
        XmlExportStrategy exportStrategy = mock(XmlExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("3");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("1");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).get10BestResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }

    @Test
    public void testExecute_allWhenJSON() {
        JsonExportStrategy exportStrategy = mock(JsonExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("2");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("2");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).getAllResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }
    @Test
    public void testExecute_allWhenCSV() {
        CsvExportStrategy exportStrategy = mock(CsvExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("1");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("2");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).getAllResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }
    @Test
    public void testExecute_allWhenXML() {
            XmlExportStrategy exportStrategy = mock(XmlExportStrategy.class);
        when(mockInputUtility.getValidInput(eq(FILE_STRATEGY_PROMPT), any())).thenReturn("3");
        when(mockInputUtility.getValidInput(eq(FILE_PROMPT), any())).thenReturn("2");
        when(mockExportService.findStrategy(any())).thenReturn(exportStrategy);
        exportResultToFile.execute();
        verify(mockExportService, times(1)).findStrategy(any());
        verify(mockDatabaseService, times(1)).getAllResults();
        verify(exportStrategy, times(1)).exportToFile(any());
    }

    @Test
    public void testGetActionName() {
        ExportResultToFile exportResultToFile = new ExportResultToFile(mockExportService, mockDatabaseService, mockInputUtility);
        assertEquals(GameActionName.EXPORT_FILE, exportResultToFile.getActionName());
    }
}
