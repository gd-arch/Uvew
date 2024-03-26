package com.flux.uvew.controller;

import com.flux.uvew.dto.CommentDataDto;
import com.flux.uvew.dto.CommentDto;
import com.flux.uvew.dto.CommentPageDto;
import com.flux.uvew.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
//    @PreAuthorize("admin")
    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable String commentId) {
        CommentDto commentDto = commentService.getCommentForId(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<List<CommentDto>> getCommentsByVideoId(@PathVariable String videoId) {
        List<CommentDto> comments = commentService.getCommentsByVideoId(videoId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentDto> saveComment( @RequestBody CommentDataDto commentDto) {
        CommentDto savedComment = commentService.saveComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<CommentPageDto> getCommentsByPaginationForVideo(@RequestParam(required = true)String videoId,
                                                    @RequestParam(defaultValue = "0",required = true)int pageNo,
                                                    @RequestParam(defaultValue = "10",required = true)int pageSize){
        return ResponseEntity.ok(commentService.getCommentsByPaginationForVideo(videoId,pageNo,pageSize));
    }
}

