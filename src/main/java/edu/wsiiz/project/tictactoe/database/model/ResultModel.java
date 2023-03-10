package edu.wsiiz.project.tictactoe.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "result")
public class ResultModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;
    @Column(name = "created")
    private String createdDate;
    @Column(name = "score")
    private Integer score;

    @Override
    public String toString() {
        return "username='" + username + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", score=" + score;
    }
}
