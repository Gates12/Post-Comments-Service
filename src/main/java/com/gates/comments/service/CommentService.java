package com.gates.comments.service;

import com.gates.comments.model.Comment;
import com.gates.comments.model.Post;
import com.gates.comments.model.User;
import com.gates.comments.repository.CommentRepository;
import com.gates.comments.repository.PostRepository;
import com.gates.comments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addCommentToPost(Long postId, String username, Comment comment) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Error: User not found."));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("Error: Post not found."));
        comment.setUser(user);
        comment.setPost(post);
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
