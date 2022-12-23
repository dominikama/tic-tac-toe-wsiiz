package edu.wsiiz.project.tictactoe.database.converter;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import org.springframework.stereotype.Component;

@Component
public class ResultConverter {

    public ResultModel createNewModel(String username) {
       ResultModel resultModel = new ResultModel();
       resultModel.setUsername(username);
       resultModel.setScore(0);
       return resultModel;
    }
}
