package com.flux.uvew.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Document(value = "Video")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    private String id;
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotBlank
    private String userId;
    private Integer likes;
    private Integer dislikes;
    private Set<String> tags;
    private VideoStatus videoStatus;
    private Integer videoCount;
    @URL
    private String thumbnailUrl;
    @URL
    private String videoUrl;
    private Integer commentCount;
    @NotBlank
    @Size(max = 1000)
    private String description;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateModified;

}
