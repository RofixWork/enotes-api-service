package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.TodoRequestDTO;
import com.rofix.enotes_service.dto.response.TodoResponseDTO;
import com.rofix.enotes_service.entity.Todo;
import com.rofix.enotes_service.enums.TodoStatus;
import com.rofix.enotes_service.helper.TodoHelper;
import com.rofix.enotes_service.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;
    private final TodoHelper todoHelper;

    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {

        TodoStatus todoStatus = TodoStatus.getTodoStatus(todoRequestDTO.getStatus());

        Todo todo = Todo.builder()
                .title(todoRequestDTO.getTitle())
                .description(todoRequestDTO.getDescription())
                .status(todoStatus.getId())
                .build();
        Todo saveTodo = todoRepository.save(todo);

        return todoHelper.getTodoResponseDTO(saveTodo);
    }

    @Override
    public TodoResponseDTO updateTodo(Long todoId, Integer userId, TodoRequestDTO todoRequestDTO) {
        Todo todo = todoHelper.getTodoByIdAndUser(todoId, userId);
        TodoStatus todoStatus = TodoStatus.getTodoStatus(todoRequestDTO.getStatus());

        todo.setTitle(todoRequestDTO.getTitle());
        todo.setDescription(todoRequestDTO.getDescription());
        todo.setStatus(todoStatus.getId());
        Todo updatedTodo = todoRepository.save(todo);

        return todoHelper.getTodoResponseDTO(updatedTodo);
    }

    @Override
    public List<TodoResponseDTO> findTodosByUser(Integer userId) {
        List<Todo> userTodos = todoHelper.getUserTodos(userId);
        return userTodos.stream().map(todoHelper::getTodoResponseDTO).toList();
    }

    @Override
    public TodoResponseDTO findTodoByIdAndUser(Long id, Integer userId) {
        Todo todo = todoHelper.getTodoByIdAndUser(id, userId);
        return todoHelper.getTodoResponseDTO(todo);
    }
}
