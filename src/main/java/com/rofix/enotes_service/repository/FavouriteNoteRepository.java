package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote,Long> {
    List<FavouriteNote> findAllByUser(Integer user);

    boolean existsByNote_IdAndUser(Long noteId, Integer user);

    Optional<FavouriteNote> findByIdAndUser(Long id, Integer user);
}
