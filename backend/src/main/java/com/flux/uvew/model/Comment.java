package com.flux.uvew.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    private String id;
    private String videoId;
    private String text;
    private String authorId;
    private Integer likeCount;
    private Integer dislikeCount;
    @CreatedDate
    private LocalDateTime dateCreated;
    @LastModifiedDate
    private LocalDateTime dateModified;

}
