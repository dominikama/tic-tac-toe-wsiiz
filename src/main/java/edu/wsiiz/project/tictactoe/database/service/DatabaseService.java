package edu.wsiiz.project.tictactoe.database.service;

import edu.wsiiz.project.tictactoe.database.converter.ResultConverter;
import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import edu.wsiiz.project.tictactoe.database.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    private final ResultRepository resultRepository;

    private final ResultConverter resultConverter;

    public DatabaseService(ResultRepository resultRepository, ResultConverter resultConverter) {
        this.resultRepository = resultRepository;
        this.resultConverter = resultConverter;
    }

    public ResultModel saveResultModel(String username) {
        return resultRepository.save(resultConverter.createNewModel(username));
    }

    public ResultModel updateResultScore(String username, int amount) {
        ResultModel resultModel = findResultByUsername(username);
        resultModel.setScore(resultModel.getScore() + amount);
        return resultRepository.save(resultModel);
    }

    public List<ResultModel> get10BestResults() {
        return resultRepository.findTop10ByOrderByScoreDesc();
    }

    public List<ResultModel> getAllResults() {
        return resultRepository.findAll();
    }

    public ResultModel findResultByUsername(String username) {
        return resultRepository.findByUsername(username);
    }
}
