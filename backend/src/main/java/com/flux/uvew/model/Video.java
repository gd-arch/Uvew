package com.flux.uvew.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(value = "Video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private String id;
    @TextIndexed(weight=2)
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Field("video_title")
    private String title;

    @NotBlank(message = "User Sub ID is required")
    @Field("sub_id")
    private String userSubId;

    @Field("author_name")
    private String authorName;

    @Min(0)
    private Integer likes = 0;

    @Min(0)
    private Integer dislikes = 0;

    private Set<String> tags = new HashSet<>();

    @Field("video_status")
    private VideoStatus videoStatus = VideoStatus.PUBLIC;

    @Min(0)
    private Integer views = 0;

    @URL(message = "Invalid thumbnail URL format")
    @Field("thumbnail_url")
    private String thumbnailUrl;

    @URL(message = "Invalid video URL format")
    @Field("video_url")
    private String videoUrl;

    @TextIndexed
    @NotBlank(message = "Description is required")
    @Size(max = 1000)
    @Field("description")
    private String description;

    @CreatedDate
    @Field("date_created")
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Field("date_modified")
    private LocalDateTime dateModified;

    @Version
    private Long version;

    private boolean isActive = false;
}
