package edu.wsiiz.project.tictactoe.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FileExportFactoryTest {

    @Mock
    private FileExport csvExport;
    @Mock
    private FileExport jsonExport;

    private FileExportFactory fileExportFactory;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        when(csvExport.getFileExportStrategyName()).thenReturn(FileExportStrategyName.CSV);
        when(jsonExport.getFileExportStrategyName()).thenReturn(FileExportStrategyName.JSON);
        Set<FileExport> fileExports = new HashSet<>();
        fileExports.add(csvExport);
        fileExports.add(jsonExport);
        fileExportFactory = new FileExportFactory(fileExports);
    }
    @Test
    void testFindStrategy_success() {
        assertSame(csvExport, fileExportFactory.findStrategy(FileExportStrategyName.CSV));
        assertSame(jsonExport, fileExportFactory.findStrategy(FileExportStrategyName.JSON));
    }

    @Test
    void testNullWhenNotFind() {
        assertNull(fileExportFactory.findStrategy(FileExportStrategyName.XML));
    }

}