package com.flux.uvew.dto;

import com.flux.uvew.model.VideoStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;
@Data
public class VideoDto {
    private String id;
    private String title;
    private Set<String> tags;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private String videoUrl;
}
