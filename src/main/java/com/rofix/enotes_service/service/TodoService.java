package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.response.TodoRequestDTO;
import com.rofix.enotes_service.dto.response.TodoResponseDTO;

import java.util.List;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO updateTodo(Long todoId, Integer userId, TodoRequestDTO todoRequestDTO);

    List<TodoResponseDTO> findTodosByUser(Integer userId);

    TodoResponseDTO findTodoByIdAndUser(Long id, Integer userId);

}
