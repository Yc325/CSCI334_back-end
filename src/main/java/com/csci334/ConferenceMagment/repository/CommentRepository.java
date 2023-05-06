package com.csci334.ConferenceMagment.repository;

import com.csci334.ConferenceMagment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select a from Comment a where a.paper.id = :paperId")
    Set<Comment> findByPaperId(Long paperId);
}
