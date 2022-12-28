package edu.wsiiz.project.tictactoe.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class FileExportFactory {
    private Map<FileExportStrategyName, FileExport> fileExportStrategiesMap;

    @Autowired
    public FileExportFactory(Set<FileExport> fileExports) {
        createStrategy(fileExports);
    }

    public FileExport findStrategy(FileExportStrategyName strategyName) {
        return fileExportStrategiesMap.get(strategyName);
    }
    private void createStrategy(Set<FileExport> strategySet) {
        fileExportStrategiesMap = new HashMap<>();
        strategySet.forEach(fileStrategy -> fileExportStrategiesMap.put(fileStrategy.getFileExportStrategyName(), fileStrategy));
    }
}
