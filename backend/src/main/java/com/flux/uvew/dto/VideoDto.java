package com.flux.uvew.dto;

import com.flux.uvew.model.Comment;
import com.flux.uvew.model.VideoStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Data
public class VideoDto {
    private String id;
    private String title;
    private String userId;
    private Integer likes;
    private Integer dislikes;
    private Set<String> tags;
    private VideoStatus videoStatus;
    private Integer videoCount;
    private String thumbnailUrl;
    private String videoUrl;
    private List<Comment> commentList;
    private String description;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
