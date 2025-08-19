package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.response.FavouriteNoteResponseDTO;

import java.util.List;

public interface FavouriteNoteService {
    FavouriteNoteResponseDTO addNoteToFavourite(Long noteId);

    String removeNoteFromFavourite(Long favId);

    List<FavouriteNoteResponseDTO> getUserFavouriteNotes(Long userId);
}
