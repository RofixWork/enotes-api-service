package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_USER;
import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todos", description = "Manages a user's to-do list.")
@RequestMapping(value = "/api/v1/todos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface TodoEndpoint {

    @Operation(
            summary = "Create a new to-do item",
            description = "Creates a new to-do item for the authenticated user.",
            tags = {"Todos"}
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> createTodo(
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
    );

    @Operation(
            summary = "Update an existing to-do item",
            description = "Updates an existing to-do item by its ID.",
            tags = {"Todos"}
    )
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> updateTodo(
            @Min(value = 1) @PathVariable("id") Long todoId,
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
    );

    @Operation(
            summary = "Get all to-do items for the user",
            description = "Retrieves a list of all to-do items for the authenticated user.",
            tags = {"Todos"}
    )
    @GetMapping
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getUserTodos();

    @Operation(
            summary = "Get a to-do item by ID",
            description = "Retrieves a single to-do item by its ID.",
            tags = {"Todos"}
    )
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    ResponseEntity<?> getTodoById(
            @Min(value = 1) @PathVariable("id") Long todoId
    );
}