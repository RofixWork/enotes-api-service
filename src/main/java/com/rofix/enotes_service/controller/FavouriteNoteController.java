package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.response.FavouriteNoteResponseDTO;
import com.rofix.enotes_service.repository.FavouriteNoteRepository;
import com.rofix.enotes_service.service.FavouriteNoteService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class FavouriteNoteController {

    private final FavouriteNoteService favouriteNoteService;

    @GetMapping("/{noteId}")
    public ResponseEntity<?> addNoteToFavourite(
            @Min (value = 1) @PathVariable("noteId") Long noteId
    ) {
        FavouriteNoteResponseDTO favouriteNoteResponseDTO = favouriteNoteService.addNoteToFavourite(noteId);

        return ResponseUtils.createSuccessResponse("Note added successfully", favouriteNoteResponseDTO);
    }

    @DeleteMapping("/{favId}")
    public ResponseEntity<?> removeNoteFromFavourite(
            @Min (value = 1) @PathVariable("favId") Long favId
    ) {
        String status = favouriteNoteService.removeNoteFromFavourite(favId);

        return ResponseUtils.createSuccessResponse(status);
    }

    @GetMapping
    public ResponseEntity<?> getUserFavouriteNotes() {
        Integer userId = AuthUtils.getCurrentUserId();
        List<FavouriteNoteResponseDTO> favUserNotes = favouriteNoteService.getUserFavouriteNotes(userId);

        return ResponseUtils.createSuccessResponse("Get User Fav Notes", favUserNotes);
    }
}
