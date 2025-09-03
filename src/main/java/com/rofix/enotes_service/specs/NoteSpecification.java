package com.rofix.enotes_service.specs;

import com.rofix.enotes_service.entity.Note;
import com.rofix.enotes_service.utils.AuthUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class NoteSpecification {
    public static Specification<Note> byTitleOrDescription(String keyword)
    {
        return (Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String cleanKeyword = "%" + keyword.trim().toLowerCase() + "%";
            Expression<String> dbTitle = criteriaBuilder.lower(criteriaBuilder.trim(root.get("title")));
            Expression<String> dbDescription = criteriaBuilder.lower(criteriaBuilder.trim(root.get("description")));

            Predicate byTitle = criteriaBuilder.like(dbTitle,  cleanKeyword);
            Predicate byDescription = criteriaBuilder.like(dbDescription,  cleanKeyword);
            Predicate byCreatedBy = getCreatedBy(root, criteriaBuilder);
            return criteriaBuilder.and(byCreatedBy, criteriaBuilder.isFalse(root.get("isDeleted")) ,criteriaBuilder.or(byTitle, byDescription));
        };
    }

    public static Specification<Note> byCategory(String category)
    {
        return (Root<Note> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String cleanCategory = category.trim().toLowerCase();
            Expression<String> dbCategory = criteriaBuilder.lower(criteriaBuilder.trim(root.get("category").get("name")));
            Predicate byCategory = criteriaBuilder.equal(dbCategory, cleanCategory);
            Predicate byCreatedBy = getCreatedBy(root, criteriaBuilder);
            return criteriaBuilder.and(byCreatedBy, criteriaBuilder.isFalse(root.get("isDeleted")), byCategory);
        };
    }

    private static Predicate getCreatedBy(Root<Note> root, CriteriaBuilder criteriaBuilder)
    {
        Long userId = AuthUtils.getLoggedInUser().getId();

        return criteriaBuilder.equal(root.get("createdBy"), userId);
    }
}
