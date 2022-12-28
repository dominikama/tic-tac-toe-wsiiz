package edu.wsiiz.project.tictactoe.files;


import edu.wsiiz.project.tictactoe.database.model.ResultModel;

import java.text.SimpleDateFormat;
import java.util.List;

public interface FileExport {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    void exportToFile(List<ResultModel> models);

    FileExportStrategyName getFileExportStrategyName();
}
