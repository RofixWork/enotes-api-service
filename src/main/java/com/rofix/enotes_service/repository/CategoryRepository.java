package com.rofix.enotes_service.repository;

import com.rofix.enotes_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByIsActiveTrueAndIsDeletedFalse();

    Optional<Category> findByIdAndIsDeletedIsFalseAndIsActiveIsTrue(Long id);

    Optional<Category> findByIdAndIsDeletedIsFalse(Long id);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
