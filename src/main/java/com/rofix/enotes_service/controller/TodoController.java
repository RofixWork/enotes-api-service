package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import com.rofix.enotes_service.dto.response.TodoResponseDTO;
import com.rofix.enotes_service.service.TodoService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/todos", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class TodoController {
    private final TodoService todoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createTodo(
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
            ) {
        TodoResponseDTO todoResponseDTO = todoService.createTodo(todoRequestDTO);

        return ResponseUtils.createSuccessResponse("Create todo success",  todoResponseDTO);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateTodo(
            @Min(value = 1) @PathVariable("id") Long todoId,
            @Valid @RequestBody TodoRequestDTO todoRequestDTO
    ) {
        Long userId = AuthUtils.getLoggedInUser().getId();
        TodoResponseDTO todoResponseDTO = todoService.updateTodo(todoId, userId, todoRequestDTO);

        return ResponseUtils.createSuccessResponse("Update todo success",  todoResponseDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserTodos() {
        Long userId = AuthUtils.getLoggedInUser().getId();
        List<TodoResponseDTO> todosByUser = todoService.findTodosByUser(userId);

        return ResponseUtils.createSuccessResponse("Get user todos", todosByUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTodoById(
            @Min(value = 1) @PathVariable("id") Long todoId
    ) {
        Long userId = AuthUtils.getLoggedInUser().getId();
        TodoResponseDTO todo = todoService.findTodoByIdAndUser(todoId, userId);

        return ResponseUtils.createSuccessResponse("Get Todo", todo);
    }
}
