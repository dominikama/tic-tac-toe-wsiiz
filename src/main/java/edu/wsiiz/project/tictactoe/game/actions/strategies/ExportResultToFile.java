package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.files.FileExport;
import edu.wsiiz.project.tictactoe.files.FileExportFactory;
import edu.wsiiz.project.tictactoe.files.FileExportStrategyName;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.util.Constants.*;

@Component
public class ExportResultToFile implements GameStrategy {
    private final FileExportFactory fileExportFactory;
    private final DatabaseService databaseService;

    private final InputUtility inputUtility;

    public ExportResultToFile(FileExportFactory fileExportFactory, DatabaseService databaseService,
                              InputUtility inputUtility) {
        this.fileExportFactory = fileExportFactory;
        this.databaseService = databaseService;
        this.inputUtility = inputUtility;
    }

    @Override
    public void execute() {
        String input = inputUtility.getValidInput(FILE_STRATEGY_PROMPT, FILE_STRATEGY_OPTIONS);
        chooseFileType(input);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.EXPORT_FILE;
    }


    private void chooseFileType(String input) {
        switch (input) {
            case "1" -> processRequest(fileExportFactory.findStrategy(FileExportStrategyName.CSV));
            case "2" -> processRequest(fileExportFactory.findStrategy(FileExportStrategyName.JSON));
            case "3" -> processRequest(fileExportFactory.findStrategy(FileExportStrategyName.XML));
        }
    }
    private void processRequest(FileExport fileExport) {
        String action = inputUtility.getValidInput(FILE_PROMPT, DATABASE_OPTIONS);
        switch (action) {
            case "1" -> fileExport.exportToFile(databaseService.get10BestResults());
            case "2" -> fileExport.exportToFile(databaseService.getAllResults());
        }
    }

}
