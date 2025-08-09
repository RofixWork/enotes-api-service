package com.rofix.enotes_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.dto.response.PageResponseDTO;
import com.rofix.enotes_service.entity.FileDetails;
import com.rofix.enotes_service.helper.NoteHelper;
import com.rofix.enotes_service.service.NoteService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteHelper noteHelper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNote(
                @RequestParam(name = "note")String note,
                @RequestParam(name = "file", required = false)MultipartFile file
                ) throws JsonProcessingException {
        NoteResponseDTO noteResponseDTO = noteService.createNote(note, file);
        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED,"Note created", noteResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getNotes() {
        List<NoteResponseDTO> notes = noteService.getAllNotes();
        return ResponseUtils.createSuccessResponse("All Notes", notes);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateNote(
            @Min(value = 1) @PathVariable Long id,
            @RequestParam(name = "note") String note,
            @RequestParam(name = "file", required = false)MultipartFile file
    ) throws JsonProcessingException {
        NoteResponseDTO noteResponseDTO = noteService.updateNote(id, note, file);

        return ResponseUtils.createSuccessResponse("Note updated", noteResponseDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserNotes(
           @RequestParam(name = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
           @RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize
    ) {
        Integer userId = 1;

        PageResponseDTO resp = noteService.getUserNotes(userId, pageNumber, pageSize);

        return ResponseUtils.createSuccessResponse("Get All User Notes", resp);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@Min(value = 1) @PathVariable Long id) throws IOException {
        FileDetails fileDetails = noteHelper.getFileDetailsOrThrow(id);
        byte[] downloadFile = noteService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();

        String contentType = noteHelper.getContentType(fileDetails.getOriginalFileName());

        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(headers).body(downloadFile);
    }
}
