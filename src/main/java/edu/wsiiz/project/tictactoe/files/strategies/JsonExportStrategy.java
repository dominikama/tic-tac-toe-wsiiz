package edu.wsiiz.project.tictactoe.files.strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.files.FileExport;
import edu.wsiiz.project.tictactoe.files.FileExportStrategyName;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Component
public class JsonExportStrategy implements FileExport {
    private final ObjectMapper objectMapper;
    public JsonExportStrategy() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void exportToFile(List<ResultModel> models) {
        Date date = new Date();
        String name = "result".concat(dateFormat.format(date)).concat(".json");
        File file = new File(name);
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
               objectMapper.writeValue(out, models);
            System.out.println("Exported to file: " + name);
        }  catch (Exception e) {
            System.out.println("Error While writing JSON " + e);
        }
    }

    @Override
    public FileExportStrategyName getFileExportStrategyName() {
        return FileExportStrategyName.JSON;
    }
}
