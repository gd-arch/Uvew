package com.flux.uvew.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoPageDto {
    private List<VideoDto> content;
    private int pageNumber;
    private  int pageSize;
    private  long totalElements;
    private  int totalPages;
    private boolean isLastPage;

}
