package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.Score;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.repository.PaperRepository;
import com.csci334.ConferenceMagment.repository.ScoreRepository;
import com.csci334.ConferenceMagment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaperRepository paperRepository;

    @Autowired
    PaperService paperService;

    public Score createScore(int score,Long paperId, User user){
        Score paperScore = new Score();
        User newUser = user;
        Paper paper = paperService.getPaper(paperId);
        paperScore.setPaper(paper);
        paperScore.setReviwer(user);
        paperScore.setScore(score);
        scoreRepository.save(paperScore);
        return paperScore;
    }

    public Integer getScore(User user,Long paperId){
        User newUser = user;
        Integer score = scoreRepository.findByReviwerandPaper(newUser.getId(),paperId);
        try{
            return score;
        }catch(Exception e ){
            return 0;
        }
    }

    public Double getAvarageScore(Long PaperId){
        Double score = scoreRepository.getAvarageByPaper(PaperId);
        try{
            return score;
        }catch(Exception e ){
            return 0.0;
        }
    }

    public Set<Score> getAllScoresByUser(User user){
        return scoreRepository.findByReviwer(user);
    }

    public Set<Score> getALlScoresByPaperId(Long paperId) {
        return scoreRepository.findbyPaperId(paperId);

    }
}
