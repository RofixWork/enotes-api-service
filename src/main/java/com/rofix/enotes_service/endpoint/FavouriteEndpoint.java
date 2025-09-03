package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_USER;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Favourites", description = "Manages a user's favorite notes.")
@RequestMapping(value = "/api/v1/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
public interface FavouriteEndpoint {

    @Operation(
            summary = "Add a note to favorites",
            description = "Adds a specific note to the authenticated user's list of favorites.",
            tags = {"Favourites"}
    )
    @PostMapping("/{noteId}") // Changed to POST for resource creation
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> addNoteToFavourite(
            @Min(value = 1) @PathVariable("noteId") Long noteId
    );

    @Operation(
            summary = "Remove a note from favorites",
            description = "Removes a specific note from the authenticated user's list of favorites.",
            tags = {"Favourites"}
    )
    @DeleteMapping("/{favId}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> removeNoteFromFavourite(
            @Min (value = 1) @PathVariable("favId") Long favId
    );

    @Operation(
            summary = "Get user's favorite notes",
            description = "Retrieves all notes in the authenticated user's favorites list.",
            tags = {"Favourites"}
    )
    @GetMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserFavouriteNotes();
}

