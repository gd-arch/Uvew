package com.flux.uvew.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UploadThumbnailDto {
    @NotBlank(message = "Video ID is required")
    String id;
    @URL(message = "thumbnail url should be valid")
    String thumbnailUrl;
}
