package com.rofix.enotes_service.enums;

import com.rofix.enotes_service.exception.base.BadRequestException;

public enum TodoStatus {
    NOT_STARTED(1, "Not Started"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed");

    private Integer id;
    private String name;

    TodoStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
