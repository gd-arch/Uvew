package com.flux.uvew.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String commentId;

    @Field("video_id")
    @Indexed(unique = true)
    private String videoId;

    @Field("comment_text")
    @NotBlank(message = "Comment text is required")
    @Size(max = 10000, message = "Text must be less than 10000 characters")
    private String text;

    @Field("author_id")
    @NotBlank(message = "Author ID is required")
    private String authorId;

    @Field("author_name")
    @NotBlank(message = "Author Name is required")
    private String authorName;

    @Min(0)
    private Integer likeCount = 0;

    @Min(0)
    private Integer dislikeCount = 0;

    @Field("is_active")
    private boolean active ;

    @CreatedDate
    @Field("date_created")
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Field("date_modified")
    private LocalDateTime dateModified;
}
