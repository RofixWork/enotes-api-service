package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findAllByCreatedBy(Long createdBy);

    Optional<Todo> findByIdAndCreatedBy(Long id, Long createdBy);
}
