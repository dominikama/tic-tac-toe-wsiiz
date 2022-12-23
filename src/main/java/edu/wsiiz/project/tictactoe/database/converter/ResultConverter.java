package edu.wsiiz.project.tictactoe.database.converter;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ResultConverter {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
    public ResultModel createNewModel(String username) {
       ResultModel resultModel = new ResultModel();
       resultModel.setUsername(username);
       resultModel.setScore(0);
       resultModel.setCreatedDate(dateFormat.format(new Date()));
       return resultModel;
    }
}
