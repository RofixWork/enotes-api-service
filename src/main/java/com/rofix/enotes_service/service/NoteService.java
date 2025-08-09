package com.rofix.enotes_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.entity.FileDetails;
import com.rofix.enotes_service.entity.Note;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(String note, MultipartFile file) throws JsonProcessingException;

    List<NoteResponseDTO> getAllNotes();

    byte[] downloadFile(FileDetails fileDetails) throws IOException;
}
