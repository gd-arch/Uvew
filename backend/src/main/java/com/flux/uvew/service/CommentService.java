package com.flux.uvew.service;

import com.flux.uvew.dto.CommentDataDto;
import com.flux.uvew.dto.CommentDto;
import com.flux.uvew.dto.CommentPageDto;
import com.flux.uvew.exceptions.ResourceNotFoundException;
import com.flux.uvew.exceptions.UnauthorisedException;
import com.flux.uvew.model.Comment;
import com.flux.uvew.model.User;
import com.flux.uvew.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final VideoService videoService;

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
        return modelMapper.map(getCommentById(commentId), CommentDto.class);
    }

    public List<CommentDto> getCommentsByVideoId(@NonNull String videoId) {
       return commentRepository.findByVideoId(videoId).orElse(new ArrayList<>())
                .stream()
                .filter(Comment::isActive)
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }
    public CommentPageDto getCommentsByPaginationForVideo(String videoId,int pageNo, int pageSize){
        Sort sortByModifiedDate = Sort.by("dateModified").descending();
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sortByModifiedDate );
        Page<Comment> page = commentRepository.findByVideoIdWithPagination(videoId,pageRequest);
        return CommentPageDto.builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .content(page.getContent()
                        .stream()
                        .map(comment -> modelMapper.map(comment, CommentDto.class))
                        .toList())
                .isLastPage(page.isLast())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public CommentDto saveComment(CommentDataDto commentDto) {
        Comment comment = populateCommentFields(commentDto);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    public CommentDto updateComment(CommentDataDto commentDto) {
        Comment existingComment = commentRepository.findById(commentDto.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentDto.getCommentId()));

        if(!existingComment.isActive()){
            throw new UnauthorisedException(String.format("Comment not active with id %s",commentDto.getCommentId()));
        }
        if (Objects.nonNull(commentDto.getText())) {
            String sanitizedText = HtmlUtils.htmlEscape(commentDto.getText());
            existingComment.setText(sanitizedText);
        }
        existingComment.setAuthorId(userService.getCurrentUser().getSub());

        Comment updatedComment = commentRepository.save(existingComment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    private Comment populateCommentFields(CommentDataDto commentDto) {
        Comment comment = new Comment();
        comment.setActive(true);
        if (Objects.nonNull(commentDto.getText())) {
            String sanitizedText = HtmlUtils.htmlEscape(commentDto.getText());
            comment.setText(sanitizedText);
        }
        User currentUser = userService.getCurrentUser();
        if (Objects.nonNull(currentUser)){
            if(Objects.nonNull(currentUser.getId())){
                comment.setAuthorId(currentUser.getSub());
            }
            if(Objects.nonNull(currentUser.getFirstName()) && Objects.nonNull(currentUser.getLastName())){
                comment.setAuthorName(currentUser.getFirstName()+" "+currentUser.getLastName());
            }
        }

        if (Objects.nonNull(commentDto.getVideoId()) && (videoService.findVideoById(commentDto.getVideoId())!=null)) {
                comment.setVideoId(commentDto.getVideoId());

        }
        comment.setDislikeCount(0);
        comment.setLikeCount(0);
        return comment;
    }

    public void deleteComment(@NonNull String commentId) {
        Comment comment = getCommentById(commentId);
        User user = userService.getCurrentUser();
        if(comment.isActive() && comment.getAuthorId().equals(user.getId())) {
            comment.setActive(false);
        }
        commentRepository.save(comment);
    }

}
