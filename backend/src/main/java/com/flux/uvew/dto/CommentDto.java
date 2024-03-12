package com.flux.uvew.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String commentId;

    @NotBlank(message = "Video ID is required")
    private String videoId;

    @NotBlank(message = "Comment text is required")
    @Size(max = 10000, message = "Text must be less than 10000 characters")
    private String text;

    @NotBlank(message = "Author ID is required")
    private String authorId;

    @NotBlank(message = "Author Name is required")
    private String authorName;

    @Min(value = 0, message = "Like count cannot be negative")
    private Integer likeCount;

    @Min(value = 0, message = "Dislike count cannot be negative")
    private Integer dislikeCount;

}
