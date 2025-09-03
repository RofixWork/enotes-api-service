package com.rofix.enotes_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.config.AppConstants;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.dto.response.PageResponseDTO;
import com.rofix.enotes_service.endpoint.NoteEndpoint;
import com.rofix.enotes_service.entity.FileDetails;
import com.rofix.enotes_service.helper.NoteHelper;
import com.rofix.enotes_service.service.NoteService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class NoteController implements NoteEndpoint {
    private final NoteService noteService;
    private final NoteHelper noteHelper;

    @Override
    public ResponseEntity<?> createNote(
                String note,
                MultipartFile file
                ) throws JsonProcessingException {
        NoteResponseDTO noteResponseDTO = noteService.createNote(note, file);
        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED,"Note created", noteResponseDTO);
    }

    @Override
    public ResponseEntity<?> getNotes() {
        List<NoteResponseDTO> notes = noteService.getAllNotes();
        return ResponseUtils.createSuccessResponse("All Notes", notes);
    }

    @Override
    public ResponseEntity<?> updateNote(
           Long id,
            String note,
            MultipartFile file
    ) throws JsonProcessingException {
        NoteResponseDTO noteResponseDTO = noteService.updateNote(id, note, file);

        return ResponseUtils.createSuccessResponse("Note updated", noteResponseDTO);
    }

    @Override
    public ResponseEntity<?> getUserNotes(
           Integer pageNumber,
           Integer pageSize
    ) {
        Long userId = AuthUtils.getLoggedInUser().getId();

        PageResponseDTO resp = noteService.getUserNotes(userId, pageNumber, pageSize);

        return ResponseUtils.createSuccessResponse("Get All User Notes", resp);
    }

    @Override
    public ResponseEntity<?> downloadFile(Long id) throws IOException {
        FileDetails fileDetails = noteHelper.getFileDetailsOrThrow(id);
        byte[] downloadFile = noteService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();

        String contentType = noteHelper.getContentType(fileDetails.getOriginalFileName());

        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());

        return ResponseEntity.ok().headers(headers).body(downloadFile);
    }

    @Override
    public ResponseEntity<?> restoreDeletedNote(
           Long id
    ){
        NoteResponseDTO restoredNote = noteService.restoreDeleteNote(id);

        return ResponseUtils.createSuccessResponse("Restore Deleted Note", restoredNote);
    }

    @Override
    public ResponseEntity<?> softDeleteNote(
            Long id
    ){
        String status = noteService.softDeleteNote(id);

        return ResponseUtils.createSuccessResponse(status);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBin() {
        Long userId = AuthUtils.getLoggedInUser().getId();
        List<NoteResponseDTO> noteResponseDTOS = noteService.getUserRecycleBin(userId);

        return ResponseUtils.createSuccessResponse("Get Recycle Bin Note", noteResponseDTOS);
    }

    @Override
    public ResponseEntity<?> userClearRecycleBin() {
        Long userId = AuthUtils.getLoggedInUser().getId();
        String status = noteService.userClearRecycleBin(userId);

        return ResponseUtils.createSuccessResponse(status);
    }

    @Override
    public ResponseEntity<?> copyNote(
            Long noteId
    ) {
        noteService.copyNote(noteId);

        return ResponseUtils.createSuccessResponse("Note has been Copied Successfully.");
    }

    @Override
    public ResponseEntity<?> searchNote(
            String search,
            String category,
            Integer pageNumber,
            Integer pageSize
    ) {
        PageResponseDTO noteResponseDTOS = noteService.searchNote(search, category, pageNumber, pageSize);

        return ResponseUtils.createSuccessResponse("Search Note Successfully", noteResponseDTOS);
    }
}
