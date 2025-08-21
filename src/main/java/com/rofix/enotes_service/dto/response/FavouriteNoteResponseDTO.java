package com.rofix.enotes_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteNoteResponseDTO {
    private Long id;
    private NoteResponseDTO note;
    private Integer user;
}
