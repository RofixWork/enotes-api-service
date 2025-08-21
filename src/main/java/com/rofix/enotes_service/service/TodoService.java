package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import com.rofix.enotes_service.dto.response.TodoResponseDTO;

import java.util.List;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO updateTodo(Long todoId, Long userId, TodoRequestDTO todoRequestDTO);

    List<TodoResponseDTO> findTodosByUser(Long userId);

    TodoResponseDTO findTodoByIdAndUser(Long id, Long userId);

}
