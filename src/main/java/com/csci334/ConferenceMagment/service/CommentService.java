package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.Comment;
import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.dto.CommentDto;
import com.csci334.ConferenceMagment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PaperService paperService;


    public Comment save(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        Paper paper = paperService.getPaper(commentDto.getPaperId());
        comment.setComment(commentDto.getComment());
        comment.setReviewer(user);
        comment.setPaper(paper);
        comment.setPostedAt(LocalDate.from(LocalDateTime.now()));
        return commentRepository.save(comment);
    }

    public Set<Comment> getCommentsByPaperId(Long paperId) {
       Set<Comment> comments = commentRepository.findByPaperId(paperId);
       return comments;
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);

    }
}
