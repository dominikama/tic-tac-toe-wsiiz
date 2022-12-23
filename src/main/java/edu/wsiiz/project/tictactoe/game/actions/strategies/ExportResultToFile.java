package edu.wsiiz.project.tictactoe.game.actions.strategies;

import edu.wsiiz.project.tictactoe.csv.CsvExportService;
import edu.wsiiz.project.tictactoe.database.service.DatabaseService;
import edu.wsiiz.project.tictactoe.game.actions.GameActionName;
import edu.wsiiz.project.tictactoe.game.actions.GameStrategy;
import edu.wsiiz.project.tictactoe.util.InputUtility;
import org.springframework.stereotype.Component;

import static edu.wsiiz.project.tictactoe.util.Constants.DATABASE_OPTIONS;
import static edu.wsiiz.project.tictactoe.util.Constants.FILE_PROMPT;

@Component
public class ExportResultToFile implements GameStrategy {
    private final CsvExportService exportService;
    private final DatabaseService databaseService;


    public ExportResultToFile(CsvExportService exportService, DatabaseService databaseService) {
        this.exportService = exportService;
        this.databaseService = databaseService;
    }

    @Override
    public void execute() {
        String action = InputUtility.getValidInput(FILE_PROMPT, DATABASE_OPTIONS);
        processRequest(action);
    }

    @Override
    public GameActionName getActionName() {
        return GameActionName.EXPORT_FILE;
    }

    private void processRequest(String action) {
        switch (action) {
            case "1" -> exportService.writeToCsv(databaseService.get10BestResults());
            case "2" -> exportService.writeToCsv(databaseService.getAllResults());
        }
    }

}
