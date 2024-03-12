package com.flux.uvew.dto;

import com.flux.uvew.model.VideoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.Set;
@Data
public class VideoDto {
    private String id;

    @NotBlank(message = "Title is required")
    private String title;

    private String userSubId;

    private String authorName;

    private Integer views;

    private Integer likes;

    private Integer dislikes;

    private Set<String> tags;

    private VideoStatus videoStatus;

    @URL(message = "Invalid thumbnail URL format")
    private String thumbnailUrl;

    @URL(message = "Invalid video URL format")
    private String videoUrl;

    @Size(max = 10000, message = "Description cannot exceed 10,000 characters")
    private String description;

    private LocalDateTime dateCreated;

    private LocalDateTime dateModified;


}
