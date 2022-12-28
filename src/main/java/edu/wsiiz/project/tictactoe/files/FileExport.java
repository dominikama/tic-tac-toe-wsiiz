package edu.wsiiz.project.tictactoe.files;


import edu.wsiiz.project.tictactoe.database.model.ResultModel;

import java.util.List;

public interface FileExport {

    void exportToFile(List<ResultModel> models);

    FileExportStrategyName getFileExportStrategyName();
}
