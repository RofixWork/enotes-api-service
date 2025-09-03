package com.rofix.enotes_service.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.rofix.enotes_service.config.AppConstants.*;

@Tag(name = "Notes", description = "Manages a user's notes, including CRUD, search, and file management.")
@RequestMapping(value = "/api/v1/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface NoteEndpoint {

    @Operation(
            summary = "Create a new note",
            description = "Creates a new note, with an option to attach a file.",
            tags = {"Notes"}
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> createNote(
            @Parameter(
                    description = "Json String Note",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NoteRequestDTO.class))
            )
            @RequestParam(name = "note") String note,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) throws JsonProcessingException;

    @Operation(
            summary = "Get all notes",
            description = "Retrieves a list of all notes in the system. (Admin only)",
            tags = {"Notes"}
    )
    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getNotes();

    @Operation(
            summary = "Update an existing note",
            description = "Updates an existing note by ID, including its content and attached file.",
            tags = {"Notes"}
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> updateNote(
            @Min(value = 1) @PathVariable Long id,
            @RequestParam(name = "note") String note,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) throws JsonProcessingException;

    @Operation(
            summary = "Get all notes for the current user",
            description = "Retrieves a paginated list of notes owned by the authenticated user.",
            tags = {"Notes"}
    )
    @GetMapping("/user")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserNotes(
            @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize
    );

    @Operation(
            summary = "Download an attached file",
            description = "Downloads a file attached to a specific note by its ID.",
            tags = {"Notes"}
    )
    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> downloadFile(@Min(value = 1) @PathVariable Long id) throws IOException;

    @Operation(
            summary = "Restore a deleted note",
            description = "Restores a soft-deleted note from the recycle bin.",
            tags = {"Notes"}
    )
    @GetMapping("restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreDeletedNote(
            @Min(value = 1) @PathVariable Long id
    );

    @Operation(
            summary = "Soft delete a note",
            description = "Moves a note to the user's recycle bin.",
            tags = {"Notes"}
    )
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> softDeleteNote(
            @Min(value = 1) @PathVariable Long id
    );

    @Operation(
            summary = "Get user's recycle bin",
            description = "Retrieves all notes in the authenticated user's recycle bin.",
            tags = {"Notes"}
    )
    @GetMapping("/user/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBin();

    @Operation(
            summary = "Clear the recycle bin",
            description = "Permanently deletes all notes in the authenticated user's recycle bin.",
            tags = {"Notes"}
    )
    @DeleteMapping("/user/clear/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> userClearRecycleBin();

    @Operation(
            summary = "Copy an existing note",
            description = "Creates a duplicate copy of an existing note.",
            tags = {"Notes"}
    )
    @GetMapping("/{id}/copy")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNote(
            @Min(value = 1) @PathVariable("id") Long noteId
    );

    @Operation(
            summary = "Search notes",
            description = "Searches for notes by keyword and category, with pagination support.",
            tags = {"Notes"}
    )
    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNote(
            @RequestParam(name = "keyword", defaultValue = "") String search,
            @RequestParam(name = "category", defaultValue = "") String category,
            @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize
    );
}
