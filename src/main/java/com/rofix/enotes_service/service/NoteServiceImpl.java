package com.rofix.enotes_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rofix.enotes_service.dto.request.NoteRequestDTO;
import com.rofix.enotes_service.dto.response.NoteResponseDTO;
import com.rofix.enotes_service.dto.response.PageResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.entity.FileDetails;
import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.helper.NoteHelper;
import com.rofix.enotes_service.repository.NoteRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final ModelMapper modelMapper;
    private final NoteRepository noteRepository;
    private final CategoryHelper categoryHelper;
    private final NoteHelper noteHelper;


    @Override
    public NoteResponseDTO createNote(String noteRequest, MultipartFile file) throws JsonProcessingException {
        NoteRequestDTO noteRequestDTO = noteHelper.getNoteRequestDTO(noteRequest);

        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(noteRequestDTO.getCategoryId());

        //save file
        FileDetails fileDetails = file != null && !file.isEmpty() ? noteHelper.saveFileDetails(file) : null;

        Note note = Note.builder()
                .title(noteRequestDTO.getTitle())
                .description(noteRequestDTO.getDescription())
                .category(category)
                .fileDetails(fileDetails)
                .build(),
        savedNote = noteRepository.save(note);
        LoggerUtils.createLog(Level.INFO, NoteServiceImpl.class.getName(), "createNote", "Note created successfully");
        return modelMapper.map(savedNote, NoteResponseDTO.class);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws IOException {
        var inputStream = new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(inputStream);
    }

    @Override
    public PageResponseDTO getUserNotes(Integer userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<Note> notePage = noteRepository.findAllByCreatedByAndIsDeletedIsFalse(userId, pageable);

        if(notePage.isEmpty())
            return PageResponseDTO.builder().build();

        List<NoteResponseDTO> noteResponseDTOS = notePage.get()
                .map(note -> modelMapper.map(note, NoteResponseDTO.class))
                .toList();

        return PageResponseDTO.builder()
                .content(noteResponseDTOS)
                .pageNumber(1)
                .pageSize(5)
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getTotalElements())
                .pageSize(notePage.getSize())
                .isLast(notePage.isLast())
                .isFirst(notePage.isFirst())
                .build();
    }

    @Override
    public NoteResponseDTO updateNote(Long id, String note, MultipartFile file) throws JsonProcessingException {
        Note currentNote = noteHelper.getNoteOrThrow(id);
        NoteRequestDTO noteRequestDTO = noteHelper.getNoteRequestDTO(note);
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(noteRequestDTO.getCategoryId());
        FileDetails fileDetails = file != null && !file.isEmpty() ?
                noteHelper.saveFileDetails(file) : currentNote.getFileDetails();

        //update
        currentNote.setTitle(noteRequestDTO.getTitle());
        currentNote.setDescription(noteRequestDTO.getDescription());
        currentNote.setCategory(category);
        currentNote.setFileDetails(fileDetails);
        Note updatedNote = noteRepository.save(currentNote);

        return modelMapper.map(updatedNote, NoteResponseDTO.class);
    }

    @Override
    public String softDeleteNote(Long id) {
        Note note = noteHelper.getNoteOrThrow(id);

        if(note.getIsDeleted())
        {
            LoggerUtils.createLog(Level.WARN,  NoteServiceImpl.class.getName(), "softDeleteNote", "Note with id {} not deleted", note.getId());
            throw new BadRequestException("Note with id: " + note.getId() + " not deleted");
        }

        note.setIsDeleted(true);
        note.setDeletedOn(Instant.now());
        noteRepository.save(note);

        return "Note with id: " + note.getId() + " has been deleted successfully...";
    }

    @Override
    public NoteResponseDTO restoreDeleteNote(Long id) {
        Note note  = noteHelper.getNoteOrThrow(id);

        if(note.getIsDeleted() == Boolean.FALSE)
        {
            LoggerUtils.createLog(Level.WARN, NoteServiceImpl.class.getName(), "restoreDeleteNote", "Note with ID {} has not been deleted.", id);
            throw new BadRequestException("Note with ID " + id + " has not been deleted.");
        }

        note.setIsDeleted(false);
        note.setDeletedOn(null);
        Note restoredNote = noteRepository.save(note);

        return modelMapper.map(restoredNote, NoteResponseDTO.class);
    }

    @Override
    public List<NoteResponseDTO> getUserRecycleBin(Integer userId) {
        List<Note> notes = noteRepository.findAllByCreatedByAndIsDeletedIsTrue(userId);

        return notes.stream().map(note -> modelMapper.map(note, NoteResponseDTO.class)).toList();
    }

    @Override
    public String userClearRecycleBin(Integer userId) {
        List<Note> notes = noteRepository.findAllByCreatedByAndIsDeletedIsTrue(userId);

        if(notes == null || notes.isEmpty())
        {
            throw new BadRequestException("You dont have any notes in the Recycle Bin!!!");
        }
        noteRepository.deleteAll(notes);
        return "Recycle Bin has been clear successfully...";
    }
}

