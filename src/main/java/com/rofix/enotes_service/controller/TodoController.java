package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import com.rofix.enotes_service.dto.response.TodoResponseDTO;
import com.rofix.enotes_service.endpoint.TodoEndpoint;
import com.rofix.enotes_service.service.TodoService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class TodoController implements TodoEndpoint {
    private final TodoService todoService;

    @Override
    public ResponseEntity<?> createTodo(
           TodoRequestDTO todoRequestDTO
            ) {
        TodoResponseDTO todoResponseDTO = todoService.createTodo(todoRequestDTO);

        return ResponseUtils.createSuccessResponse("Create todo success",  todoResponseDTO);
    }

    @Override
    public ResponseEntity<?> updateTodo(
           Long todoId,
           TodoRequestDTO todoRequestDTO
    ) {
        Long userId = AuthUtils.getLoggedInUser().getId();
        TodoResponseDTO todoResponseDTO = todoService.updateTodo(todoId, userId, todoRequestDTO);

        return ResponseUtils.createSuccessResponse("Update todo success",  todoResponseDTO);
    }

    @Override
    public ResponseEntity<?> getUserTodos() {
        Long userId = AuthUtils.getLoggedInUser().getId();
        List<TodoResponseDTO> todosByUser = todoService.findTodosByUser(userId);

        return ResponseUtils.createSuccessResponse("Get user todos", todosByUser);
    }

    @Override
    public ResponseEntity<?> getTodoById(
            Long todoId
    ) {
        Long userId = AuthUtils.getLoggedInUser().getId();
        TodoResponseDTO todo = todoService.findTodoByIdAndUser(todoId, userId);

        return ResponseUtils.createSuccessResponse("Get Todo", todo);
    }
}
