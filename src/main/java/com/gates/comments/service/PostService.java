package com.gates.comments.service;

import com.gates.comments.model.Post;
import com.gates.comments.model.User;
import com.gates.comments.repository.PostRepository;
import com.gates.comments.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(Post post, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("Error: User not found."));
        post.setUser(user);
        post.setCreatedAt(Instant.now());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }
}