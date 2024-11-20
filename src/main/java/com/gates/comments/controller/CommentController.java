package com.gates.comments.controller;

import com.gates.comments.model.Comment;
import com.gates.comments.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Add Comment to Post
    @PostMapping("/posts/{postId}")
    public ResponseEntity<?> addCommentToPost(@PathVariable Long postId, @Valid @RequestBody Comment comment, Authentication authentication) {
        try {
            String username = authentication.getName();
            Comment savedComment = commentService.addCommentToPost(postId, username, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the comment.");
        }
    }

    // Get Comments for a Post
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching comments.");
        }
    }
}
