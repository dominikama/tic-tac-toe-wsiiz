package edu.wsiiz.project.tictactoe.files.strategies;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public class XmlExportStrategy implements FileExport {

    private final XmlMapper xmlMapper;

    public XmlExportStrategy() {
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public void exportToFile(List<ResultModel> models) {
        Date date = new Date();
        String name = "result".concat(dateFormat.format(date)).concat(".xml");
        File file = new File(name);
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            xmlMapper.writeValue(out, models);
            System.out.println("Exported to file: " + name);
        }  catch (Exception e) {
            System.out.println("Error While writing XML " + e);
        }
    }

    @Override
    public FileExportStrategyName getFileExportStrategyName() {
        return FileExportStrategyName.XML;
    }
}
