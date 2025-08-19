package com.rofix.enotes_service.helper;

import com.rofix.enotes_service.dto.response.TodoResponseDTO;
import com.rofix.enotes_service.entity.Todo;
import com.rofix.enotes_service.enums.TodoStatus;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.repository.TodoRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodoHelper {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public List<Todo> getUserTodos(Long userId) {
        return todoRepository.findAllByCreatedBy(userId);
    }

    public Todo getTodoByIdAndUser(Long todoId, Long userId) {
        return todoRepository.findByIdAndCreatedBy(todoId, userId).orElseThrow(() -> {
            LoggerUtils.createLog(Level.WARN, getClass().getName(),
                    "getFavNoteByIdAndUserOrThrow",
                    String.format("User %d attempted to show todo note %d without permission", userId, todoId));
            return new BadRequestException("Todo note not found or access denied");
        });
    }

    public TodoResponseDTO getTodoResponseDTO(Todo todo) {
        TodoResponseDTO todoResponseDTO = modelMapper.map(todo, TodoResponseDTO.class);
        TodoStatus todoStatus = TodoStatus.getTodoStatus(todo.getStatus());

        TodoResponseDTO.TodoStatusDTO todoStatusDTO = TodoResponseDTO.TodoStatusDTO.builder()
                .id(todoStatus.getId())
                .name(todoStatus.getName())
                .build();
        todoResponseDTO.setStatus(todoStatusDTO);

        return todoResponseDTO;
    }
}
