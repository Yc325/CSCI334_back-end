package com.csci334.ConferenceMagment.web;

import com.csci334.ConferenceMagment.domain.Comment;
import com.csci334.ConferenceMagment.domain.User;
import com.csci334.ConferenceMagment.dto.CommentDto;
import com.csci334.ConferenceMagment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user){
        Comment comment = commentService.save(commentDto, user);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("")
    public ResponseEntity<Set<Comment>> getCommentsBypaper(@RequestParam Long paperId){
       Set<Comment> comments =  commentService.getCommentsByPaperId(paperId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteCommentById(@RequestParam Long commentId){
        commentService.deleteCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

}
