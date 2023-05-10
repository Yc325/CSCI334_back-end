package com.csci334.ConferenceMagment.web;

import com.csci334.ConferenceMagment.domain.Score;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @PostMapping("/{score}/{paperId}")
    public ResponseEntity<?> createScore(@PathVariable int score, @PathVariable Long paperId, @AuthenticationPrincipal User user){
        Score newScore = scoreService.createScore(score,paperId,user);
        return ResponseEntity.ok(newScore);

    }

    @GetMapping("/{paperId}")
    public ResponseEntity<?> getScore(@PathVariable Long paperId, @AuthenticationPrincipal User user){
        Integer score = scoreService.getScore(user,paperId);
        return ResponseEntity.ok(score);
    }

    @GetMapping("avg/{paperId}")
    public ResponseEntity<?> getAvarageScore(@PathVariable Long paperId, @AuthenticationPrincipal User user){
        Double score = scoreService.getAvarageScore(paperId);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllScore( @AuthenticationPrincipal User user) {
        Set<Score> allScores = scoreService.getAllScoresByUser(user);
        return ResponseEntity.ok(allScores);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllScoreBypaper(@RequestParam Long paperId, @AuthenticationPrincipal User user){
        Set<Score> allScores = scoreService.getALlScoresByPaperId(paperId);
        return ResponseEntity.ok(allScores);
    }
}
