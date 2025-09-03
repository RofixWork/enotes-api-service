package com.rofix.enotes_service.enums;

import com.rofix.enotes_service.exception.base.BadRequestException;
import lombok.Getter;

@Getter
public enum TodoStatus {
    NOT_STARTED(1, "Not Started"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed");

    private final Integer id;
    private final String name;

    TodoStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TodoStatus getTodoStatus(Integer id) {
        for (TodoStatus status : TodoStatus.values()) {
            if(status.id.equals(id)) {
                return status;
            }
        }
        throw new BadRequestException("No Todo Status with Id " + id + " found.");
    }
}
