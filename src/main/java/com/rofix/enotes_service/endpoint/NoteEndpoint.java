package com.rofix.enotes_service.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.rofix.enotes_service.config.AppConstants.*;

@RequestMapping(value = "/api/v1/notes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface NoteEndpoint {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> createNote(
            @RequestParam(name = "note")String note,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) throws JsonProcessingException;

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getNotes();

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> updateNote(
            @Min(value = 1) @PathVariable Long id,
            @RequestParam(name = "note") String note,
            @RequestParam(name = "file", required = false)MultipartFile file
    ) throws JsonProcessingException;

    @GetMapping("/user")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserNotes(
            @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize
    );

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> downloadFile(@Min(value = 1) @PathVariable Long id) throws IOException;

    @GetMapping("restore/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> restoreDeletedNote(
            @Min(value = 1) @PathVariable Long id
    );

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> softDeleteNote(
            @Min(value = 1) @PathVariable Long id
    );

    @GetMapping("/user/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserRecycleBin();

    @DeleteMapping("/user/clear/recycle-bin")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> userClearRecycleBin();

    @GetMapping("/{id}/copy")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> copyNote(
            @Min(value = 1) @PathVariable("id") Long noteId
    );

    @GetMapping("/search")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> searchNote(
            @RequestParam(name = "keyword", defaultValue = "") String search,
            @RequestParam(name = "category", defaultValue = "") String category,
            @RequestParam(name = "pageNumber", defaultValue = PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = PAGE_SIZE, required = false) Integer pageSize
    );
}
