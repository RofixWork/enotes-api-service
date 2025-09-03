package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.response.FavouriteNoteResponseDTO;
import com.rofix.enotes_service.entity.FavouriteNote;
import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.helper.FavouriteNoteHelper;
import com.rofix.enotes_service.helper.NoteHelper;
import com.rofix.enotes_service.repository.FavouriteNoteRepository;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteNoteServiceImpl implements FavouriteNoteService {
    private final NoteHelper noteHelper;
    private final FavouriteNoteRepository favouriteNoteRepository;
    private final ModelMapper modelMapper;
    private final FavouriteNoteHelper favouriteNoteHelper;

    @Override
    public FavouriteNoteResponseDTO addNoteToFavourite(Long noteId) {
        Note note = noteHelper.getNoteNotDeletedOrThrow(noteId);
        Long userId = AuthUtils.getLoggedInUser().getId();

        if(favouriteNoteRepository.existsByNote_IdAndUser(noteId, userId))
        {
            LoggerUtils.createLog(Level.WARN, getClass().getName(), "addNoteToFavourite",
                    String.format("Note %d already exists in the favourites for user %d", noteId, userId));
            throw new ConflictException("This note is already in your favourites");
        }

        FavouriteNote favouriteNote = FavouriteNote.builder()
                .note(note)
                .user(userId)
                .build();
        FavouriteNote savedFavNote = favouriteNoteRepository.save(favouriteNote);

        return modelMapper.map(savedFavNote, FavouriteNoteResponseDTO.class);
    }

    @Override
    public String removeNoteFromFavourite(Long favId) {
        Long userId = AuthUtils.getLoggedInUser().getId();
        FavouriteNote favNote = favouriteNoteHelper.getFavNoteByIdAndUserOrThrow(favId, userId);
        favouriteNoteRepository.delete(favNote);
        return "Favourite note deleted successfully";
    }

    @Override
    public List<FavouriteNoteResponseDTO> getUserFavouriteNotes(Long userId) {
        List<FavouriteNote> userFavNotes = favouriteNoteRepository.findAllByUser(userId);

        if(userFavNotes.isEmpty())
        {
            LoggerUtils.createLog(Level.WARN, FavouriteNoteServiceImpl.class.getName(), "getUserFavouriteNotes", "You dont have any Favourite Notes");
            return List.of();
        }

        return userFavNotes.stream().map(favNote -> modelMapper.map(favNote, FavouriteNoteResponseDTO.class)).toList();
    }
}
