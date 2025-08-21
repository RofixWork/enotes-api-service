package com.rofix.enotes_service.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO {
    @Builder.Default
    private Object content = List.of();
    @Builder.Default
    private Integer pageNumber = 0;
    @Builder.Default
    private Integer pageSize = 0;
    @Builder.Default
    private Integer totalPages = 0;
    @Builder.Default
    private Long totalElements = 0L;
    @Builder.Default
    private Boolean isFirst = false;
    @Builder.Default
    private Boolean isLast = false;
}
