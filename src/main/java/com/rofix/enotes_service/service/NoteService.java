package com.rofix.enotes_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.dto.response.PageResponseDTO;
import com.rofix.enotes_service.entity.FileDetails;
import jakarta.validation.constraints.Min;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(String note, MultipartFile file) throws JsonProcessingException;

    List<NoteResponseDTO> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws IOException;

    PageResponseDTO getUserNotes(Integer userId, Integer pageNumber, Integer pageSize);


    NoteResponseDTO updateNote(Long id, String note, MultipartFile file) throws JsonProcessingException;

    String softDeleteNote(Long id);

    NoteResponseDTO restoreDeleteNote(Long id);

    List<NoteResponseDTO> getUserRecycleBin(Integer userId);

    String userClearRecycleBin(Integer userId);

    void copyNote(Long noteId);
}
