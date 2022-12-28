package edu.wsiiz.project.tictactoe.files.strategies;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.files.FileExport;
import edu.wsiiz.project.tictactoe.files.FileExportStrategyName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Component
public class CsvExportStrategy implements FileExport {
    @Override
    public void exportToFile(List<ResultModel> models) {
        Date date = new Date();
        String name = "result".concat(dateFormat.format(date)).concat(".csv");
        try (FileWriter file = new FileWriter(name);PrintWriter writer = new PrintWriter(file);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (ResultModel resultModel: models) {
                csvPrinter.printRecord(resultModel.getUsername(), resultModel.getScore(), resultModel.getCreatedDate());
            }
            System.out.println("Exported to file: " + name);
        } catch (IOException e) {
            System.out.println("Error While writing CSV " +  e);
        }
    }

    @Override
    public FileExportStrategyName getFileExportStrategyName() {
        return FileExportStrategyName.CSV;
    }
}
