package com.ericafenyo.carehub.contexts;

import com.ericafenyo.carehub.model.Priority;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder(setterPrefix = "set")
public class CreateTaskContext {
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private UUID teamId;
}
