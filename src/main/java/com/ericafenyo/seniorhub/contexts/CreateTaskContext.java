package com.ericafenyo.seniorhub.contexts;

import com.ericafenyo.seniorhub.model.Priority;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder(setterPrefix = "set")
public class CreateTaskContext {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private String teamId;
}
