package edu.wsiiz.project.tictactoe.csv;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CsvExportService {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss") ;
    public void writeToCsv(List<ResultModel> models) {
        Date date = new Date() ;
        String name = "result".concat(dateFormat.format(date)).concat(".csv");
        try (FileWriter file = new FileWriter(name);PrintWriter writer = new PrintWriter(file);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            for (ResultModel resultModel: models) {
                csvPrinter.printRecord(resultModel.getUsername(), resultModel.getScore(), resultModel.getCreatedDate());
            }
        } catch (IOException e) {
            System.out.println("Error While writing CSV " +  e);
        }
    }
}
