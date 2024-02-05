package com.flux.uvew.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadVideoDto {
    @NotBlank(message = "Video ID is required")
    private String id;

    @URL(message = "video url should be valid")
    private String videoUrl;
}
