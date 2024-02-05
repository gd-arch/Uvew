package com.flux.uvew.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentPageDto {
    private List<CommentDto> content;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private boolean isLastPage;
}
