package com.csci334.ConferenceMagment.repository;

import com.csci334.ConferenceMagment.domain.Score;
import com.csci334.ConferenceMagment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScoreRepository extends JpaRepository<Score,Long> {

    @Query("select u.score from Score u where u.reviwer.id = ?1 and u.paper.id = ?2")
    Integer findByReviwerandPaper (Long userId, Long paperId);

    @Query("select avg(u.score) from Score u where u.paper.id = ?1")
    Double getAvarageByPaper(Long paperId);

    Set<Score> findByReviwer(User user);
}
