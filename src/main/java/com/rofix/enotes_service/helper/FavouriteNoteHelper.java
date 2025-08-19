package com.rofix.enotes_service.helper;

import com.rofix.enotes_service.entity.FavouriteNote;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.repository.FavouriteNoteRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FavouriteNoteHelper {
    private final FavouriteNoteRepository favouriteNoteRepository;

    public FavouriteNote getFavNoteByIdAndUserOrThrow(Long favId, Long userId) {
        return favouriteNoteRepository.findByIdAndUser(favId, userId)
                .orElseThrow(() -> {
                    LoggerUtils.createLog(Level.WARN, getClass().getName(),
                            "getFavNoteByIdAndUserOrThrow",
                            String.format("User %d attempted to delete favourite note %d without permission", userId, favId));
                    return new BadRequestException("Favourite note not found or access denied");
                });
    }
}
