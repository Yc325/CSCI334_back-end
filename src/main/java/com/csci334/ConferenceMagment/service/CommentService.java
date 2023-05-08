package com.csci334.ConferenceMagment.service;

import com.csci334.ConferenceMagment.domain.Comment;
import com.csci334.ConferenceMagment.domain.builderPattern.BuildNotification;
import com.csci334.ConferenceMagment.domain.builderPattern.CommentNotification;
import com.csci334.ConferenceMagment.domain.builderPattern.ConstractorNotification;
import com.csci334.ConferenceMagment.domain.builderPattern.Notification;
import com.csci334.ConferenceMagment.domain.Paper;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.dto.CommentDto;
import com.csci334.ConferenceMagment.repository.CommentRepository;
import com.csci334.ConferenceMagment.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PaperService paperService;

    @Autowired
    private NotificationRepository notificationRepository;


    public Comment save(CommentDto commentDto, User user) {
        Comment comment = new Comment();
        Paper paper = paperService.getPaper(commentDto.getPaperId());
        comment.setComment(commentDto.getComment());
        comment.setReviewer(user);
        comment.setPaper(paper);
        comment.setPostedAt(LocalDate.from(LocalDateTime.now()));


        // Send notification

        List<User> authors = paper.getAuthors();
        int sizeAuthors = authors.size();

        for(int i = 0; i<sizeAuthors; i++){
            User newUser = authors.get(i);
            BuildNotification commentNotification = new CommentNotification(newUser,comment.getComment(),paper,user);
            ConstractorNotification notificationComment = new ConstractorNotification(commentNotification);
            Notification notification = notificationComment.constructNotification();
            notificationRepository.save(notification);

        }
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
