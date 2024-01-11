package com.flux.uvew.service;

import com.flux.uvew.dto.CommentDto;
import com.flux.uvew.exceptions.ResourceNotFoundException;
import com.flux.uvew.model.Comment;
import com.flux.uvew.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .filter(Comment::isActive)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    private Comment getCommentById(@NonNull String commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException(String.format("comment with comment Id %s not found",commentId)));
    }
    public CommentDto getCommentForId(@NonNull String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException(String.format("comment with comment Id %s not found",commentId)));
        return modelMapper.map(comment, CommentDto.class);
    }

    public List<CommentDto> getCommentsByVideoId(@NonNull String videoId) {
       return commentRepository.findByVideoId(videoId).orElse(new ArrayList<>())
                .stream()
                .filter(Comment::isActive)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        populateCommentFields(comment, commentDto);
        comment.setDislikeCount(0);
        comment.setLikeCount(0);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    public CommentDto updateComment(CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentDto.getCommentId()));

        populateCommentFields(existingComment, commentDto);
        Comment updatedComment = commentRepository.save(existingComment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    private void populateCommentFields(Comment comment, CommentDto commentDto) {
        comment.setActive(true);
        if (Objects.nonNull(commentDto.getText())) {
            comment.setText(commentDto.getText());
        }
        if (Objects.nonNull(commentDto.getAuthorId())) {
            comment.setAuthorId(commentDto.getAuthorId());
        }
        if (Objects.nonNull(commentDto.getVideoId())) {
            comment.setVideoId(commentDto.getVideoId());
        }
    }

    public void deleteComment(@NonNull String commentId) {
        Comment comment = getCommentById(commentId);
        comment.setActive(false);
        commentRepository.save(comment);
    }

}
