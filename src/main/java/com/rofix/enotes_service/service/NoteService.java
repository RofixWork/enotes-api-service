package com.rofix.enotes_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.dto.response.PageResponseDTO;
import com.rofix.enotes_service.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(String note, MultipartFile file) throws JsonProcessingException;

    List<NoteResponseDTO> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws IOException;

    PageResponseDTO getUserNotes(Long userId, Integer pageNumber, Integer pageSize);


    NoteResponseDTO updateNote(Long id, String note, MultipartFile file) throws JsonProcessingException;

    String softDeleteNote(Long id);

    NoteResponseDTO restoreDeleteNote(Long id);

    List<NoteResponseDTO> getUserRecycleBin(Long userId);

    String userClearRecycleBin(Long userId);

    void copyNote(Long noteId);
}
