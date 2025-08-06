package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.helper.LoggerHelper;
import com.rofix.enotes_service.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final ModelMapper modelMapper;
    private final NoteRepository noteRepository;
    private final CategoryHelper categoryHelper;
    private final LoggerHelper loggerHelper;

    @Override
    public NoteResponseDTO createNote(NoteRequestDTO noteRequestDTO) {
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(noteRequestDTO.getCategoryId());
        Note note = Note.builder()
                .title(noteRequestDTO.getTitle())
                .description(noteRequestDTO.getDescription())
                .category(category)
                .build(),
        savedNote = noteRepository.save(note);
        loggerHelper.createLog(Level.INFO, NoteServiceImpl.class.getName(), "createNote", "Note created successfully");
        return modelMapper.map(savedNote, NoteResponseDTO.class);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }
}
