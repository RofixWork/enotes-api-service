package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.entity.Note;

import java.util.List;

public interface NoteService {
    NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO);

    List<NoteResponseDTO> getAllNotes();
}
