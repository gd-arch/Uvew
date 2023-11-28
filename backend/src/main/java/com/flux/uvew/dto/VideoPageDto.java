package com.flux.uvew.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VideoPageDto {
    private List<VideoDto> content;
    private int pageNumber;
    private  int pageSize;
    private  long totalElements;
    private  int totalPages;
    private boolean isLastPage;

}
