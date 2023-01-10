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

    public ResultModel saveResultModel(String username, String password) {
        return resultRepository.save(resultConverter.createNewModel(username, password));
    }

    public void deleteResultModel(String username) {
        ResultModel resultModel = resultRepository.findByUsername(username);
        if (resultModel != null) {
            resultRepository.deleteById(resultModel.getId());
            System.out.println("Deleted result: " + resultModel);
        } else {
            System.out.println("No user found with this username");
        }
    }

    public ResultModel updateResultScore(String username, int amount) {
        ResultModel resultModel = findResultByUsername(username);
        int newScore = resultModel.getScore() + amount;
        resultModel.setScore(newScore);
        System.out.println("User: " + username + ", score: " + newScore);
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
