package com.rofix.enotes_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDetailsDTO {
    private String to;
    private String subject;
    private String title;
    private String message;
}
