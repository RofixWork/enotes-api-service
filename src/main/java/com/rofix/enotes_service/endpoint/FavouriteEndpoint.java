package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_USER;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/v1/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
public interface FavouriteEndpoint {
    @GetMapping("/{noteId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> addNoteToFavourite(
            @Min(value = 1) @PathVariable("noteId") Long noteId
    );

    @DeleteMapping("/{favId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> removeNoteFromFavourite(
            @Min (value = 1) @PathVariable("favId") Long favId
    );

    @GetMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavouriteNotes();
}
