package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findAllByCreatedBy(Integer createdBy, Pageable pageable);
}
