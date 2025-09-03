package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.response.FavouriteNoteResponseDTO;
import com.rofix.enotes_service.endpoint.FavouriteEndpoint;
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
@RequiredArgsConstructor
@Validated
public class FavouriteNoteController implements FavouriteEndpoint {

    private final FavouriteNoteService favouriteNoteService;

    @Override
    public ResponseEntity<?> addNoteToFavourite(
           Long noteId
    ) {
        FavouriteNoteResponseDTO favouriteNoteResponseDTO = favouriteNoteService.addNoteToFavourite(noteId);

        return ResponseUtils.createSuccessResponse("Note added successfully", favouriteNoteResponseDTO);
    }

    @Override
    public ResponseEntity<?> removeNoteFromFavourite(
            Long favId
    ) {
        String status = favouriteNoteService.removeNoteFromFavourite(favId);

        return ResponseUtils.createSuccessResponse(status);
    }

    @Override
    public ResponseEntity<?> getUserFavouriteNotes() {
        Long userId = AuthUtils.getLoggedInUser().getId();
        List<FavouriteNoteResponseDTO> favUserNotes = favouriteNoteService.getUserFavouriteNotes(userId);

        return ResponseUtils.createSuccessResponse("Get User Fav Notes", favUserNotes);
    }
}
