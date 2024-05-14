package com.example.jpa.controller;

import com.example.jpa.exception.ResourceNotFoundException;
import com.example.jpa.model.Comment;
import com.example.jpa.repository.CommentRepository;
import com.example.jpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable (value =
            "postId") Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }
//Toegevoegd door Jonathan om individuele comments terug te krijgen
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Comment> getCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId) {
        Optional<Comment> comment = commentRepository.findByIdAndPostId(commentId, postId);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }





    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable (value = "postId") Long
                                         postId, @Valid @RequestBody Comment comment) {

        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId
                + " not found"));

    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable (value = "postId") Long
                                         postId, @PathVariable (value = "commentId") Long commentId, @Valid
                                 @RequestBody Comment commentRequest) {

        if(!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " +
                commentId + "not found"));

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId")
                                           Long postId, @PathVariable (value = "commentId") Long commentId) {

        return commentRepository.findByIdAndPostId(commentId,
                postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
            // Default Exception die kan gebruikt worden is ResponseStatusException
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));

    }

}
