package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.service.NoteService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NoteRequestDTO noteRequestDTO) {
        NoteResponseDTO noteResponseDTO = noteService.createNote(noteRequestDTO);

        return ResponseUtils.createSuccessResponse("Note created", noteResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getNotes() {
        List<NoteResponseDTO> notes = noteService.getAllNotes();
        return ResponseUtils.createSuccessResponse("All Notes", notes);
    }
}
