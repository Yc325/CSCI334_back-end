package com.csci334.ConferenceMagment.repository;

import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PaperRepository extends JpaRepository<Paper,Long> {

 Set<Paper> findByAuthors (User user);

 Set<Paper> findByReviewers (User user);

 @Query("select p from Paper p where p.status='Submitted'")
 Set<Paper> findSubmittedPapers();


}
