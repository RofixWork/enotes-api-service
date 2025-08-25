package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_USER;
import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TodoEndpoint {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> createTodo(
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
    );

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> updateTodo(
            @Min(value = 1) @PathVariable("id") Long todoId,
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
    );

    @GetMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserTodos();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(
            @Min(value = 1) @PathVariable("id") Long todoId
    );
}
