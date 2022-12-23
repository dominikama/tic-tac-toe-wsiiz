package edu.wsiiz.project.tictactoe.database.repository;

import edu.wsiiz.project.tictactoe.database.model.ResultModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<ResultModel, Long> {
    List<ResultModel> findTop10ByOrderByScoreDesc();
    ResultModel findByUsername(String username);
}
