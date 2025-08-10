package com.rofix.enotes_service.schedular;

import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.repository.NoteRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotesSchedular {
    private final NoteRepository noteRepository;


    @Scheduled(cron = "0 */15 * * * *")
    public void deleteNoteSchedular() {
        //15 minutes for testing
        Instant cutoff = Instant.now().minus(Duration.ofMinutes(15));

        List<Note> notes = noteRepository.findAllByIsDeletedIsTrueAndDeletedOnBefore(cutoff);
        int size = notes.size();
        if (size > 0)
        {
            noteRepository.deleteAll(notes);

            LoggerUtils.createLog(Level.INFO, NotesSchedular.class.getName(), "deleteNoteSchedular", "Deleted {} Notes...", size);

        }
    }
}
