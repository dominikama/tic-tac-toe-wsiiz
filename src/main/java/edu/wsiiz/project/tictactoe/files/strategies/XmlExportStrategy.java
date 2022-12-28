package edu.wsiiz.project.tictactoe.files.strategies;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.files.FileExport;
import edu.wsiiz.project.tictactoe.files.FileExportStrategyName;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//todo refactor formatter
@Component
public class XmlExportStrategy implements FileExport {

    private XmlMapper xmlMapper;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

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
        }  catch (Exception e) {
            System.out.println("Error While writing JSON " + e);
        }
    }

    @Override
    public FileExportStrategyName getFileExportStrategyName() {
        return FileExportStrategyName.XML;
    }
}
